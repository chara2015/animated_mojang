package net.labymod.core.client.render.schematic.lighting.legacy;

import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/lighting/legacy/LegacyLightEngine.class */
public interface LegacyLightEngine {
    public static final ConfigProperty<Float> brightnessSetting = Laby.labyAPI().config().appearance().dynamicBackground().brightness();

    int getLightAt(int i, int i2, int i3);

    void handleLightUpdates();

    boolean isInProgress();

    boolean isDirty();

    void reset();

    float getAverageLightLevelAt(int i, int i2, int i3);

    byte[] getData();

    void setData(byte[] bArr);

    default boolean isFullyCalculated() {
        return (isInProgress() || isDirty()) ? false : true;
    }

    default float getRedStrengthAt(int x, int y, int z) {
        return getStrengthAt(x, y, z);
    }

    default float getGreenStrengthAt(int x, int y, int z) {
        return getStrengthAt(x, y, z);
    }

    default float getBlueStrengthAt(int x, int y, int z) {
        return getStrengthAt(x, y, z);
    }

    default float getStrengthAt(int x, int y, int z) {
        return getStrengthFrom(getAverageLightLevelAt(x, y, z));
    }

    default float getStrengthFrom(float level) {
        return applyBrightness((0.06f * level) + 0.1f);
    }

    default float applyBrightness(float value) {
        return Math.min(value * brightnessSetting.get().floatValue(), 1.0f);
    }
}
