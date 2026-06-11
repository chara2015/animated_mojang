package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/MotionBlurConfig.class */
public interface MotionBlurConfig {
    ConfigProperty<MotionBlurType> motionBlurType();

    ConfigProperty<Boolean> enabled();

    ConfigProperty<Integer> blurQuality();

    ConfigProperty<Float> blurStrength();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/MotionBlurConfig$MotionBlurType.class */
    public enum MotionBlurType {
        LABYMOD,
        MIX(true, 0.0f, 0.9f),
        MAX(true, 0.0f, 0.9f);

        private final boolean old;
        private final float min;
        private final float max;

        MotionBlurType() {
            this(false, 0.0f, 1.0f);
        }

        MotionBlurType(boolean old, float min, float max) {
            this.old = old;
            this.min = min;
            this.max = max;
        }

        public boolean isOld() {
            return this.old;
        }

        public float clamp(float value) {
            return MathHelper.clamp(value, this.min, this.max);
        }
    }
}
