package de.mkammerer.vmbackup.time;

import java.nio.file.Path;

public interface LastModified {

    boolean hasSameLastModified(Path source, Path target);

    void syncLastModified(Path source, Path target);

    static LastModified create() {
        return new LastModifiedImpl();
    }

    static LastModified noop() {
        return new LastModified() {
            @Override
            public boolean hasSameLastModified(Path source, Path target) {
                return false;
            }

            @Override
            public void syncLastModified(Path source, Path target) {
                // No-op
            }
        };
    }
}
