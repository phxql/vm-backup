package de.mkammerer.vmbackup.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public final class ChannelUtils {
	private ChannelUtils() {
	}

	/**
	 * Reads from the channel until the buffer is full or EOF is reached. It clears the buffer
	 * before reading and flips it afterwards.
	 *
	 * @param channel channel to read from
	 * @param buffer  buffer to read into
	 * @return true if there's more data in the channel (EOF hasn't been reached)
	 * @throws IOException if something went wrong
	 */
	public static boolean read(ReadableByteChannel channel, ByteBuffer buffer) throws IOException {
		buffer.clear();
		while (buffer.hasRemaining()) {
			if (channel.read(buffer) == -1) {
				// If buffer has no data in it, signal EOF
				boolean eof = buffer.position() == 0;
				buffer.flip();
				return !eof;
			}
		}
		buffer.flip();
		return true;
	}

	public static long write(WritableByteChannel channel, ByteBuffer buffer) throws IOException {
		long written = 0;
		while (buffer.hasRemaining()) {
			written = written + channel.write(buffer);
		}
		return written;
	}
}
