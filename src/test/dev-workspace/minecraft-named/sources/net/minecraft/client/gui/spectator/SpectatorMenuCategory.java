package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/SpectatorMenuCategory.class */
public interface SpectatorMenuCategory {
    List<SpectatorMenuItem> getItems();

    Component getPrompt();
}
