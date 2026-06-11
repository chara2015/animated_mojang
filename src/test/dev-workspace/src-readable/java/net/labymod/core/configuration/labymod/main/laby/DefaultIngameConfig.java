package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.MethodOrder;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultAdvancedChatConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultChatInputConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultCosmeticsConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultDamageConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultEmotesConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultFieldOfViewConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultHitboxConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMeasurementConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMotionBlurConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultSprayConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultZoomConfig;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/labymod/main/laby/DefaultIngameConfig.class */
public class DefaultIngameConfig extends Config implements IngameConfig {

    @VersionCompatibility("1.8.9")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fastWorldLoading = new ConfigProperty<>(true);

    @VersionCompatibility("1.8.9<1.11")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("crosshair_sync")
    @SpriteSlot(x = 7, y = 2, page = 1)
    private final ConfigProperty<Boolean> mouseDelayFix = new ConfigProperty<>(true);

    @SpriteSlot(x = 0, y = 4, page = 1)
    @IntroducedIn("4.1.0")
    @SettingSection("camera")
    private final DefaultMotionBlurConfig motionBlur = new DefaultMotionBlurConfig();

    @SpriteSlot(x = 7, y = 4)
    private DefaultZoomConfig zoom = new DefaultZoomConfig();

    @SpriteSlot(x = 6, y = 6, page = 1)
    private final DefaultFieldOfViewConfig fieldOfView = new DefaultFieldOfViewConfig();

    @SpriteSlot(x = 4, y = 6, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> noViewBobbing = new ConfigProperty<>(false);

    @SpriteSlot(x = 5, y = 6, page = 1)
    @SwitchWidget.SwitchSetting(hotkey = true)
    @IntroducedIn("4.3.0")
    private final ConfigProperty<Boolean> enableFullbright = new ConfigProperty<>(false);

    @SpriteSlot(x = 2, page = 1)
    @SwitchWidget.SwitchSetting
    @SettingSection("hud")
    private final ConfigProperty<Boolean> hudWidgets = new ConfigProperty<>(true);

    @SpriteSlot(x = 3, page = 1)
    private DefaultAdvancedChatConfig advancedChat = new DefaultAdvancedChatConfig();

    @SpriteSlot(x = 4, y = 4, page = 1)
    private DefaultChatInputConfig chatInput = new DefaultChatInputConfig();

    @SpriteSlot(x = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> inventoryBanner = new ConfigProperty<>(true);

    @SpriteSlot(x = 1, y = 4)
    @SettingSection("player")
    private DefaultCosmeticsConfig cosmetics = new DefaultCosmeticsConfig();

    @SpriteSlot(x = 4, y = 4)
    private DefaultEmotesConfig emotes = new DefaultEmotesConfig();

    @SpriteSlot(x = 0, y = 6, page = 1)
    @IntroducedIn("4.2.0")
    private DefaultSprayConfig spray = new DefaultSprayConfig();

    @SpriteSlot(x = 1, y = 6, page = 1)
    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.2.0")
    private final ConfigProperty<Boolean> lootBoxes = new ConfigProperty<>(true);

    @SpriteSlot(x = 5, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showCapeParticles = new ConfigProperty<>(true);

    @VersionCompatibility("1.11<*")
    @SpriteSlot(x = 1, y = 3, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> hideTotemInOffHand = new ConfigProperty<>(false);

    @VersionCompatibility("1.9<*")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3, y = 6, page = 1)
    @IntroducedIn("4.3.0")
    private final ConfigProperty<Boolean> improvedTorchPlacementInOffHand = new ConfigProperty<>(false);

    @SpriteSlot(x = 2, y = 6, page = 1)
    @IntroducedIn("4.3.0")
    private DefaultMeasurementConfig measurement = new DefaultMeasurementConfig();

    @SearchTag({"nick hider"})
    @SwitchWidget.SwitchSetting(hotkey = true)
    @SpriteSlot(x = 6, y = 5, page = 1)
    @SettingSection(ItemMetadata.NAMETAG_KEY)
    private final ConfigProperty<Boolean> hideNametag = new ConfigProperty<>(false);

    @SpriteSlot(x = 6, y = 3)
    @SwitchWidget.SwitchSetting
    @SettingRequires(value = "hideNametag", invert = true)
    private final ConfigProperty<Boolean> showMyName = new ConfigProperty<>(true);

    @SpriteSlot(x = 7, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showCountryFlag = new ConfigProperty<>(true);

    @SpriteSlot(x = 4, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showUserIndicatorBesideName = new ConfigProperty<>(true);

    @VersionCompatibility("1.8.9")
    @SwitchWidget.SwitchSetting
    @IntroducedIn("4.1.11")
    private final ConfigProperty<Boolean> translucentSkins = new ConfigProperty<>(true);

    @SpriteSlot(y = 1, page = 1)
    @SettingSection("entity")
    private DefaultDamageConfig damage = new DefaultDamageConfig();

    @SpriteSlot(x = 3, y = 1, page = 1)
    private DefaultHitboxConfig hitbox = new DefaultHitboxConfig();

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> mouseDelayFix() {
        return this.mouseDelayFix;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> hudWidgets() {
        return this.hudWidgets;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public AdvancedChatConfig advancedChat() {
        return this.advancedChat;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ChatInputConfig chatInput() {
        return this.chatInput;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> inventoryBanner() {
        return this.inventoryBanner;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public CosmeticsConfig cosmetics() {
        return this.cosmetics;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public EmotesConfig emotes() {
        return this.emotes;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public SprayConfig spray() {
        return this.spray;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> lootBoxes() {
        return this.lootBoxes;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> showCapeParticles() {
        return this.showCapeParticles;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> hideTotemInOffHand() {
        return this.hideTotemInOffHand;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> hideNametag() {
        return this.hideNametag;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> showMyName() {
        return this.showMyName;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> showCountryFlag() {
        return this.showCountryFlag;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> showUserIndicatorBesideName() {
        return this.showUserIndicatorBesideName;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> fastWorldLoading() {
        return this.fastWorldLoading;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> improvedTorchPlacementInOffHand() {
        return this.improvedTorchPlacementInOffHand;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> noViewBobbing() {
        return this.noViewBobbing;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public FieldOfViewConfig fieldOfView() {
        return this.fieldOfView;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ZoomConfig zoom() {
        return this.zoom;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public DefaultMeasurementConfig measurement() {
        return this.measurement;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public DamageConfig damage() {
        return this.damage;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public HitboxConfig hitbox() {
        return this.hitbox;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> translucentSkins() {
        return this.translucentSkins;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public MotionBlurConfig motionBlur() {
        return this.motionBlur;
    }

    @Override // net.labymod.api.configuration.labymod.main.laby.IngameConfig
    public ConfigProperty<Boolean> enableFullbright() {
        return this.enableFullbright;
    }

    @SpriteSlot(x = 1, y = 1)
    @MethodOrder(before = "fastWorldLoading")
    @ButtonWidget.ButtonSetting
    public void performance() {
        Setting fluxSetting = Laby.labyAPI().coreSettingRegistry().getById("flux");
        if (fluxSetting != null) {
            Laby.labyAPI().showSetting(fluxSetting);
        }
    }

    @Override // net.labymod.api.configuration.loader.Config
    public int getConfigVersion() {
        return 2;
    }
}
