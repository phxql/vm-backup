package de.mkammerer.vmbackup.hash;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.mkammerer.vmbackup.progress.DataSize;

/**
 * Warning: This class is not thread safe!
 */
public class Hasher {
	private final MessageDigest digest;

	private final DataSize hashSize;

	public Hasher(HashAlgorithm hashAlgorithm) {
		try {
			this.digest = MessageDigest.getInstance(hashAlgorithm.getId());
			this.hashSize = DataSize.ofBytes(this.digest.getDigestLength());
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Failed to get %s digest".formatted(hashAlgorithm.getId()), e);
		}
	}

	public DataSize getHashSize() {
		return this.hashSize;
	}

	public Hash hash(ByteBuffer buffer) {
		buffer.mark();
		this.digest.update(buffer);
		buffer.reset();
		return new Hash(this.digest.digest());
	}
}
