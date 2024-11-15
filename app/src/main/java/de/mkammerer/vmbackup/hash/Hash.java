package de.mkammerer.vmbackup.hash;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HexFormat;

import de.mkammerer.vmbackup.util.ByteBufferUtils;

public record Hash(byte[] value) {
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(this.value);
    }

    public static Hash fromByteBuffer(ByteBuffer buffer) {
        return new Hash(ByteBufferUtils.toByteArray(buffer));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hash hash = (Hash) o;
        return Arrays.equals(this.value, hash.value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public String toString() {
        return HexFormat.of().formatHex(this.value);
    }
}
