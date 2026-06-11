package net.labymod.api.util.time;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/time/ModernTickDeltaTimer.class */
public class ModernTickDeltaTimer {
    private float tickDelta;
    private long lastMs;
    private final float msPerTick;

    public ModernTickDeltaTimer() {
        this(20.0f);
    }

    private ModernTickDeltaTimer(float ticksPerSecond) {
        this.msPerTick = 1000.0f / ticksPerSecond;
        this.lastMs = getCurrentMillis();
    }

    public void advanceTime() {
        long currentTimeMillis = getCurrentMillis();
        this.tickDelta = (currentTimeMillis - this.lastMs) / this.msPerTick;
        this.lastMs = currentTimeMillis;
    }

    public float getTickDelta() {
        return this.tickDelta;
    }

    public long getCurrentMillis() {
        return TimeUtil.getNanoTime() / 1000000;
    }
}
