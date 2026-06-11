package net.minecraft.client.gui.spectator;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.gui.spectator.categories.TeleportToPlayerMenuCategory;
import net.minecraft.client.gui.spectator.categories.TeleportToTeamMenuCategory;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/RootSpectatorMenuCategory.class */
public class RootSpectatorMenuCategory implements SpectatorMenuCategory {
    private static final Component PROMPT_TEXT = Component.translatable("spectatorMenu.root.prompt");
    private final List<SpectatorMenuItem> items = Lists.newArrayList();

    public RootSpectatorMenuCategory() {
        this.items.add(new TeleportToPlayerMenuCategory());
        this.items.add(new TeleportToTeamMenuCategory());
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuCategory
    public List<SpectatorMenuItem> getItems() {
        return this.items;
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuCategory
    public Component getPrompt() {
        return PROMPT_TEXT;
    }
}
