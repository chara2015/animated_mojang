package net.labymod.api.client.gui.screen.key;

import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/KeyHandler.class */
public final class KeyHandler {
    public static boolean isControlDown() {
        return isLeftControlDown() || isRightControlDown();
    }

    public static boolean isRightControlDown() {
        if (isMacOS()) {
            return isKeyPressed(Key.R_WIN);
        }
        return isKeyPressed(Key.R_CONTROL);
    }

    public static boolean isLeftControlDown() {
        if (isMacOS()) {
            return isKeyPressed(Key.L_WIN);
        }
        return isKeyPressed(Key.L_CONTROL);
    }

    public static boolean isAltDown() {
        return isKeyPressed(Key.L_ALT) || isKeyPressed(Key.R_ALT);
    }

    public static boolean isShiftDown() {
        return isKeyPressed(Key.L_SHIFT) || isKeyPressed(Key.R_SHIFT);
    }

    public static boolean isSelectAll(Key key) {
        return key == Key.A && isControlDown() && !isShiftDown() && !isAltDown();
    }

    public static boolean isSelectLeft(Key key) {
        return key == Key.HOME && !isControlDown() && isShiftDown() && !isAltDown();
    }

    public static boolean isSelectRight(Key key) {
        return key == Key.END && !isControlDown() && isShiftDown() && !isAltDown();
    }

    public static boolean isCopy(Key key) {
        return key == Key.C && isControlDown() && !isShiftDown() && !isAltDown();
    }

    public static boolean isPaste(Key key) {
        return key == Key.V && isControlDown() && !isShiftDown() && !isAltDown();
    }

    public static boolean isCut(Key key) {
        return key == Key.X && isControlDown() && !isShiftDown() && !isAltDown();
    }

    public static boolean isControl(Key key) {
        if (key != (isMacOS() ? Key.L_WIN : Key.L_CONTROL)) {
            if (key != (isMacOS() ? Key.R_WIN : Key.R_CONTROL)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEnter(Key key) {
        return key == Key.NUMPAD_ENTER || key == Key.ENTER;
    }

    private static boolean isMacOS() {
        return OperatingSystem.isOSX();
    }

    private static boolean isKeyPressed(Key key) {
        return KeyMapper.isPressed(key);
    }
}
