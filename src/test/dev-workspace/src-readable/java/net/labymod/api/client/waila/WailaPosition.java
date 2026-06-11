package net.labymod.api.client.waila;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/WailaPosition.class */
public enum WailaPosition {
    TOP,
    BOTTOM,
    LEFT,
    MIDDLE,
    RIGHT;

    private static final WailaPosition[] VALUES = values();

    public static WailaPosition[] getValues() {
        return VALUES;
    }

    public boolean isCustom() {
        return this == TOP || this == BOTTOM;
    }
}
