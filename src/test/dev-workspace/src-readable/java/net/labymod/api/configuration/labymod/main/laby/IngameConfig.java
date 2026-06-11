package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MeasurementConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.property.ConfigProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/main/laby/IngameConfig.class */
public interface IngameConfig extends ConfigAccessor {
    ConfigProperty<Boolean> mouseDelayFix();

    ConfigProperty<Boolean> hudWidgets();

    AdvancedChatConfig advancedChat();

    ChatInputConfig chatInput();

    ConfigProperty<Boolean> inventoryBanner();

    CosmeticsConfig cosmetics();

    EmotesConfig emotes();

    SprayConfig spray();

    ConfigProperty<Boolean> lootBoxes();

    ConfigProperty<Boolean> showCapeParticles();

    ConfigProperty<Boolean> hideTotemInOffHand();

    ConfigProperty<Boolean> hideNametag();

    ConfigProperty<Boolean> showMyName();

    ConfigProperty<Boolean> showCountryFlag();

    ConfigProperty<Boolean> showUserIndicatorBesideName();

    ConfigProperty<Boolean> fastWorldLoading();

    ConfigProperty<Boolean> improvedTorchPlacementInOffHand();

    ConfigProperty<Boolean> noViewBobbing();

    FieldOfViewConfig fieldOfView();

    ZoomConfig zoom();

    MeasurementConfig measurement();

    DamageConfig damage();

    HitboxConfig hitbox();

    ConfigProperty<Boolean> translucentSkins();

    MotionBlurConfig motionBlur();

    ConfigProperty<Boolean> enableFullbright();
}
