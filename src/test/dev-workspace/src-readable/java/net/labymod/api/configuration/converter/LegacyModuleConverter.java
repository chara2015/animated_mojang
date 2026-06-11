package net.labymod.api.configuration.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.Formatting;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.util.Color;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyModuleConverter.class */
public class LegacyModuleConverter extends LegacyConverter<JsonObject> {
    private static final JsonPrimitive INGAME = new JsonPrimitive("INGAME");
    private final Map<String, LegacyModuleEntry> legacyModules;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyModuleConverter$LegacyModuleConsumer.class */
    private interface LegacyModuleConsumer {
        void accept(@NotNull JsonObject jsonObject, @NotNull Map<String, String> map, @Nullable HudWidgetConfig hudWidgetConfig);
    }

    protected LegacyModuleConverter() {
        super("modules.json", JsonObject.class);
        this.legacyModules = new HashMap();
    }

    protected void registerModule(@NotNull String legacyModuleName, @Nullable String hudWidgetId, @Nullable BiConsumer<Map<String, String>, HudWidgetConfig> attributesConfigFiller) {
        this.legacyModules.put(legacyModuleName, new LegacyModuleEntry(this, legacyModuleName, hudWidgetId, (module, attributes, config) -> {
            if (attributesConfigFiller != null) {
                attributesConfigFiller.accept(attributes, config);
            }
        }));
    }

    protected void registerModule(@NotNull String legacyModuleName, @NotNull String hudWidgetId) {
        registerModule(legacyModuleName, hudWidgetId, null);
    }

    protected void registerModule(@NotNull String legacyModuleName, @NotNull BiConsumer<JsonObject, Map<String, String>> attributesConsumer) {
        this.legacyModules.put(legacyModuleName, new LegacyModuleEntry(this, legacyModuleName, null, (module, attributes, config) -> {
            attributesConsumer.accept(module, attributes);
        }));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        Formatting formatting;
        JsonObject modules = jsonObject.getAsJsonObject("modules");
        for (LegacyModuleEntry entry : this.legacyModules.values()) {
            String legacyModuleName = entry.legacyModuleName;
            JsonObject object = modules.getAsJsonObject(legacyModuleName);
            if (object != null) {
                Map<String, String> attributes = new HashMap<>();
                for (Map.Entry<String, JsonElement> attr : object.getAsJsonObject("attributes").entrySet()) {
                    attributes.put(attr.getKey(), attr.getValue().getAsString());
                }
                HudWidget<?> hudWidget = entry.getHudWidget();
                if (hudWidget == null) {
                    entry.configFiller.accept(object, attributes, null);
                } else {
                    HudWidgetConfig config = hudWidget.getConfig();
                    String rawRegion = object.getAsJsonArray("regions").get(0).getAsString();
                    String rawAlignment = object.getAsJsonArray("alignment").get(0).getAsString();
                    float x = object.getAsJsonArray("x").get(0).getAsFloat();
                    float y = object.getAsJsonArray("y").get(0).getAsFloat();
                    config.setEnabled(object.getAsJsonArray("enabled").contains(INGAME));
                    config.setAreaIdentifier(mapRegion(rawRegion));
                    config.horizontalAlignment().set(mapAlignment(rawAlignment));
                    config.setX(x);
                    config.setY(y);
                    config.setParentId(findParentHudWidgetIdRecursive(modules, object));
                    config.scale().set(Float.valueOf(object.get("scale").getAsInt() / 100.0f));
                    HudWidgetDropzone dropzone = getDropzone(legacyModuleName, attributes);
                    config.setDropzoneId(dropzone != null ? dropzone.getId() : null);
                    config.useGlobal().set(false);
                    if (config instanceof TextHudWidgetConfig) {
                        TextHudWidgetConfig textConfig = (TextHudWidgetConfig) config;
                        if (attributes.containsKey("customKey")) {
                            textConfig.customLabel().set(attributes.get("customKey"));
                        }
                        if (attributes.containsKey("prefixColor")) {
                            textConfig.labelColor().set(Color.of(Integer.parseInt(attributes.get("prefixColor"))));
                        }
                        if (attributes.containsKey("bracketsColor")) {
                            textConfig.bracketColor().set(Color.of(Integer.parseInt(attributes.get("bracketsColor"))));
                        }
                        if (attributes.containsKey("valueColor")) {
                            textConfig.valueColor().set(Color.of(Integer.parseInt(attributes.get("valueColor"))));
                        }
                        if (attributes.containsKey("formatting")) {
                            switch (Integer.parseInt(attributes.get("formatting"))) {
                                case 0:
                                case 3:
                                default:
                                    formatting = Formatting.SQUARE_BRACKETS;
                                    break;
                                case 1:
                                    formatting = Formatting.COLON;
                                    break;
                                case 2:
                                    formatting = Formatting.BRACKETS;
                                    break;
                                case 4:
                                    formatting = Formatting.HYPHEN;
                                    break;
                            }
                            textConfig.formatting().set(formatting);
                        }
                    }
                    entry.configFiller.accept(object, attributes, config);
                    config.useGlobal().set(Boolean.valueOf(!object.get("useExtendedSettings").getAsBoolean()));
                }
            }
        }
        Laby.references().hudWidgetRegistry().saveConfig();
        Laby.references().hudWidgetRegistry().updateHudWidgets("config_converter");
    }

