package net.labymod.api.client.gui;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/MinecraftWidgetBounds.class */
public interface MinecraftWidgetBounds {
    int getBoundsX();

    void setBoundsX(int i);

    int getBoundsY();

    void setBoundsY(int i);

    int getBoundsWidth();

    void setBoundsWidth(int i);

    int getBoundsHeight();

    void setBoundsHeight(int i);

    @Nullable
    static MinecraftWidgetBounds self(Object source) {
        if (!(source instanceof MinecraftWidgetBounds)) {
            return null;
        }
        MinecraftWidgetBounds widgetBounds = (MinecraftWidgetBounds) source;
        return widgetBounds;
    }
}
