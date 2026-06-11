package net.labymod.api.client.component.format;

import net.labymod.api.client.component.ComponentService;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/TextColor.class */
public interface TextColor {
    int getValue();

    String serialize();

    Color color();

    static TextColor color(int value) {
        return ComponentService.textColor(value);
    }

    static TextColor color(int red, int green, int blue) {
        return ComponentService.textColor(ColorFormat.ARGB32.pack(red, green, blue));
    }

    @Deprecated
    default int value() {
        return getValue();
    }
}
