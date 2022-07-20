package de.mkammerer.vmbackup.progress;

public interface ProgressReporter {
    void onStart(long total);

    void onProgress(long processed, long total);

    void onStop(long copied, long skipped, long total);
}