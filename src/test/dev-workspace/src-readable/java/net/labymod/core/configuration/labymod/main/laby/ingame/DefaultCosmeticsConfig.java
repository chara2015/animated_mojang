package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingExperimental;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.user.shop.RenderMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/ingame/DefaultCosmeticsConfig.class */
@SettingRequires("renderCosmetics")
public class DefaultCosmeticsConfig extends Config implements CosmeticsConfig {

    @SpriteSlot(x = 2, y = 4)
    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    private final ConfigProperty<Boolean> renderCosmetics = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showCosmeticsInFirstPerson = new ConfigProperty<>(true);

    @DropdownWidget.DropdownSetting
    @SettingExperimental
    private final ConfigProperty<RenderMode> renderMode = ConfigProperty.createEnum(RenderMode.RETAINED);

    @DropdownWidget.DropdownSetting
    private final ConfigProperty<CloakPriority> cloakPriority = ConfigProperty.createEnum(CloakPriority.LABYMOD);

    @SpriteSlot(x = 3, y = 4)
    @SwitchWidget.SwitchSetting
    @SettingSection("hideInDistance")
    private final ConfigProperty<Boolean> hideCosmeticsInDistance = new ConfigProperty<>(true);

    @SliderWidget.SliderSetting(min = 3.0f, max = 32.0f)
    private final ConfigProperty<Integer> hideAfterBlocks = new ConfigProperty<>(4);

    @SliderWidget.SliderSetting(min = 5.0f, max = 256.0f)
    private final ConfigProperty<Integer> maxCosmeticsPerPlayer = new ConfigProperty<>(128);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> onlyFriends = new ConfigProperty<>(false);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> walkingPets = new ConfigProperty<>(true);

    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showWalkingPetNametags = new ConfigProperty<>(true);

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> renderCosmetics() {
        return this.renderCosmetics;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> showCosmeticsInFirstPerson() {
        return this.showCosmeticsInFirstPerson;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<RenderMode> renderMode() {
        return this.renderMode;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<CloakPriority> cloakPriority() {
        return this.cloakPriority;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> hideCosmeticsInDistance() {
        return this.hideCosmeticsInDistance;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Integer> hideAfterBlocks() {
        return this.hideAfterBlocks;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Integer> maxCosmeticsPerPlayer() {
        return this.maxCosmeticsPerPlayer;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> onlyFriends() {
        return this.onlyFriends;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> walkingPets() {
        return this.walkingPets;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig
    public ConfigProperty<Boolean> showWalkingPetNametags() {
        return this.showWalkingPetNametags;
    }
}
