package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.CancellableScreenRenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/IngameOverlayElementRenderEvent.class */
public class IngameOverlayElementRenderEvent extends CancellableScreenRenderEvent {
    private final OverlayElementType elementType;

    public IngameOverlayElementRenderEvent(@NotNull OverlayElementType elementType, @NotNull ScreenContext screenContext, @NotNull Phase phase) {
        super(screenContext, phase);
        this.elementType = elementType;
    }

    @NotNull
    public OverlayElementType elementType() {
        return this.elementType;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/IngameOverlayElementRenderEvent$OverlayElementType.class */
    public enum OverlayElementType {
        CROSSHAIR,
        SCOREBOARD,
        OFFHAND_TEXTURE,
        OFFHAND_ITEM,
        BOSS_BAR,
        POTION_EFFECTS,
        ATTACK_INDICATOR,
        TITLE,
        ACTION_BAR,
        FOOD,
        HEARTS;

        public boolean isOffhand() {
            return this == OFFHAND_TEXTURE || this == OFFHAND_ITEM;
        }
    }
}
