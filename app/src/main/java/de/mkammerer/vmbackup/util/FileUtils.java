package de.mkammerer.vmbackup.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public final class FileUtils {
    private FileUtils() {
    }

    public static Instant getLastModified(Path file) {
        if (!Files.exists(file)) {
            return null;
        }
        try {
            return Files.getLastModifiedTime(file).toInstant();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to get last modified time", e);
        }
    }

    public static void setLastModified(Path file, Instant lastModified) {
        try {
            Files.setLastModifiedTime(file, FileTime.from(lastModified));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to set last modified time", e);
        }
    }
}
