package net.minecraft.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/EndFlashState.class */
public class EndFlashState {
    public static final int SOUND_DELAY_IN_TICKS = 30;
    private static final int FLASH_INTERVAL_IN_TICKS = 600;
    private static final int MAX_FLASH_OFFSET_IN_TICKS = 200;
    private static final int MIN_FLASH_DURATION_IN_TICKS = 100;
    private static final int MAX_FLASH_DURATION_IN_TICKS = 380;
    private long flashSeed;
    private int offset;
    private int duration;
    private float intensity;
    private float oldIntensity;
    private float xAngle;
    private float yAngle;

    public void tick(long $$0) {
        calculateFlashParameters($$0);
        this.oldIntensity = this.intensity;
        this.intensity = calculateIntensity($$0);
    }

    private void calculateFlashParameters(long $$0) {
        long $$1 = $$0 / 600;
        if ($$1 != this.flashSeed) {
            RandomSource $$2 = RandomSource.create($$1);
            $$2.nextFloat();
            this.offset = Mth.randomBetweenInclusive($$2, 0, 200);
            this.duration = Mth.randomBetweenInclusive($$2, 100, Math.min(MAX_FLASH_DURATION_IN_TICKS, 600 - this.offset));
            this.xAngle = Mth.randomBetween($$2, -60.0f, 10.0f);
            this.yAngle = Mth.randomBetween($$2, -180.0f, 180.0f);
            this.flashSeed = $$1;
        }
    }

    private float calculateIntensity(long $$0) {
        long $$1 = $$0 % 600;
        if ($$1 < this.offset || $$1 > this.offset + this.duration) {
            return 0.0f;
        }
        return Mth.sin((($$1 - ((long) this.offset)) * 3.1415927f) / this.duration);
    }

    public float getXAngle() {
        return this.xAngle;
    }

    public float getYAngle() {
        return this.yAngle;
    }

    public float getIntensity(float $$0) {
        return Mth.lerp($$0, this.oldIntensity, this.intensity);
    }

    public boolean flashStartedThisTick() {
        return this.intensity > 0.0f && this.oldIntensity <= 0.0f;
    }
}