    private HudWidgetDropzone getDropzone(String legacyModuleName, Map<String, String> attributes) {
        if (attributes.containsKey("itemSlot")) {
            switch (Integer.parseInt(attributes.get("itemSlot"))) {
                case 0:
                default:
                    return null;
                case 1:
                    return NamedHudWidgetDropzones.ITEM_TOP_LEFT;
                case 2:
                    return NamedHudWidgetDropzones.ITEM_MIDDLE_LEFT;
                case 3:
                    return NamedHudWidgetDropzones.ITEM_BOTTOM_LEFT;
                case 4:
                    return NamedHudWidgetDropzones.ITEM_TOP_RIGHT;
                case 5:
                    return NamedHudWidgetDropzones.ITEM_MIDDLE_RIGHT;
                case 6:
                    return NamedHudWidgetDropzones.ITEM_BOTTOM_RIGHT;
            }
        }
        if (legacyModuleName.equals("ScoreboardModule") && attributes.containsKey("slot")) {
            switch (Integer.parseInt(attributes.get("slot"))) {
                case 0:
                default:
                    return NamedHudWidgetDropzones.SCOREBOARD_RIGHT;
                case 1:
                    return NamedHudWidgetDropzones.SCOREBOARD_LEFT;
            }
        }
        return null;
    }

    private String findParentHudWidgetIdRecursive(@NotNull JsonObject modules, @NotNull JsonObject currentModule) {
        JsonElement listedAfter = currentModule.get("listedAfter");
        if (listedAfter == null || !listedAfter.isJsonPrimitive()) {
            return null;
        }
        String parentLegacyName = listedAfter.getAsString();
        String globalHudWidgetId = findGlobalHudWidgetId(parentLegacyName);
        if (globalHudWidgetId != null) {
            return globalHudWidgetId;
        }
        JsonObject parentModule = modules.getAsJsonObject(parentLegacyName);
        if (parentModule != null) {
            return findParentHudWidgetIdRecursive(modules, parentModule);
        }
        return null;
    }

    protected String findGlobalHudWidgetId(@NotNull String legacyModuleName) {
        LegacyModuleEntry entry;
        for (LegacyConverter<?> converter : Laby.references().legacyConfigConverter().getConverters()) {
            if ((converter instanceof LegacyModuleConverter) && (entry = ((LegacyModuleConverter) converter).legacyModules.get(legacyModuleName)) != null) {
                return entry.hudWidgetId;
            }
        }
        return null;
    }

    private RectangleAreaPosition mapRegion(String region) {
        switch (region) {
            case "BOTTOM_LEFT":
                return RectangleAreaPosition.BOTTOM_LEFT;
            case "BOTTOM_CENTER":
                return RectangleAreaPosition.BOTTOM_CENTER;
            case "BOTTOM_RIGHT":
                return RectangleAreaPosition.BOTTOM_RIGHT;
            case "TOP_LEFT":
                return RectangleAreaPosition.TOP_LEFT;
            case "TOP_CENTER":
                return RectangleAreaPosition.TOP_CENTER;
            case "TOP_RIGHT":
                return RectangleAreaPosition.TOP_RIGHT;
            case "CENTER_LEFT":
                return RectangleAreaPosition.MIDDLE_LEFT;
            case "CENTER":
                return RectangleAreaPosition.MIDDLE_CENTER;
            case "CENTER_RIGHT":
                return RectangleAreaPosition.MIDDLE_RIGHT;
            default:
                return RectangleAreaPosition.TOP_LEFT;
        }
    }

    private HorizontalHudWidgetAlignment mapAlignment(String alignment) {
        switch (alignment) {
            case "LEFT":
                return HorizontalHudWidgetAlignment.LEFT;
            case "RIGHT":
                return HorizontalHudWidgetAlignment.RIGHT;
            case "DEFAULT":
            case "AUTO":
            default:
                return HorizontalHudWidgetAlignment.AUTO;
        }
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        JsonObject modules;
        if (getValue() == null || (modules = getValue().getAsJsonObject("modules")) == null) {
            return false;
        }
        for (String legacyModuleName : this.legacyModules.keySet()) {
            if (modules.has(legacyModuleName)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyModuleConverter$LegacyModuleEntry.class */
    private class LegacyModuleEntry {
        private final String legacyModuleName;
        private final String hudWidgetId;
        private final LegacyModuleConsumer configFiller;

        private LegacyModuleEntry(LegacyModuleConverter legacyModuleConverter, String legacyModuleName, String hudWidgetId, LegacyModuleConsumer configFiller) {
            this.legacyModuleName = legacyModuleName;
            this.hudWidgetId = hudWidgetId;
            this.configFiller = configFiller;
        }

        private HudWidget<?> getHudWidget() {
            if (this.hudWidgetId == null) {
                return null;
            }
            HudWidget<?> hudWidget = Laby.references().hudWidgetRegistry().getById(this.hudWidgetId);
            if (hudWidget == null) {
                throw new IllegalArgumentException("Unknown hud widget: " + this.hudWidgetId);
            }
            return hudWidget;
        }
    }
}
