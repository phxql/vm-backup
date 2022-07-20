package de.mkammerer.vmbackup.progress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LoggerProgressReporter implements ProgressReporter {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerProgressReporter.class);


    @Override
    public void onStart(long total) {
        LOGGER.info("Copying started, going to copy {} bytes", total);
    }

    @Override
    public void onProgress(long processed, long total) {
        LOGGER.info("Processed {} / {} bytes", processed, total);
    }

    @Override
    public void onStop(long copied, long skipped, long total) {
        LOGGER.info("Copied {} bytes, skipped {} bytes ({} total)", copied, skipped, total);
    }

}
