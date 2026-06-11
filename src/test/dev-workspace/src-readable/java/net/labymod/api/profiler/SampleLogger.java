package net.labymod.api.profiler;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/SampleLogger.class */
public class SampleLogger {
    private static final int DEFAULT_CAPACITY = 240;
    private final int capacity;
    private final long[] samples;
    private int start;
    private int size;

    public SampleLogger() {
        this(240);
    }

    public SampleLogger(int capacity) {
        this.capacity = capacity;
        this.samples = new long[capacity];
    }

    public void log(long sample) {
        int wrappedIndex = wrapIndex(this.start + this.size);
        this.samples[wrappedIndex] = sample;
        if (this.size < this.capacity) {
            this.size++;
        } else {
            this.start = wrapIndex(this.start + 1);
        }
    }

    public long get(int index) {
        Objects.checkIndex(index, this.size);
        int wrappedIndex = wrapIndex(this.start + index);
        return this.samples[wrappedIndex];
    }

    public int capacity() {
        return this.capacity;
    }

    public int size() {
        return this.size;
    }

    public void reset() {
        this.start = 0;
        this.size = 0;
    }

    private int wrapIndex(int index) {
        return index % this.capacity;
    }
}
