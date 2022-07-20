package de.mkammerer.vmbackup.progress;

import java.util.Locale;

public record DataSize(long bytes) {
    private static final long BYTES_IN_KB = 1024;
    private static final long BYTES_IN_MB = 1024 * BYTES_IN_KB;
    private static final long BYTES_IN_GB = 1024 * BYTES_IN_MB;
    private static final long BYTES_IN_TB = 1024 * BYTES_IN_GB;

    public DataSize {
        if (bytes < 0) {
            throw new IllegalArgumentException("'bytes' must be >= 0, was %d".formatted(bytes));
        }
    }

    @Override
    public String toString() {
        if (bytes >= BYTES_IN_TB) {
            return String.format(Locale.ROOT, "%.2f TiB", ((double) bytes) / BYTES_IN_TB);
        }
        if (bytes >= BYTES_IN_GB) {
            return String.format(Locale.ROOT, "%.2f GiB", ((double) bytes) / BYTES_IN_GB);
        }
        if (bytes >= BYTES_IN_MB) {
            return String.format(Locale.ROOT, "%.2f MiB", ((double) bytes) / BYTES_IN_MB);
        }
        if (bytes >= BYTES_IN_KB) {
            return String.format(Locale.ROOT, "%.2f KiB", ((double) bytes) / BYTES_IN_KB);
        }
        return String.format(Locale.ROOT, "%d B", bytes);
    }

    public static DataSize of(long bytes) {
        return new DataSize(bytes);
    }
}
