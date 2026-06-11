package net.minecraft.client.gui.screens;

import net.minecraft.client.gui.components.Renderable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/Overlay.class */
public abstract class Overlay implements Renderable {
    public boolean isPauseScreen() {
        return true;
    }

    public void tick() {
    }
}
