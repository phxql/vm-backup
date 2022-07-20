package de.mkammerer.vmbackup.util;

import java.nio.ByteBuffer;

public final class ByteBufferUtils {
	private ByteBufferUtils() {
	}

	public static byte[] toByteArray(ByteBuffer buffer) {
		buffer.mark();
		byte[] data = new byte[buffer.remaining()];
		buffer.get(data);
		buffer.reset();
		return data;
	}
}
