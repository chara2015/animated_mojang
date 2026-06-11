package net.labymod.v1_12_2.client;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/VersionedMinecraft.class */
public interface VersionedMinecraft {
    boolean dispatchKeyboardInput(LabyScreenRenderer labyScreenRenderer);

    boolean dispatchMouseInput();

    boolean handleMouseDragged(MutableMouse mutableMouse, MouseButton mouseButton, double d, double d2);

    MouseButton getCurrentEventButton();
}
