package net.labymod.api.event.client.render.model.entity.player;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/model/entity/player/PlayerModelRenderEvent.class */
public class PlayerModelRenderEvent extends RenderEvent implements Cancellable {
    private final Player player;
    private final PlayerModel model;
    private final int lightCoords;
    private final Type type;
    private boolean cancelled;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/model/entity/player/PlayerModelRenderEvent$Type.class */
    public enum Type {
        PLAYER,
        CAPE,
        ARMOR
    }

    public PlayerModelRenderEvent(@NotNull Player player, @NotNull PlayerModel model, @NotNull Stack stack, @NotNull Phase phase, int lightCoords) {
        this(player, model, stack, phase, lightCoords, Type.PLAYER);
    }

    public PlayerModelRenderEvent(@NotNull Player player, @NotNull PlayerModel model, @NotNull Stack stack, @NotNull Phase phase, int lightCoords, @NotNull Type type) {
        super(stack, phase);
        this.player = player;
        this.model = model;
        this.lightCoords = lightCoords;
        this.type = type;
    }

    @NotNull
    public Player player() {
        return this.player;
    }

    @NotNull
    public PlayerModel model() {
        return this.model;
    }

    public int getLightCoords() {
        return this.lightCoords;
    }

    public Type type() {
        return this.type;
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
