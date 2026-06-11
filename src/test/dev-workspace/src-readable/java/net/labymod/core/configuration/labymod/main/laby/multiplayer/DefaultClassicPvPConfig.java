package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.classicpvp.DefaultOldEquipConfig;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/multiplayer/DefaultClassicPvPConfig.class */
public class DefaultClassicPvPConfig extends Config implements ClassicPvPConfig {

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired(RangeOldAnimation.NAME)
    @SpriteSlot(x = 3, y = 1)
    private final ConfigProperty<Boolean> oldRange = new ConfigProperty<>(false);

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired(SlowdownOldAnimation.NAME)
    @SpriteSlot(x = 4, y = 1)
    private final ConfigProperty<Boolean> oldSlowdown = new ConfigProperty<>(false);

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 5, y = 1)
    @SettingSection("inventory")
    private final ConfigProperty<Boolean> oldSurvivalLayout = new ConfigProperty<>(false);

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 6, y = 1)
    private final ConfigProperty<Boolean> oldCreativeLayout = new ConfigProperty<>(false);

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 7, y = 1)
    private final ConfigProperty<Boolean> shouldRemoveRecipeBook = new ConfigProperty<>(false);

    @VersionCompatibility("1.8.9")
    @SpriteSlot(y = 2)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> potionFix = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 1, y = 2)
    @SettingSection("animations.item")
    private final ConfigProperty<Boolean> oldSword = new ConfigProperty<>(false);

    @SpriteSlot(x = 2, y = 2)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldItemPosture = new ConfigProperty<>(false);

    @SpriteSlot(x = 3, y = 2)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldBow = new ConfigProperty<>(false);

    @SpriteSlot(x = 4, y = 2)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldFood = new ConfigProperty<>(false);

    @SpriteSlot(x = 5, y = 2)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldFishingRod = new ConfigProperty<>(false);

    @SpriteSlot(x = 6, y = 2)
    @PermissionRequired("animations")
    private DefaultOldEquipConfig oldEquip = new DefaultOldEquipConfig();

    @VersionCompatibility("1.17<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 7, y = 2)
    @SettingSection("animations.player")
    private final ConfigProperty<Boolean> oldHeadRotation = new ConfigProperty<>(false);

    @VersionCompatibility("1.12<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(y = 3)
    private final ConfigProperty<Boolean> oldBackwards = new ConfigProperty<>(false);

    @SpriteSlot(x = 1, y = 3)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldSneaking = new ConfigProperty<>(false);

    @SpriteSlot(x = 2, y = 3)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("blockbuild")
    private final ConfigProperty<Boolean> oldBlockBuild = new ConfigProperty<>(false);

    @SpriteSlot(x = 3, y = 3)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldHeart = new ConfigProperty<>(false);

    @VersionCompatibility("1.8.9<1.21.1")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 4, y = 3)
    private final ConfigProperty<Boolean> oldDamageColor = new ConfigProperty<>(false);

    @SpriteSlot(x = 5, y = 3)
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    private final ConfigProperty<Boolean> oldHitbox = new ConfigProperty<>(false);

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldRange() {
        return this.oldRange;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldSlowdown() {
        return this.oldSlowdown;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldSurvivalLayout() {
        return this.oldSurvivalLayout;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldCreativeLayout() {
        return this.oldCreativeLayout;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> shouldRemoveRecipeBook() {
        return this.shouldRemoveRecipeBook;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> potionFix() {
        return this.potionFix;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldSword() {
        return this.oldSword;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldItemPosture() {
        return this.oldItemPosture;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldBow() {
        return this.oldBow;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldFood() {
        return this.oldFood;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldFishingRod() {
        return this.oldFishingRod;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public OldEquipConfig oldEquip() {
        return this.oldEquip;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldHeadRotation() {
        return this.oldHeadRotation;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldBackwards() {
        return this.oldBackwards;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldSneaking() {
        return this.oldSneaking;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldBlockBuild() {
        return this.oldBlockBuild;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldHeart() {
        return this.oldHeart;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldDamageColor() {
        return this.oldDamageColor;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig
    public ConfigProperty<Boolean> oldHitbox() {
        return this.oldHitbox;
    }
}
