package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/HeartBarRenderEvent.class */
public class HeartBarRenderEvent extends IngameOverlayElementRenderEvent {
    private final Player player;
    private final int x;
    private final int y;
    private final int rowHeight;
    private final int offsetHeartIndex;
    private final int lowHealthJitter;
    private final float maxHealth;
    private final int playerHealth;
    private final int displayHealth;
    private final int absorptionAmount;
    private final boolean renderHighlight;
    private final boolean hardcore;

    public HeartBarRenderEvent(@NotNull ScreenContext screenContext, @NotNull Phase phase, @NotNull Player player, int x, int y, int rowHeight, int offsetHeartIndex, int lowHealthJitter, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, boolean hardcore) {
        super(IngameOverlayElementRenderEvent.OverlayElementType.HEARTS, screenContext, phase);
        this.player = player;
        this.x = x;
        this.y = y;
        this.rowHeight = rowHeight;
        this.offsetHeartIndex = offsetHeartIndex;
        this.lowHealthJitter = lowHealthJitter;
        this.maxHealth = maxHealth;
        this.playerHealth = playerHealth;
        this.displayHealth = displayHealth;
        this.absorptionAmount = absorptionAmount;
        this.renderHighlight = renderHighlight;
        this.hardcore = hardcore;
    }

    @NotNull
    public Player player() {
        return this.player;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public int rowHeight() {
        return this.rowHeight;
    }

    public int offsetHeartIndex() {
        return this.offsetHeartIndex;
    }

    public int lowHealthJitter() {
        return this.lowHealthJitter;
    }

    public float maxHealth() {
        return this.maxHealth;
    }

    public int playerHealth() {
        return this.playerHealth;
    }

    public int displayHealth() {
        return this.displayHealth;
    }

    public int absorptionAmount() {
        return this.absorptionAmount;
    }

    public boolean renderHighlight() {
        return this.renderHighlight;
    }

    public boolean hardcore() {
        return this.hardcore;
    }
}
