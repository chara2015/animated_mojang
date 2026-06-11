package net.labymod.api.client.gui;

import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/HorizontalAlignment.class */
public enum HorizontalAlignment {
    LEFT,
    CENTER,
    RIGHT,
    NONE;

    public static HorizontalAlignment[] VALUES = values();

    public HorizontalAlignment opposite() {
        if (this == LEFT) {
            return RIGHT;
        }
        if (this == RIGHT) {
            return LEFT;
        }
        return CENTER;
    }

    public static HorizontalAlignment of(WidgetAlignment alignment) {
        if (alignment == WidgetAlignment.CENTER) {
            return CENTER;
        }
        if (alignment == WidgetAlignment.RIGHT) {
            return RIGHT;
        }
        return LEFT;
    }

    @Nullable
    public static HorizontalAlignment of(String value) {
        if (value == null) {
            return null;
        }
        for (HorizontalAlignment alignment : VALUES) {
            if (value.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }
}
