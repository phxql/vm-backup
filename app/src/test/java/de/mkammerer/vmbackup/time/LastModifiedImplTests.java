package de.mkammerer.vmbackup.time;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LastModifiedImpl}.
 */
class LastModifiedImplTests {
    @Test
    void test(@TempDir Path tempDir) throws IOException, InterruptedException {
        Path source = Files.createTempFile(tempDir, "source", ".tmp");
        Thread.sleep(1000);
        Path target = Files.createTempFile(tempDir, "target", ".tmp");
        LastModified lastModified = LastModified.create();
        assertThat(lastModified.hasSameLastModified(source, target)).isFalse();
        lastModified.syncLastModified(source, target);
        assertThat(lastModified.hasSameLastModified(source, target)).isTrue();
    }
}
