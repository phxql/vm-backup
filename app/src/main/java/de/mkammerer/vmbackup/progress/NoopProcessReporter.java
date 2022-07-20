package de.mkammerer.vmbackup.progress;

public enum NoopProcessReporter implements ProgressReporter {
    INSTANCE;

    @Override
    public void onStart(long total) {
    }

    @Override
    public void onProgress(long processed, long total) {
    }

    @Override
    public void onStop(long copied, long skipped, long total) {
    }

}
