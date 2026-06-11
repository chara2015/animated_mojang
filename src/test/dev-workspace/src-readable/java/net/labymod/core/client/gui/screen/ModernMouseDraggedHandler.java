package net.labymod.core.client.gui.screen;

import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/ModernMouseDraggedHandler.class */
public class ModernMouseDraggedHandler implements VanillaLabyScreen.MouseDraggedHandler {
    @Override // net.labymod.api.client.gui.screen.VanillaLabyScreen.MouseDraggedHandler
    public FloatVector2 getDeltaMousePosition(Mouse mouse) {
        MutableMouse previousMouse = LabyMod.references().mouseBridge().previousMouse();
        return new FloatVector2(mouse.getX() - previousMouse.getX(), mouse.getY() - previousMouse.getY());
    }
}
