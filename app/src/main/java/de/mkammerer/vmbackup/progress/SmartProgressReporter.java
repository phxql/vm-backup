package de.mkammerer.vmbackup.progress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class SmartProgressReporter implements ProgressReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmartProgressReporter.class);

    private int lastProgress = -1;

    @Override
    public void onStart(long total) {
        LOGGER.info("Copying started, source file size: {}", DataSize.of(total));
    }

    @Override
    public void onStop(long copied, long skipped, long total) {
        double efficiency = (skipped * 100.0 / total);
        LOGGER.info("Copying done. Copied {}, skipped {} ({} total): {}% efficiency", DataSize.of(copied), DataSize.of(skipped), DataSize.of(total), String.format(Locale.ROOT, "%.2f", efficiency));
    }

    @Override
    public void onProgress(long processed, long total) {
        int percent = (int) (processed * 100.0 / total);
        if (percent != this.lastProgress) {
            this.lastProgress = percent;
            LOGGER.info("{}% done ({})", percent, DataSize.of(processed));
        }
    }
}
