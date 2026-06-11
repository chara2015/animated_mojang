package net.labymod.v1_8_9.client;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_8_9.client.player.inventory.InventoryTracker;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/VersionedMinecraft.class */
public interface VersionedMinecraft {
    boolean dispatchKeyboardInput(LabyScreenRenderer labyScreenRenderer);

    boolean dispatchMouseInput();

    boolean handleMouseDragged(MutableMouse mutableMouse, MouseButton mouseButton, double d, double d2);

    MouseButton getCurrentEventButton();

    default void registerListeners() {
        Laby.labyAPI().eventBus().registerListener(new InventoryTracker());
    }
}
