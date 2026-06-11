package net.labymod.api.client.gui.hud.hudwidget.item;

import java.util.Locale;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/item/EquipmentWidgetConfig.class */
public class EquipmentWidgetConfig extends HudWidgetConfig {

    @DropdownWidget.DropdownSetting
    @CustomTranslation("labymod.hudWidget.equipmentCommon.displayMode")
    private final ConfigProperty<DisplayMode> displayMode = ConfigProperty.createEnum(DisplayMode.FULL);

    public ConfigProperty<DisplayMode> displayMode() {
        return this.displayMode;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/item/EquipmentWidgetConfig$DisplayMode.class */
    public enum DisplayMode {
        OFF,
        BAR,
        PERCENT,
        AVAILABLE,
        FULL;

        public String display(int durability, int maxDurability) {
            switch (this) {
                case OFF:
                    return "";
                case BAR:
                default:
                    throw new IllegalArgumentException("Unknown display mode: " + String.valueOf(this));
                case PERCENT:
                    return MathHelper.floor((durability / maxDurability) * 100.0f) + "%";
                case AVAILABLE:
                    return String.format(Locale.ROOT, "%d", Integer.valueOf(durability));
                case FULL:
                    return durability + "/" + maxDurability;
            }
        }
    }
}
