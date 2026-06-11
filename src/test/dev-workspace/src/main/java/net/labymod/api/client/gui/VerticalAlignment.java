package net.labymod.api.client.gui;

import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/VerticalAlignment.class */
public enum VerticalAlignment {
    TOP,
    CENTER,
    BOTTOM;

    private static final VerticalAlignment[] VALUES = values();

    public VerticalAlignment opposite() {
        switch (this) {
            case TOP:
                return TOP;
            case CENTER:
                return CENTER;
            case BOTTOM:
                return BOTTOM;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this));
        }
    }

    public static VerticalAlignment of(WidgetAlignment alignment) {
        return alignment == WidgetAlignment.BOTTOM ? BOTTOM : alignment == WidgetAlignment.CENTER ? CENTER : TOP;
    }

    @Nullable
    public static VerticalAlignment of(String value) {
        if (value == null) {
            return null;
        }
        for (VerticalAlignment alignment : VALUES) {
            if (value.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }
}
