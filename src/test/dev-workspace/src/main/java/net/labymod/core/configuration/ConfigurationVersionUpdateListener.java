package net.labymod.core.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.labymod.api.Laby;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.core.client.gui.screen.theme.vanilla.VanillaTheme;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/ConfigurationVersionUpdateListener.class */
public class ConfigurationVersionUpdateListener {
    private static final String SHOW_CURRENT_SERVER_INFO_KEY = "showCurrentServerInfo";

    @Subscribe
    public void onConfigurationVersionUpdate(ConfigurationVersionUpdateEvent event) {
        Class<? extends Config> configClass = event.getConfigClass();
        int usedVersion = event.getUsedVersion();
        if (MultiplayerConfig.class.isAssignableFrom(configClass)) {
            if (usedVersion == -1) {
                updateMultiplayerInitialVersion(event);
            }
            if (usedVersion <= 1) {
                updateMultiplayerVersionOneToTwo(event);
            }
        }
        if (RootChatTabConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            updateChatTabInitialVersion(event);
        }
        if (OtherConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            Laby.references().legacyConfigConverter().getConverters();
            JsonObject jsonObject = event.getJsonObject();
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(new JsonPrimitive("labymod"));
            for (LoadedAddon loadedAddon : DefaultAddonService.getInstance().getLoadedAddons()) {
                jsonArray.add(new JsonPrimitive(loadedAddon.info().getNamespace()));
            }
            jsonObject.add("conversionAskedNamespaces", jsonArray);
            event.setJsonObject(jsonObject);
        }
        if (AppearanceConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            updateAppearanceInitialVersion(event);
        }
        if (ZoomConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            updateZoomInitialVersion(event);
        }
        if (IngameConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            updateIngameInitialVersion(event);
        }
        if (LabyConfig.class.isAssignableFrom(configClass) && usedVersion <= 1) {
            updateLabyInitialVersion(event);
        }
        if (LabyConfig.class.isAssignableFrom(configClass) && usedVersion <= 2) {
            updateLabyTwoToThreeVersion(event);
        }
        if (LabyConfig.class.isAssignableFrom(configClass) && usedVersion <= 3) {
            updateLabyThreeToFourVersion(event);
        }
    }

    private void updateMultiplayerVersionOneToTwo(ConfigurationVersionUpdateEvent event) {
        JsonObject multiplayerConfig = event.getEditedJsonObject() == null ? event.getJsonObject() : event.getEditedJsonObject();
        JsonObject tabListConfig = new JsonObject();
        if (multiplayerConfig.has("customPlayerList")) {
            tabListConfig.addProperty("enabled", Boolean.valueOf(multiplayerConfig.get("customPlayerList").getAsBoolean()));
        }
        if (multiplayerConfig.has("serverBanner")) {
            tabListConfig.addProperty("serverBanner", Boolean.valueOf(multiplayerConfig.get("serverBanner").getAsBoolean()));
        }
        if (multiplayerConfig.has("ping")) {
            JsonObject pingConfig = multiplayerConfig.get("ping").getAsJsonObject();
            JsonObject tabListPingConfig = new JsonObject();
            if (pingConfig.has("renderPing")) {
                tabListPingConfig.addProperty("exact", Boolean.valueOf(pingConfig.get("renderPing").getAsBoolean()));
            }
            if (pingConfig.has("exactPingColored")) {
                tabListPingConfig.addProperty("exactColored", Boolean.valueOf(pingConfig.get("exactPingColored").getAsBoolean()));
            }
            tabListConfig.add("ping", tabListPingConfig);
        }
        if (multiplayerConfig.has("userIndicator")) {
            JsonObject userIndicatorConfig = multiplayerConfig.get("userIndicator").getAsJsonObject();
            if (userIndicatorConfig.has("showUserIndicatorInPlayerList")) {
                tabListConfig.addProperty("labyModBadge", Boolean.valueOf(userIndicatorConfig.get("showUserIndicatorInPlayerList").getAsBoolean()));
            }
            if (userIndicatorConfig.has("showLabyModPlayerPercentageInTabList")) {
                tabListConfig.addProperty("labyModPercentage", Boolean.valueOf(userIndicatorConfig.get("showLabyModPlayerPercentageInTabList").getAsBoolean()));
            }
        }
        multiplayerConfig.add("tabList", tabListConfig);
        event.setJsonObject(multiplayerConfig);
    }

