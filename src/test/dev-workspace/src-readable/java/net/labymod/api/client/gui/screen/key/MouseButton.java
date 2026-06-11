package net.labymod.api.client.gui.screen.key;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/MouseButton.class */
public class MouseButton extends Key {
    public static final Key LEFT = new MouseButton("LEFT").register().action();
    public static final Key RIGHT = new MouseButton("RIGHT").register().action();
    public static final Key MIDDLE = new MouseButton("MIDDLE").register().action();
    public static final Key M4 = new MouseButton("M4").register().action();
    public static final Key M5 = new MouseButton("M5").register().action();
    public static final Key M6 = new MouseButton("M6").register().action();
    public static final Key M7 = new MouseButton("M7").register().action();
    public static final Key M8 = new MouseButton("M8").register().action();

    protected MouseButton(String name) {
        super(name, "labymod.keys.mouse.");
    }

    @NotNull
    public static MouseButton get(int keyCode) {
        MouseButton mouseButton = KeyMapper.getMouseButton(keyCode);
        if (mouseButton != null) {
            return mouseButton;
        }
        MouseButton mouseButton2 = new MouseButton("M" + (keyCode + 1));
        mouseButton2.unknown = true;
        mouseButton2.id = keyCode;
        Laby.references().keyMapper().register(mouseButton2, keyCode, keyCode);
        return mouseButton2;
    }

    public boolean isLeft() {
        return this == LEFT;
    }

    public boolean isRight() {
        return this == RIGHT;
    }

    public boolean isMiddle() {
        return this == MIDDLE;
    }
}
