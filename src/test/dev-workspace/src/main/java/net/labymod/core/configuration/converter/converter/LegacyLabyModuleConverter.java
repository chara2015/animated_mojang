package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.configuration.converter.LegacyModuleConverter;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.util.Color;
import net.labymod.core.client.gui.hud.hudwidget.PotionEffectHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.ArrowHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.AfkTimerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClickTestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClockHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CoordinateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.DateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.EntityCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.FDirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.MemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PingHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.SpeedHudWidget;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.class */
public class LegacyLabyModuleConverter extends LegacyModuleConverter {
    public LegacyLabyModuleConverter() {
        registerModule("AfkTimerModule", "afk_timer", (attributes, config) -> {
            ((AfkTimerHudWidget.AfkTimeHudWidgetConfig) config).idleTime().set(Integer.valueOf(Integer.parseInt((String) attributes.get("idleSeconds"))));
        });
        registerModule("BiomeModule", "biome");
        registerModule("ClickTestModule", "click_test", (attributes2, config2) -> {
            ClickTestHudWidget.ClickTestHudWidgetConfig c = (ClickTestHudWidget.ClickTestHudWidgetConfig) config2;
            c.displayAtZeroSpeed().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes2.get("displayAtZeroClicks"))));
        });
        registerModule("ClockModule", "clock", (attributes3, config3) -> {
            String format = (String) attributes3.get("timeFormat");
            ClockHudWidget.TimeFormat timeFormat = (ClockHudWidget.TimeFormat) Arrays.stream(ClockHudWidget.TimeFormat.values()).filter(value -> {
                return Objects.equals(value.getFormat(), format);
            }).findFirst().orElse(ClockHudWidget.TimeFormat.CUSTOM);
            ((ClockHudWidget.ClockHudWidgetConfig) config3).timeFormat().set(timeFormat);
            if (timeFormat == ClockHudWidget.TimeFormat.CUSTOM) {
                ((ClockHudWidget.ClockHudWidgetConfig) config3).customFormat().set(format);
            }
        });
        registerModule("ComboModule", "combo");
        registerModule("CoordinatesModule", "coordinates", (attributes4, config4) -> {
            ((CoordinateHudWidget.CoordinateHudWidgetConfig) config4).singleLine().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes4.get("displaySingleLine"))));
        });
        registerModule("DateModule", "date", (attributes5, config5) -> {
            String format = (String) attributes5.get("dateFormat");
            DateHudWidget.DateFormat dateFormat = (DateHudWidget.DateFormat) Arrays.stream(DateHudWidget.DateFormat.values()).filter(value -> {
                return Objects.equals(value.getFormat(), format);
            }).findFirst().orElse(DateHudWidget.DateFormat.CUSTOM);
            ((DateHudWidget.DateHudWidgetConfig) config5).dateFormat().set(dateFormat);
            if (dateFormat == DateHudWidget.DateFormat.CUSTOM) {
                ((DateHudWidget.DateHudWidgetConfig) config5).customFormat().set(format);
            }
        });
        registerModule("EntityCountModule", "entity_count", (attributes6, config6) -> {
            ((EntityCountHudWidget.EntityCountHudWidgetConfig) config6).showMaxEntityCount().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes6.get("showMaxEntityCount"))));
        });
        registerModule("FModule", "fdirection", (attributes7, config7) -> {
            ((FDirectionHudWidget.FDirectionHudWidgetConfig) config7).directionNumber().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes7.get("directionNumber"))));
            ((FDirectionHudWidget.FDirectionHudWidgetConfig) config7).cardinalPoints().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes7.get("cardinalPoints"))));
            ((FDirectionHudWidget.FDirectionHudWidgetConfig) config7).xzDirection().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes7.get("xzDirection"))));
            ((FDirectionHudWidget.FDirectionHudWidgetConfig) config7).shortenNames().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes7.get("shortenNames"))));
        });
        registerModule("FPSModule", "fps");
        registerModule("MemoryModule", "memory", (attributes8, config8) -> {
            MemoryHudWidget.MemoryType memoryType;
            String s0$ = (String) attributes8.get("memoryType");
            switch (s0$) {
                case "USED":
                    memoryType = MemoryHudWidget.MemoryType.USED;
                    break;
                case "USED_PERCENT":
                    memoryType = MemoryHudWidget.MemoryType.USED_PERCENT;
                    break;
                case "USED_SLASH_MAX":
                    memoryType = MemoryHudWidget.MemoryType.USED_SLASH_MAX;
                    break;
                case "USED_OF_MAX":
                    memoryType = MemoryHudWidget.MemoryType.USED_OF_MAX;
                    break;
                case "PERCENT":
                default:
                    memoryType = MemoryHudWidget.MemoryType.PERCENT;
                    break;
            }
            ((MemoryHudWidget.MemoryHudWidgetConfig) config8).memoryType().set(memoryType);
            ((MemoryHudWidget.MemoryHudWidgetConfig) config8).coloredNumbers().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes8.get("coloredNumber"))));
            ((MemoryHudWidget.MemoryHudWidgetConfig) config8).moreSpace().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes8.get("moreSpace"))));
        });
        registerModule("OnlinePlayersModule", "player_count");
        registerModule("PingModule", "ping", (attributes9, config9) -> {
            ((PingHudWidget.PingHudWidgetConfig) config9).displayAverage().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes9.get("displayAverage"))));
            ((PingHudWidget.PingHudWidgetConfig) config9).coloredPing().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes9.get("coloredPing"))));
            ((PingHudWidget.PingHudWidgetConfig) config9).averageColor().set(Color.of(Integer.parseInt((String) attributes9.get("averageColor"))));
        });
        registerModule("PotionEffectsModule", "potion", (attributes10, config10) -> {
            if (attributes10.containsKey("nameColor")) {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).nameColor().set(Integer.valueOf(Integer.parseInt((String) attributes10.get("nameColor"))));
            } else {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).nameColor().reset();
            }
            if (attributes10.containsKey("ampColor")) {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).amplifierColor().set(Integer.valueOf(Integer.parseInt((String) attributes10.get("ampColor"))));
            } else {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).amplifierColor().reset();
            }
            if (attributes10.containsKey("durationColor")) {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).durationColor().set(Integer.valueOf(Integer.parseInt((String) attributes10.get("durationColor"))));
            } else {
                ((PotionEffectHudWidget.PotionEffectHudWidgetConfig) config10).durationColor().reset();
            }
        });
        registerModule("RangeModule", RangeOldAnimation.NAME);
        registerModule("ScoreboardModule", "scoreboard", (attributes11, config11) -> {
            config11.scale().set(Float.valueOf(Integer.parseInt((String) attributes11.get(ItemMetadata.SIZE_KEY)) / 100.0f));
        });
        registerModule("ServerAddressModule", "server_address");
        registerModule("ServerInfoModule", (module, attributes12) -> {
            JsonArray enabledArray = module.getAsJsonArray("enabled");
            boolean enabled = enabledArray.contains(new JsonPrimitive("ESCAPE"));
            Laby.labyAPI().config().multiplayer().showCurrentServerInfo().set(enabled ? MultiplayerConfig.ServerInfoPosition.BELOW_BUTTONS : MultiplayerConfig.ServerInfoPosition.DISABLED);
        });
        registerModule("SpeedModule", "speed", (attributes13, config12) -> {
            ((SpeedHudWidget.SpeedHudWidgetConfig) config12).displayAtZeroSpeed().set(Boolean.valueOf(Boolean.parseBoolean((String) attributes13.get("displayAtZeroSpeed"))));
        });
        registerModule("YouTubeRealTimeModule", "service_youtube");
        registerEquipmentModule("BootsModule", "feet");
        registerEquipmentModule("LeggingsModule", "legs");
        registerEquipmentModule("ChestplateModule", "chest");
        registerEquipmentModule("HelmetModule", "helmet");
        registerEquipmentModule("HeldItemModule", "main_hand");
        registerModule("ArrowAmountModule", "arrow", (attributes14, config13) -> {
            ((ArrowHudWidget.ArrowHudWidgetConfig) config13).displayMode().set(ArrowHudWidget.ArrowHudWidgetConfig.DisplayMode.ALL);
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.configuration.converter.LegacyModuleConverter, net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        super.convert(jsonObject);
        GlobalHudWidgetConfig globalHudWidgetConfig = Laby.labyAPI().hudWidgetRegistry().globalHudWidgetConfig();
        globalHudWidgetConfig.labelColor().set(Color.of(jsonObject.get("prefixColor").getAsInt()));
        globalHudWidgetConfig.bracketColor().set(Color.of(jsonObject.get("bracketsColor").getAsInt()));
        globalHudWidgetConfig.valueColor().set(Color.of(jsonObject.get("valuesColor").getAsInt()));
        globalHudWidgetConfig.itemGravity().set(Boolean.valueOf(jsonObject.get("itemSlotGravity").getAsBoolean()));
        BackgroundConfig background = globalHudWidgetConfig.background();
        background.enabled().set(Boolean.valueOf(jsonObject.get("backgroundVisible").getAsBoolean()));
        int color = jsonObject.get("backgroundColor").getAsInt();
        int alpha = jsonObject.get("backgroundTransparency").getAsInt();
        background.color().set(Color.of(color, alpha));
        background.padding().set(Float.valueOf(jsonObject.get("padding").getAsInt()));
    }

    private void registerEquipmentModule(String legacyModuleName, String hudWidgetId) {
        registerModule(legacyModuleName, hudWidgetId, (attributes, config) -> {
            EquipmentWidgetConfig.DisplayMode displayMode;
            String s0$ = (String) attributes.get("visibility");
            switch (s0$) {
                case "DURABILITY_ONLY":
                    displayMode = EquipmentWidgetConfig.DisplayMode.AVAILABLE;
                    break;
                case "PERCENT":
                    displayMode = EquipmentWidgetConfig.DisplayMode.PERCENT;
                    break;
                case "OFF":
                    displayMode = EquipmentWidgetConfig.DisplayMode.OFF;
                    break;
                case "DURABILITY_MAX":
                case "DEFAULT":
                default:
                    displayMode = EquipmentWidgetConfig.DisplayMode.FULL;
                    break;
            }
            ((EquipmentWidgetConfig) config).displayMode().set(displayMode);
        });
    }
}