    private void updateAppearanceInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject appearanceConfig = event.getJsonObject();
        JsonObject customTitleScreenObject = new JsonObject();
        if (appearanceConfig.has("customTitleScreen")) {
            customTitleScreenObject.addProperty("enabled", Boolean.valueOf(appearanceConfig.get("customTitleScreen").getAsBoolean()));
            appearanceConfig.remove("customTitleScreen");
        }
        if (appearanceConfig.has("socialMediaLinks")) {
            customTitleScreenObject.addProperty("socialMediaLinks", appearanceConfig.get("socialMediaLinks").getAsString());
        }
        appearanceConfig.add("customTitleScreen", customTitleScreenObject);
        event.setJsonObject(appearanceConfig);
    }

    private void updateZoomInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject zoomConfig = event.getJsonObject();
        if (!zoomConfig.has("zoomTransition") && zoomConfig.has("zoomInType") && zoomConfig.has("zoomInDuration") && zoomConfig.has("zoomOutType") && zoomConfig.has("zoomOutDuration")) {
            JsonObject obj = new JsonObject();
            obj.addProperty("enabled", true);
            obj.addProperty("zoomInType", zoomConfig.get("zoomInType").getAsString());
            obj.addProperty("zoomInDuration", Long.valueOf(zoomConfig.get("zoomInDuration").getAsLong()));
            obj.addProperty("zoomOutType", zoomConfig.get("zoomOutType").getAsString());
            obj.addProperty("zoomOutDuration", Long.valueOf(zoomConfig.get("zoomOutDuration").getAsLong()));
            zoomConfig.add("zoomTransition", obj);
        }
        event.setJsonObject(zoomConfig);
    }

    private void updateChatTabInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonElement jsonObject;
        JsonObject jsonObject2 = event.getJsonObject();
        if (!jsonObject2.has("config")) {
            return;
        }
        JsonObject configObject = jsonObject2.getAsJsonObject("config");
        if (!configObject.has("filters")) {
            return;
        }
        if (configObject.has("general")) {
            jsonObject = configObject.getAsJsonObject("general");
        } else {
            jsonObject = new JsonObject();
            configObject.add("general", jsonObject);
        }
        jsonObject.add("filters", configObject.get("filters"));
        JsonObject copy = jsonObject.deepCopy();
        jsonObject2.remove("config");
        jsonObject2.add("config", copy);
        event.setJsonObject(jsonObject2);
    }

    private void updateMultiplayerInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject jsonObject = event.getJsonObject();
        if (jsonObject.has(SHOW_CURRENT_SERVER_INFO_KEY) && jsonObject.get(SHOW_CURRENT_SERVER_INFO_KEY).isJsonPrimitive()) {
            JsonPrimitive showCurrentServerInfo = jsonObject.get(SHOW_CURRENT_SERVER_INFO_KEY).getAsJsonPrimitive();
            if (showCurrentServerInfo.isBoolean()) {
                boolean showCurrentServerInfoValue = showCurrentServerInfo.getAsBoolean();
                if (showCurrentServerInfoValue) {
                    jsonObject.addProperty(SHOW_CURRENT_SERVER_INFO_KEY, MultiplayerConfig.ServerInfoPosition.BELOW_BUTTONS.name());
                } else {
                    jsonObject.addProperty(SHOW_CURRENT_SERVER_INFO_KEY, MultiplayerConfig.ServerInfoPosition.DISABLED.name());
                }
                event.setJsonObject(jsonObject);
            }
        }
    }

    private void updateIngameInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject ingameConfig = event.getJsonObject();
        if (ingameConfig.has("performance")) {
            JsonObject performanceConfig = ingameConfig.getAsJsonObject("performance");
            if (performanceConfig.has("fastWorldLoading") && !ingameConfig.has("fastWorldLoading")) {
                ingameConfig.addProperty("fastWorldLoading", Boolean.valueOf(performanceConfig.get("fastWorldLoading").getAsBoolean()));
            }
        }
        if (ingameConfig.has(VanillaTheme.ID)) {
            JsonObject vanillaConfig = ingameConfig.getAsJsonObject(VanillaTheme.ID);
            if (vanillaConfig.has("clearTitles") && !ingameConfig.has("clearTitles")) {
                ingameConfig.addProperty("clearTitles", Boolean.valueOf(vanillaConfig.get("clearTitles").getAsBoolean()));
            }
        }
        event.setJsonObject(ingameConfig);
    }

    private void updateLabyInitialVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject labyConfig = event.getJsonObject();
        if (labyConfig.has("ingame") && labyConfig.has("appearance")) {
            JsonObject ingameConfig = labyConfig.getAsJsonObject("ingame");
            JsonObject appearanceConfig = labyConfig.getAsJsonObject("appearance");
            if (ingameConfig.has("performance")) {
                JsonObject performanceConfig = ingameConfig.getAsJsonObject("performance");
                if (performanceConfig.has("blurQuality") && !appearanceConfig.has("blurQuality")) {
                    appearanceConfig.addProperty("blurQuality", performanceConfig.get("blurQuality").getAsString());
                    event.setJsonObject(labyConfig);
                }
            }
        }
    }

    private void updateLabyTwoToThreeVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject config = event.getJsonObject();
        migrate(config, "/ingame/clearTitles", "/multiplayer/clearTitles");
        migrate(config, "/ingame/replaceSkinCustomization", "/appearance/replaceSkinCustomization");
        migrate(config, "/appearance/blurQuality", "/ingame/motionBlur/blurQuality");
        migrate(config, "/other/borderlessWindow", "/other/window/borderlessWindow");
        migrate(config, "/other/restoreResolution", "/other/window/restoreWindowResolution");
        event.setJsonObject(config);
    }

    private void updateLabyThreeToFourVersion(ConfigurationVersionUpdateEvent event) {
        JsonObject config = event.getJsonObject();
        migrate(config, "/ingame/fastLanguageReload", "/other/fastLanguageReload");
        event.setJsonObject(config);
    }

    private void migrate(JsonObject config, String oldPath, String newPath) {
        JsonObject oldObject = getPath(config, oldPath);
        JsonObject newObject = getPath(config, newPath);
        String lastOldComponent = getEntryName(oldPath);
        String lastNewComponent = getEntryName(newPath);
        JsonElement oldElement = oldObject.get(lastOldComponent);
        if (oldElement == null) {
            return;
        }
        newObject.add(lastNewComponent, oldElement);
    }

    private JsonObject getPath(JsonObject config, String path) {
        String[] split = path.split("/");
        JsonObject asJsonObject = config;
        for (int i = 0; i < split.length; i++) {
            String component = split[i];
            if (!component.isEmpty() && i != split.length - 1) {
                if (!asJsonObject.isJsonObject()) {
                    throw new IllegalStateException("Expected JsonObject at " + path);
                }
                JsonObject currentObject = asJsonObject.getAsJsonObject();
                if (!currentObject.has(component)) {
                    currentObject.add(component, new JsonObject());
                }
                asJsonObject = currentObject.getAsJsonObject(component);
            }
        }
        return asJsonObject.getAsJsonObject();
    }

    private String getEntryName(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }
}
