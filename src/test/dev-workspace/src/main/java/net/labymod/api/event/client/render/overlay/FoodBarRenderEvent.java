package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/FoodBarRenderEvent.class */
public class FoodBarRenderEvent extends IngameOverlayElementRenderEvent {
    private final Player player;
    private final int x;
    private final int y;
    private final int jitterOffset;
    private final int foodLevel;
    private final float saturationLevel;

    public FoodBarRenderEvent(@NotNull ScreenContext screenContext, @NotNull Phase phase, @NotNull Player player, int x, int y, int jitterOffset, int foodLevel, float saturationLevel) {
        super(IngameOverlayElementRenderEvent.OverlayElementType.FOOD, screenContext, phase);
        this.player = player;
        this.x = x;
        this.y = y;
        this.jitterOffset = jitterOffset;
        this.foodLevel = foodLevel;
        this.saturationLevel = saturationLevel;
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

    public int jitterOffset() {
        return this.jitterOffset;
    }

    public int foodLevel() {
        return this.foodLevel;
    }

    public float saturationLevel() {
        return this.saturationLevel;
    }
}
