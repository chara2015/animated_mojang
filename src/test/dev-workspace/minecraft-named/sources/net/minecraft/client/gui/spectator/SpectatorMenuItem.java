package net.minecraft.client.gui.spectator;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/SpectatorMenuItem.class */
public interface SpectatorMenuItem {
    void selectItem(SpectatorMenu spectatorMenu);

    Component getName();

    void renderIcon(GuiGraphics guiGraphics, float f, float f2);

    boolean isEnabled();
}
