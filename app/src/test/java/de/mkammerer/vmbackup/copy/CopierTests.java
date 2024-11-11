package de.mkammerer.vmbackup.copy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

import de.mkammerer.vmbackup.hash.HashAlgorithm;
import de.mkammerer.vmbackup.hash.Hasher;
import de.mkammerer.vmbackup.progress.DataSize;
import de.mkammerer.vmbackup.progress.NoopProcessReporter;
import de.mkammerer.vmbackup.util.ChannelUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link Copier}.
 */
class CopierTests {
    private Random random;

    @BeforeEach
    void setUp() {
        long seed = new Random().nextLong();
        System.out.println("Seed is " + seed);
        this.random = new Random(seed);
    }

    @RepeatedTest(10)
    void test(@TempDir Path tempDir) throws IOException {
        Path source = createSource(tempDir, this.random, DataSize.ofMegaBytes(this.random.nextInt(1, 11)));
        Path target = Files.createTempFile(tempDir, "target", ".bin");
        Path targetIndex = Files.createTempFile(tempDir, "target", ".index");

        Copier copier = new Copier(new Hasher(HashAlgorithm.SHA256), DataSize.ofBytes(this.random.nextInt(1, 1025)), NoopProcessReporter.INSTANCE);
        copier.copy(source, target, targetIndex);
        assertThat(source).hasSameBinaryContentAs(target);

        modify(this.random, source);

        copier.copy(source, target, targetIndex);
        assertThat(source).hasSameBinaryContentAs(target);
    }

    private void modify(Random random, Path source) throws IOException {
        try (SeekableByteChannel channel = Files.newByteChannel(source, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            long positionToChange = random.nextLong(0, channel.size());
            channel.position(positionToChange);
            ByteBuffer buffer = ByteBuffer.allocate(1);
            ChannelUtils.read(channel, buffer);
            byte toWrite = (byte) (buffer.get(0) == 0 ? 1 : 0);
            buffer.put(toWrite);
            buffer.flip();
            channel.write(buffer);
        }
    }

    private Path createSource(Path tempDir, Random random, DataSize dataSize) throws IOException {
        Path source = Files.createTempFile(tempDir, "source", ".bin");
        long toWrite = dataSize.bytes();
        long written = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
        try (SeekableByteChannel channel = Files.newByteChannel(source, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            while (written < toWrite) {
                random.nextBytes(buffer.array());
                buffer.position(0);
                written += ChannelUtils.write(channel, buffer);
            }
        }
        return source;
    }
}
