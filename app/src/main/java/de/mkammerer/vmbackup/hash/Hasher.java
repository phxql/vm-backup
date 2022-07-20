package de.mkammerer.vmbackup.hash;

import de.mkammerer.vmbackup.util.ByteBufferUtils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
	private static final String SHA_256 = "SHA-256";
	public static final int HASH_SIZE = 256 / 8;

	public Hash hash(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance(SHA_256);
			return new Hash(digest.digest(data));
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Failed to get SHA-256 digest", e);
		}
	}

	public Hash hash(ByteBuffer buffer) {
		return hash(ByteBufferUtils.toByteArray(buffer));
	}
}
