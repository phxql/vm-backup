package de.mkammerer.vmbackup.time;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

import de.mkammerer.vmbackup.util.FileUtils;

/**
 * @author Moritz Halbritter
 */
class LastModifiedImpl implements LastModified {

    @Override
    public boolean hasSameLastModified(Path source, Path target) {
        Instant sourceLastModified = FileUtils.getLastModified(source);
        Instant targetLastModified = FileUtils.getLastModified(target);
        return Objects.equals(sourceLastModified, targetLastModified);
    }

    @Override
    public void syncLastModified(Path source, Path target) {
        Instant sourceLastModified = FileUtils.getLastModified(source);
        if (sourceLastModified == null) {
            throw new IllegalStateException("Source '%s' doesn't exist".formatted(source));
        }
        FileUtils.setLastModified(target, sourceLastModified);
    }
}
