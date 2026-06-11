package net.labymod.core.client.gui.screen.key.mapper;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.loader.platform.PlatformEnvironment;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/key/mapper/DefaultKey.class */
public abstract class DefaultKey {
    private final String keyName;
    private final int glfwKeyCode;
    private final int lwjglKeyCode;
    private final int keyCode;
    private boolean action;
    private char character = 0;

    protected abstract String getKeyName(int i);

    protected DefaultKey(Key key, int glfwKeyCode, int lwjglKeyCode) {
        this.keyName = key.getActualName();
        this.glfwKeyCode = glfwKeyCode;
        this.lwjglKeyCode = lwjglKeyCode;
        this.keyCode = PlatformEnvironment.isAncientOpenGL() ? lwjglKeyCode : glfwKeyCode;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public int getGlfwKeyCode() {
        return this.glfwKeyCode;
    }

    public int getLwjglKeyCode() {
        return this.lwjglKeyCode;
    }

    public char getCharacter() {
        if (this.action) {
            return this.character;
        }
        if (this.character == 0) {
            String keyName = getKeyName(this.keyCode);
            if (keyName == null || keyName.length() == 0) {
                this.action = true;
                return this.character;
            }
            this.character = keyName.charAt(0);
        }
        return this.character;
    }
}
