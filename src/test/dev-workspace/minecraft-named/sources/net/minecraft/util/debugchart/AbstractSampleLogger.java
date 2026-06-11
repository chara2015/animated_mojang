package net.minecraft.util.debugchart;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debugchart/AbstractSampleLogger.class */
public abstract class AbstractSampleLogger implements SampleLogger {
    protected final long[] defaults;
    protected final long[] sample;

    protected abstract void useSample();

    protected AbstractSampleLogger(int $$0, long[] $$1) {
        if ($$1.length != $$0) {
            throw new IllegalArgumentException("defaults have incorrect length of " + $$1.length);
        }
        this.sample = new long[$$0];
        this.defaults = $$1;
    }

    @Override // net.minecraft.util.debugchart.SampleLogger
    public void logFullSample(long[] $$0) {
        System.arraycopy($$0, 0, this.sample, 0, $$0.length);
        useSample();
        resetSample();
    }

    @Override // net.minecraft.util.debugchart.SampleLogger
    public void logSample(long $$0) {
        this.sample[0] = $$0;
        useSample();
        resetSample();
    }

    @Override // net.minecraft.util.debugchart.SampleLogger
    public void logPartialSample(long $$0, int $$1) {
        if ($$1 < 1 || $$1 >= this.sample.length) {
            throw new IndexOutOfBoundsException($$1 + " out of bounds for dimensions " + this.sample.length);
        }
        this.sample[$$1] = $$0;
    }

    protected void resetSample() {
        System.arraycopy(this.defaults, 0, this.sample, 0, this.defaults.length);
    }
}
