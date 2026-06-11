package net.minecraft.util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/TickThrottler.class */
public class TickThrottler {
    private final int incrementStep;
    private final int threshold;
    private int count;

    public TickThrottler(int $$0, int $$1) {
        this.incrementStep = $$0;
        this.threshold = $$1;
    }

    public void increment() {
        this.count += this.incrementStep;
    }

    public void tick() {
        if (this.count > 0) {
            this.count--;
        }
    }

    public boolean isUnderThreshold() {
        return this.count < this.threshold;
    }
}
