package net.labymod.core.event.client.render.entity;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/entity/PlayerRenderEvent.class */
public class PlayerRenderEvent implements Event {
    private final Phase phase;
    private final Stack stack;
    private final Player player;
    private final PlayerModel playerModel;
    private final float partialTicks;
    private final int packedLightCoords;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/entity/PlayerRenderEvent$Phase.class */
    public enum Phase {
        BEFORE,
        AFTER
    }

    public PlayerRenderEvent(Phase phase, Stack stack, Player player, PlayerModel playerModel, float partialTicks, int packedLightCoords) {
        this.phase = phase;
        this.stack = stack;
        this.player = player;
        this.playerModel = playerModel;
        this.partialTicks = partialTicks;
        this.packedLightCoords = packedLightCoords;
    }

    public Phase phase() {
        return this.phase;
    }

    public Stack stack() {
        return this.stack;
    }

    public Player player() {
        return this.player;
    }

    public PlayerModel playerModel() {
        return this.playerModel;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public int getPackedLightCoords() {
        return this.packedLightCoords;
    }
}
