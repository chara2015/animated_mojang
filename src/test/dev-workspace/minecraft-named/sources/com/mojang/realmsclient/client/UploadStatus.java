package com.mojang.realmsclient.client;

import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/UploadStatus.class */
public class UploadStatus {
    private volatile long bytesWritten;
    private volatile long totalBytes;
    private long previousTimeSnapshot = Util.getMillis();
    private long previousBytesWritten;
    private long bytesPerSecond;

    public void setTotalBytes(long $$0) {
        this.totalBytes = $$0;
    }

    public void restart() {
        this.bytesWritten = 0L;
        this.previousTimeSnapshot = Util.getMillis();
        this.previousBytesWritten = 0L;
        this.bytesPerSecond = 0L;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }

    public long getBytesWritten() {
        return this.bytesWritten;
    }

    public void onWrite(long $$0) {
        this.bytesWritten = $$0;
    }

    public boolean uploadStarted() {
        return this.bytesWritten > 0;
    }

    public boolean uploadCompleted() {
        return this.bytesWritten >= this.totalBytes;
    }

    public double getPercentage() {
        return Math.min(getBytesWritten() / getTotalBytes(), 1.0d);
    }

    public void refreshBytesPerSecond() {
        long $$0 = Util.getMillis();
        long $$1 = $$0 - this.previousTimeSnapshot;
        if ($$1 < 1000) {
            return;
        }
        long $$2 = this.bytesWritten;
        this.bytesPerSecond = (1000 * ($$2 - this.previousBytesWritten)) / $$1;
        this.previousBytesWritten = $$2;
        this.previousTimeSnapshot = $$0;
    }

    public long getBytesPerSecond() {
        return this.bytesPerSecond;
    }
}
