package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/ingame/FieldOfViewConfig.class */
public interface FieldOfViewConfig extends ConfigAccessor {
    ConfigProperty<Boolean> enabled();

    ConfigProperty<Boolean> dynamicSprint();

    ConfigProperty<Boolean> dynamicStatusEffect();

    ConfigProperty<Boolean> dynamicBow();

    ConfigProperty<Float> vanilla();

    ConfigProperty<Boolean> overwriteWaterFov();

    ConfigProperty<Float> waterFov();

    ConfigProperty<Boolean> overwriteLavaFov();

    ConfigProperty<Float> lavaFov();

    ConfigProperty<Boolean> overwriteSnowPowderFov();

    ConfigProperty<Float> snowPowderFov();

    ConfigProperty<Boolean> bowPowerOverwrite();

    ConfigProperty<Float> bowPowerMultiplier();

    ConfigProperty<Boolean> flyingOverwrite();

    ConfigProperty<Float> flyingMultiplier();

    ConfigProperty<Boolean> sprintingOverwrite();

    ConfigProperty<Float> sprintingMultiplier();

    ConfigProperty<Boolean> speedOverwrite();

    ConfigProperty<Float> speedMultiplier();

    ConfigProperty<Boolean> slownessOverwrite();

    ConfigProperty<Float> slownessMultiplier();
}
