package net.minecraft.client;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/InputType.class */
public enum InputType {
    NONE,
    MOUSE,
    KEYBOARD_ARROW,
    KEYBOARD_TAB;

    public boolean isMouse() {
        return this == MOUSE;
    }

    public boolean isKeyboard() {
        return this == KEYBOARD_ARROW || this == KEYBOARD_TAB;
    }
}
