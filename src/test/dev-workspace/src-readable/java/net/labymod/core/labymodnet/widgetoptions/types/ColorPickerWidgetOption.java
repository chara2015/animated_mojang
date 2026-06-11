package net.labymod.core.labymodnet.widgetoptions.types;

import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.util.ColorUtil;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/types/ColorPickerWidgetOption.class */
public class ColorPickerWidgetOption extends WidgetOption {
    private static final String DEBOUNCE_ID = "color-picker-debounce";
    private static final String UPDATE_LISTENER_ID = "color-picker-cosmetic-option";

    public ColorPickerWidgetOption(String optionName, int optionIndex) {
        super(optionName, optionIndex);
    }

    @Override // net.labymod.core.labymodnet.widgetoptions.WidgetOption
    protected void create(String data, Consumer<Widget> consumer) {
        Color currentColor;
        try {
            currentColor = Color.of("#" + data);
        } catch (NumberFormatException e) {
            LOGGER.info("CosmeticWidget.getOptionWidgets: invalid color: " + data, new Object[0]);
            currentColor = Color.BLUE;
        }
        ColorPickerWidget colorPickerWidget = ColorPickerWidget.of(currentColor);
        colorPickerWidget.addUpdateListener(UPDATE_LISTENER_ID, color -> {
            setData(DEBOUNCE_ID, ColorUtil.rgbToHex(color.get()).substring(1));
        });
        consumer.accept(colorPickerWidget);
    }
}
