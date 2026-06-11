package net.labymod.api.event.client.render.model.entity.player;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/model/entity/player/PlayerCapeRenderEvent.class */
public class PlayerCapeRenderEvent extends RenderEvent implements Cancellable {
    private final Player player;
    private final PlayerModel playerModel;
    private boolean cancelled;

    public PlayerCapeRenderEvent(@NotNull Player player, @NotNull PlayerModel playerModel, @NotNull Stack stack, @NotNull Phase phase) {
        super(stack, phase);
        this.player = player;
        this.playerModel = playerModel;
    }

    @NotNull
    public Player player() {
        return this.player;
    }

    @NotNull
    public PlayerModel playerModel() {
        return this.playerModel;
    }

    @Override // net.labymod.api.event.Cancellable
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
