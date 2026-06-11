package net.minecraft.client.gui.spectator.categories;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.spectator.PlayerMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.GameType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/categories/TeleportToPlayerMenuCategory.class */
public class TeleportToPlayerMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
    private static final Identifier TELEPORT_TO_PLAYER_SPRITE = Identifier.withDefaultNamespace("spectator/teleport_to_player");
    private static final Comparator<PlayerInfo> PROFILE_ORDER = Comparator.comparing($$0 -> {
        return $$0.getProfile().id();
    });
    private static final Component TELEPORT_TEXT = Component.translatable("spectatorMenu.teleport");
    private static final Component TELEPORT_PROMPT = Component.translatable("spectatorMenu.teleport.prompt");
    private final List<SpectatorMenuItem> items;

    public TeleportToPlayerMenuCategory() {
        this(Minecraft.getInstance().getConnection().getListedOnlinePlayers());
    }

    public TeleportToPlayerMenuCategory(Collection<PlayerInfo> $$0) {
        this.items = (List) $$0.stream().filter($$02 -> {
            return $$02.getGameMode() != GameType.SPECTATOR;
        }).sorted(PROFILE_ORDER).map(PlayerMenuItem::new).collect(Collectors.toUnmodifiableList());
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuCategory
    public List<SpectatorMenuItem> getItems() {
        return this.items;
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuCategory
    public Component getPrompt() {
        return TELEPORT_PROMPT;
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
    public void selectItem(SpectatorMenu $$0) {
        $$0.selectCategory(this);
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
    public Component getName() {
        return TELEPORT_TEXT;
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
    public void renderIcon(GuiGraphics $$0, float $$1, float $$2) {
        $$0.blitSprite(RenderPipelines.GUI_TEXTURED, TELEPORT_TO_PLAYER_SPRITE, 0, 0, 16, 16, ARGB.colorFromFloat($$2, $$1, $$1, $$1));
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
    public boolean isEnabled() {
        return !this.items.isEmpty();
    }
}
