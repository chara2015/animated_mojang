package net.labymod.core.client.gui.screen.key;

import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/key/MacOSMouse.class */
public final class MacOSMouse {
    public static double fixMouseScroll(double value, long windowHandle, double horizontal, double vertical) {
        if (OperatingSystem.isOSX()) {
            return value == 0.0d ? horizontal : value;
        }
        return value;
    }
}
