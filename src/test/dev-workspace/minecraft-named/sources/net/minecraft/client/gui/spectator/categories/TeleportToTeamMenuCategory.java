package net.minecraft.client.gui.spectator.categories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.level.GameType;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/categories/TeleportToTeamMenuCategory.class */
public class TeleportToTeamMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
    private static final Identifier TELEPORT_TO_TEAM_SPRITE = Identifier.withDefaultNamespace("spectator/teleport_to_team");
    private static final Component TELEPORT_TEXT = Component.translatable("spectatorMenu.team_teleport");
    private static final Component TELEPORT_PROMPT = Component.translatable("spectatorMenu.team_teleport.prompt");
    private final List<SpectatorMenuItem> items;

    public TeleportToTeamMenuCategory() {
        Minecraft $$0 = Minecraft.getInstance();
        this.items = createTeamEntries($$0, $$0.level.getScoreboard());
    }

    private static List<SpectatorMenuItem> createTeamEntries(Minecraft $$0, Scoreboard $$1) {
        return $$1.getPlayerTeams().stream().flatMap($$12 -> {
            return TeamSelectionItem.create($$0, $$12).stream();
        }).toList();
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
        $$0.blitSprite(RenderPipelines.GUI_TEXTURED, TELEPORT_TO_TEAM_SPRITE, 0, 0, 16, 16, ARGB.colorFromFloat($$2, $$1, $$1, $$1));
    }

    @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
    public boolean isEnabled() {
        return !this.items.isEmpty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/spectator/categories/TeleportToTeamMenuCategory$TeamSelectionItem.class */
    static class TeamSelectionItem implements SpectatorMenuItem {
        private final PlayerTeam team;
        private final Supplier<PlayerSkin> iconSkin;
        private final List<PlayerInfo> players;

        private TeamSelectionItem(PlayerTeam $$0, List<PlayerInfo> $$1, Supplier<PlayerSkin> $$2) {
            this.team = $$0;
            this.players = $$1;
            this.iconSkin = $$2;
        }

        public static Optional<SpectatorMenuItem> create(Minecraft $$0, PlayerTeam $$1) {
            List<PlayerInfo> $$2 = new ArrayList<>();
            for (String $$3 : $$1.getPlayers()) {
                PlayerInfo $$4 = $$0.getConnection().getPlayerInfo($$3);
                if ($$4 != null && $$4.getGameMode() != GameType.SPECTATOR) {
                    $$2.add($$4);
                }
            }
            if ($$2.isEmpty()) {
                return Optional.empty();
            }
            PlayerInfo $$5 = $$2.get(RandomSource.create().nextInt($$2.size()));
            Objects.requireNonNull($$5);
            return Optional.of(new TeamSelectionItem($$1, $$2, $$5::getSkin));
        }

        @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
        public void selectItem(SpectatorMenu $$0) {
            $$0.selectCategory(new TeleportToPlayerMenuCategory(this.players));
        }

        @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
        public Component getName() {
            return this.team.getDisplayName();
        }

        @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
        public void renderIcon(GuiGraphics $$0, float $$1, float $$2) {
            Integer $$3 = this.team.getColor().getColor();
            if ($$3 != null) {
                float $$4 = (($$3.intValue() >> 16) & 255) / 255.0f;
                float $$5 = (($$3.intValue() >> 8) & 255) / 255.0f;
                float $$6 = ($$3.intValue() & 255) / 255.0f;
                $$0.fill(1, 1, 15, 15, ARGB.colorFromFloat($$2, $$4 * $$1, $$5 * $$1, $$6 * $$1));
            }
            PlayerFaceRenderer.draw($$0, this.iconSkin.get(), 2, 2, 12, ARGB.colorFromFloat($$2, $$1, $$1, $$1));
        }

        @Override // net.minecraft.client.gui.spectator.SpectatorMenuItem
        public boolean isEnabled() {
            return true;
        }
    }
}
