package net.labymod.core.client.accessor.gui;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/accessor/gui/StringWidgetAccessor.class */
public interface StringWidgetAccessor {
    boolean isBasedOnTextWidth();

    default int getTextColor() {
        return -1;
    }

    default boolean isClipped() {
        return false;
    }

    default float getMaxWidth() {
        return 0.0f;
    }
}
