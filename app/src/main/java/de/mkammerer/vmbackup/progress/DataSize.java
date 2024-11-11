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
		if (this.bytes >= BYTES_IN_TB) {
			return String.format(Locale.ROOT, "%.2f TiB", ((double) this.bytes) / BYTES_IN_TB);
        }
		if (this.bytes >= BYTES_IN_GB) {
			return String.format(Locale.ROOT, "%.2f GiB", ((double) this.bytes) / BYTES_IN_GB);
        }
		if (this.bytes >= BYTES_IN_MB) {
			return String.format(Locale.ROOT, "%.2f MiB", ((double) this.bytes) / BYTES_IN_MB);
		}
		if (this.bytes >= BYTES_IN_KB) {
			return String.format(Locale.ROOT, "%.2f KiB", ((double) this.bytes) / BYTES_IN_KB);
		}
		return String.format(Locale.ROOT, "%d B", this.bytes);
	}

	public static DataSize ofBytes(long bytes) {
		return new DataSize(bytes);
	}

	public static DataSize ofKiloBytes(long kiloBytes) {
		return ofBytes(kiloBytes * BYTES_IN_KB);
	}

	public static DataSize ofMegaBytes(long megaBytes) {
		return ofBytes(megaBytes * BYTES_IN_MB);
	}

	public static DataSize ofGigaBytes(long gigaBytes) {
		return ofBytes(gigaBytes * BYTES_IN_GB);
	}

	public static DataSize ofTeraBytes(long teraBytes) {
		return ofBytes(teraBytes * BYTES_IN_TB);
	}

	public static DataSize parse(String value) {
		if (value.endsWith("KiB")) {
			return DataSize.ofKiloBytes(parseLong(value, "KiB"));
		}
		if (value.endsWith("MiB")) {
			return DataSize.ofMegaBytes(parseLong(value, "MiB"));
		}
		if (value.endsWith("GiB")) {
			return DataSize.ofGigaBytes(parseLong(value, "GiB"));
		}
		if (value.endsWith("TiB")) {
			return DataSize.ofTeraBytes(parseLong(value, "TiB"));
		}
		return DataSize.ofBytes(Long.parseLong(value.trim()));
	}

	private static long parseLong(String value, String suffix) {
		return Long.parseLong(value.substring(0, value.length() - suffix.length()).trim());
	}
}
