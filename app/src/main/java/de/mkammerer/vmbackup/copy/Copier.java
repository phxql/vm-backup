package de.mkammerer.vmbackup.copy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import de.mkammerer.vmbackup.hash.Hash;
import de.mkammerer.vmbackup.hash.Hasher;
import de.mkammerer.vmbackup.progress.DataSize;
import de.mkammerer.vmbackup.progress.ProgressReporter;
import de.mkammerer.vmbackup.util.ChannelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Copier {
	private static final Logger LOGGER = LoggerFactory.getLogger(Copier.class);

	private final Hasher hasher;

	private final DataSize blockSize;

	private final ProgressReporter progressReporter;

	public Copier(Hasher hasher, DataSize blockSize, ProgressReporter progressReporter) {
		this.hasher = hasher;
		this.blockSize = blockSize;
		this.progressReporter = progressReporter;
	}

	public void copy(Path source, Path target, Path targetIndex) throws IOException {
		ByteBuffer dataBuffer = ByteBuffer.allocateDirect((int) this.blockSize.bytes());
		ByteBuffer indexBuffer = ByteBuffer.allocateDirect((int) this.hasher.getHashSize().bytes());

		long copied = 0;
		long skipped = 0;
		long total;
		try (
				SeekableByteChannel sourceChannel = Files.newByteChannel(source, StandardOpenOption.READ);
				SeekableByteChannel targetChannel = Files.newByteChannel(target, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
				SeekableByteChannel targetIndexChannel = Files.newByteChannel(targetIndex, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE)
		) {
			total = sourceChannel.size();
			this.progressReporter.onStart(total);

            while (ChannelUtils.read(sourceChannel, dataBuffer)) {
                Hash sourceHash = this.hasher.hash(dataBuffer);

                boolean indexEof = !ChannelUtils.read(targetIndexChannel, indexBuffer);
                boolean chunkNeedsUpdate;
                if (indexEof) {
                    // New chunk
                    chunkNeedsUpdate = true;
                } else {
                    // Chunk is in index, compare hashes
                    Hash targetHash = Hash.fromByteBuffer(indexBuffer);
                    chunkNeedsUpdate = !sourceHash.equals(targetHash);
                }

                if (chunkNeedsUpdate) {
                    // Update data
                    long positionToUpdate = sourceChannel.position() - dataBuffer.remaining();
                    LOGGER.debug("Updating chunk of length {} at position {}", dataBuffer.remaining(), positionToUpdate);
                    targetChannel.position(positionToUpdate);
                    copied = copied + ChannelUtils.write(targetChannel, dataBuffer);

                    // Update index
                    long indexPositionToUpdate = targetIndexChannel.position() - indexBuffer.remaining();
                    LOGGER.debug("Updating index at position {}", indexPositionToUpdate);
                    targetIndexChannel.position(indexPositionToUpdate);
                    ChannelUtils.write(targetIndexChannel, sourceHash.toByteBuffer());
                } else {
                    skipped = skipped + dataBuffer.remaining();
                }

				this.progressReporter.onProgress(sourceChannel.position(), total);
            }

            // Remove superfluous target data at the end (happens if source file has been made smaller)
            targetChannel.truncate(total);
            // Remove superfluous index entries at the end (happens if source file has been made smaller)
            targetIndexChannel.truncate(targetIndexChannel.position());
        }

		this.progressReporter.onStop(copied, skipped, total);
    }
}
