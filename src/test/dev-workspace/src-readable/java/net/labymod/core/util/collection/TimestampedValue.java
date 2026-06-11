package net.labymod.core.util.collection;

import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/collection/TimestampedValue.class */
public class TimestampedValue<T> {
    private final T value;
    private long timestamp;

    public TimestampedValue(T value) {
        this(TimeUtil.getCurrentTimeMillis(), value);
    }

    public TimestampedValue(long timestamp, T value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public T getValue() {
        updateTimestamp();
        return this.value;
    }

    public void updateTimestamp() {
        this.timestamp = TimeUtil.getCurrentTimeMillis();
    }
}
