package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.HorizontalAlignment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/WidgetAlignment.class */
public enum WidgetAlignment {
    LEFT,
    TOP,
    CENTER,
    RIGHT,
    BOTTOM;

    public static WidgetAlignment of(HorizontalAlignment alignment) {
        if (alignment == HorizontalAlignment.LEFT) {
            return LEFT;
        }
        if (alignment == HorizontalAlignment.RIGHT) {
            return RIGHT;
        }
        return CENTER;
    }
}
