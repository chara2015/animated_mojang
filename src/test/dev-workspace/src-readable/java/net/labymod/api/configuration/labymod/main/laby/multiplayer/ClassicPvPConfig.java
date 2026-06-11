package net.labymod.api.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/multiplayer/ClassicPvPConfig.class */
public interface ClassicPvPConfig extends ConfigAccessor {
    ConfigProperty<Boolean> oldRange();

    ConfigProperty<Boolean> oldSlowdown();

    ConfigProperty<Boolean> oldSurvivalLayout();

    ConfigProperty<Boolean> oldCreativeLayout();

    ConfigProperty<Boolean> shouldRemoveRecipeBook();

    ConfigProperty<Boolean> potionFix();

    ConfigProperty<Boolean> oldSword();

    ConfigProperty<Boolean> oldItemPosture();

    ConfigProperty<Boolean> oldBow();

    ConfigProperty<Boolean> oldFood();

    ConfigProperty<Boolean> oldFishingRod();

    OldEquipConfig oldEquip();

    ConfigProperty<Boolean> oldHeadRotation();

    ConfigProperty<Boolean> oldBackwards();

    ConfigProperty<Boolean> oldSneaking();

    ConfigProperty<Boolean> oldBlockBuild();

    ConfigProperty<Boolean> oldHeart();

    ConfigProperty<Boolean> oldDamageColor();

    ConfigProperty<Boolean> oldHitbox();
}
