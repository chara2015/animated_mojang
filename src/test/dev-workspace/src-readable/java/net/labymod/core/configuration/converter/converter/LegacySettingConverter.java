package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.controller.ZoomTransitionType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.user.shop.CloakPriority;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacySettingConverter.class */
public class LegacySettingConverter extends LegacyConverter<JsonObject> {
    public LegacySettingConverter() {
        super("LabyMod-3.json", JsonObject.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject value) throws Exception {
        convertIngameSettings(value);
        convertAppearanceSettings(value);
        convertMultiplayerSettings(value);
        convertOtherSettings(value);
        convertHotkeySettings(value);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        return Files.exists(this.path, new LinkOption[0]);
    }

    private void convertMultiplayerSettings(JsonObject jsonObject) {
        convertClassicPvPSettings(jsonObject);
        MultiplayerConfig multiplayer = Laby.labyAPI().config().multiplayer();
        multiplayer.serverList().liveServerList().set(Boolean.valueOf(jsonObject.get("serverlistLiveView").getAsBoolean()));
        multiplayer.serverList().cooldown().set(Integer.valueOf(jsonObject.get("serverlistLiveViewInterval").getAsInt()));
        multiplayer.confirmDisconnect().set(Boolean.valueOf(jsonObject.get("confirmDisconnect").getAsBoolean()));
        TabListConfig tabListConfig = multiplayer.tabList();
        tabListConfig.labyModBadge().set(Boolean.valueOf(jsonObject.get("revealFamiliarUsers").getAsBoolean()));
        tabListConfig.labyModPercentage().set(Boolean.valueOf(jsonObject.get("revealFamiliarUsersPercentage").getAsBoolean()));
        tabListConfig.showCountryFlags().set(Boolean.valueOf(jsonObject.get("showLanguageFlags").getAsBoolean()));
        PingConfig ping = multiplayer.ping();
        ping.exact().set(Boolean.valueOf(jsonObject.get("tabPing").getAsBoolean()));
        ping.exactColored().set(Boolean.valueOf(jsonObject.get("tabPing_colored").getAsBoolean()));
        multiplayer.serverBanner().set(Boolean.valueOf(jsonObject.get("serverBanner").getAsBoolean()));
    }

    private void convertClassicPvPSettings(JsonObject jsonObject) {
        ClassicPvPConfig classicPvP = Laby.labyAPI().config().multiplayer().classicPvP();
        classicPvP.oldRange().set(Boolean.valueOf(jsonObject.get("oldRange").getAsBoolean()));
        classicPvP.oldSlowdown().set(Boolean.valueOf(jsonObject.get("oldSlowdown").getAsBoolean()));
        classicPvP.oldItemPosture().set(Boolean.valueOf(jsonObject.get("oldItemHold").getAsBoolean()));
        classicPvP.oldEquip().enabled().set(Boolean.valueOf(jsonObject.get("oldItemSwitch").getAsBoolean()));
        classicPvP.oldFood().set(Boolean.valueOf(jsonObject.get("oldFood").getAsBoolean()));
        classicPvP.oldFishingRod().set(Boolean.valueOf(jsonObject.get("oldFishing").getAsBoolean()));
        classicPvP.oldSword().set(Boolean.valueOf(jsonObject.get("oldSword").getAsBoolean()));
        classicPvP.oldHeadRotation().set(Boolean.valueOf(jsonObject.get("oldHeadRotation").getAsBoolean()));
        classicPvP.oldBackwards().set(Boolean.valueOf(jsonObject.get("oldWalking").getAsBoolean()));
        classicPvP.oldSneaking().set(Boolean.valueOf(jsonObject.get("oldSneaking").getAsBoolean()));
        classicPvP.oldHeart().set(Boolean.valueOf(jsonObject.get("oldHearts").getAsBoolean()));
        classicPvP.oldDamageColor().set(Boolean.valueOf(jsonObject.get("oldDamage").getAsBoolean()));
        classicPvP.oldHitbox().set(Boolean.valueOf(jsonObject.get("oldHitbox").getAsBoolean()));
    }

    private void convertOtherSettings(JsonObject jsonObject) {
        OtherConfig other = Laby.labyAPI().config().other();
        DiscordConfig discord = other.discord();
        discord.enabled().set(Boolean.valueOf(jsonObject.get("discordRichPresence").getAsBoolean()));
        discord.showServerAddress().set(Boolean.valueOf(jsonObject.get("discordShowIpAddress").getAsBoolean()));
        other.window().borderlessWindow().set(Boolean.valueOf(jsonObject.get("borderlessWindow").getAsBoolean()));
        other.anonymousStatistics().set(Boolean.valueOf(jsonObject.get("sendAnonymousStatistics").getAsBoolean()));
    }

    private void convertAppearanceSettings(JsonObject jsonObject) {
        FadeOutAnimationType fadeOutAnimationType;
        AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        appearance.darkLoadingScreen().set(Boolean.valueOf(jsonObject.get("darkMode").getAsBoolean()));
        ConfigProperty<FadeOutAnimationType> configPropertyFadeOutAnimation = appearance.fadeOutAnimation();
        if (jsonObject.get("fadeOut").getAsBoolean()) {
            fadeOutAnimationType = FadeOutAnimationType.FADING;
        } else {
            fadeOutAnimationType = FadeOutAnimationType.INSTANT;
        }
        configPropertyFadeOutAnimation.set(fadeOutAnimationType);
        appearance.hideMenuBackground().set(Boolean.valueOf(!jsonObject.get("guiBackground").getAsBoolean()));
        appearance.cleanWindowTitle().set(Boolean.valueOf(jsonObject.get("cleanWindowTitle").getAsBoolean()));
        appearance.titleScreen().quickPlay().set(Boolean.valueOf(jsonObject.get("quickPlay").getAsBoolean()));
        appearance.replaceSkinCustomization().set(Boolean.valueOf(jsonObject.get("betterSkinCustomization").getAsBoolean()));
    }

    private void convertIngameSettings(JsonObject jsonObject) throws IOException {
        IngameConfig ingame = Laby.labyAPI().config().ingame();
        AdvancedChatConfig advancedChat = ingame.advancedChat();
        advancedChat.globalBackground().set(Boolean.valueOf(!jsonObject.get("fastChat").getAsBoolean()));
        advancedChat.fadeInMessages().set(Boolean.valueOf(jsonObject.get("chatAnimation").getAsBoolean()));
        advancedChat.globalChatLimit().set(Integer.valueOf(jsonObject.get("chatLineLimit").getAsInt()));
        CosmeticsConfig cosmetics = ingame.cosmetics();
        if (jsonObject.get("capePriority").getAsString().equalsIgnoreCase("labymod")) {
            cosmetics.cloakPriority().set(CloakPriority.LABYMOD);
        } else {
            cosmetics.cloakPriority().set(CloakPriority.VANILLA);
        }
        cosmetics.renderCosmetics().set(Boolean.valueOf(jsonObject.get("cosmetics").getAsBoolean()));
        cosmetics.hideCosmeticsInDistance().set(Boolean.valueOf(jsonObject.get("cosmeticsHideInDistance").getAsBoolean()));
        ingame.emotes().emotes().set(Boolean.valueOf(jsonObject.get("emotes").getAsBoolean()));
        ingame.showCapeParticles().set(Boolean.valueOf(jsonObject.get("capeOriginalParticles").getAsBoolean()));
        ingame.showMyName().set(Boolean.valueOf(jsonObject.get("showMyName").getAsBoolean()));
        convertZoomSettings(jsonObject, ingame);
    }

    private void convertZoomSettings(JsonObject jsonObject, IngameConfig ingame) throws IOException {
        ZoomTransitionType transitionType;
        Map<String, String> optifineSettings = loadMinecraftSettings(Paths.get("optionsof.txt", new String[0]));
        int ofZoomKey = 0;
        try {
            ofZoomKey = Integer.parseInt(optifineSettings.getOrDefault("key_of.key.zoom", "0"));
        } catch (NumberFormatException e) {
        }
        ZoomConfig zoom = ingame.zoom();
        if (ofZoomKey != 0) {
            zoom.zoomKey().set(KeyMapper.getKey(ofZoomKey));
            zoom.zoomHold().set(true);
            zoom.zoomCinematic().enabled().set(true);
            zoom.zoomCinematic().disableAfterTransition().set(false);
            zoom.shouldRenderFirstPersonHand().set(false);
            zoom.scrollToZoom().set(false);
            zoom.zoomDistance().set(Double.valueOf(4.0d));
            zoom.zoomTransition().zoomInType().set(ZoomTransitionType.NO_TRANSITION);
            zoom.zoomTransition().zoomInDuration().set(0L);
            zoom.zoomTransition().zoomOutType().set(ZoomTransitionType.NO_TRANSITION);
            zoom.zoomTransition().zoomOutDuration().set(0L);
            return;
        }
        if (jsonObject.has("zoom")) {
            zoom.zoomHold().set(Boolean.valueOf(jsonObject.get("zoomHoldKey").getAsBoolean()));
            zoom.zoomCinematic().enabled().set(Boolean.valueOf(jsonObject.get("zoomCinematic").getAsBoolean()));
            zoom.zoomCinematic().disableAfterTransition().reset();
            zoom.zoomDistance().set(Double.valueOf(jsonObject.get("zoomDivisor").getAsInt()));
            if (jsonObject.get("zoom").getAsBoolean()) {
                zoom.zoomKey().set(getKey(jsonObject.get("keyZoom").getAsInt()));
            } else {
                zoom.zoomKey().set(Key.NONE);
            }
            String rawTransitionType = jsonObject.get("zoomTransition").getAsString();
            if (rawTransitionType.equals("smooth")) {
                transitionType = ZoomTransitionType.EASE_IN_OUT;
            } else if (rawTransitionType.equals("linear")) {
                transitionType = ZoomTransitionType.LINEAR;
            } else {
                transitionType = ZoomTransitionType.NO_TRANSITION;
            }
            zoom.zoomTransition().zoomInType().set(transitionType);
            zoom.zoomTransition().zoomOutType().set(transitionType);
            zoom.zoomTransition().zoomInDuration().reset();
            zoom.zoomTransition().zoomOutDuration().reset();
            return;
        }
        zoom.zoomKey().set(Key.NONE);
    }

    private void convertHotkeySettings(JsonObject jsonObject) {
        HotkeysConfig hotkeys = Laby.labyAPI().config().hotkeys();
        hotkeys.emoteWheelKey().set(getKey(jsonObject.get("keyEmote").getAsInt()));
        hotkeys.widgetEditorKey().set(getKey(jsonObject.get("keyModuleEditor").getAsInt()));
        hotkeys.markerKey().set(getKey(jsonObject.get("keyMarker").getAsInt()));
        Key keyPlayerMenu = getKey(jsonObject.get("keyPlayerMenu").getAsInt());
        if (keyPlayerMenu == Key.NONE) {
            keyPlayerMenu = MouseButton.MIDDLE;
        }
        hotkeys.interactionMenuKey().set(keyPlayerMenu);
    }
}
