package net.labymod.v1_21_3.client.gui.screen.key.mapper;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKey;
import org.lwjgl.glfw.GLFW;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gui/screen/key/mapper/VersionedKey.class */
public class VersionedKey extends DefaultKey {
    public VersionedKey(Key key, int keyCode, int legacyKeyCode) {
        super(key, keyCode, legacyKeyCode);
    }

    @Override // net.labymod.core.client.gui.screen.key.mapper.DefaultKey
    protected String getKeyName(int keyCode) {
        return GLFW.glfwGetKeyName(keyCode, -1);
    }
}
