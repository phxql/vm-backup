package de.mkammerer.vmbackup.hash;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Warning: This class is not thread safe!
 */
public class Hasher {
    private static final String SHA_256 = "SHA-256";
    public static final int HASH_SIZE = 256 / 8;

    private final MessageDigest digest;

    public Hasher() {
        try {
            this.digest = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Failed to get SHA-256 digest", e);
        }
    }

    public Hash hash(ByteBuffer buffer) {
        buffer.mark();
        this.digest.update(buffer);
        buffer.reset();
        return new Hash(this.digest.digest());
    }
}
