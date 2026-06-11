package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.CancellableScreenRenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/CameraOverlayRenderEvent.class */
public class CameraOverlayRenderEvent extends CancellableScreenRenderEvent {
    private final OverlayType overlayType;
    private float opacity;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/CameraOverlayRenderEvent$OverlayType.class */
    public enum OverlayType {
        PUMPKIN,
        POWDER_SNOW,
        EQUIPPABLE_CAMERA,
        UNKNOWN
    }

    public CameraOverlayRenderEvent(@NotNull OverlayType overlayType, @NotNull ScreenContext screenContext, @NotNull Phase phase, float opacity) {
        super(screenContext, phase);
        this.overlayType = overlayType;
        this.opacity = opacity;
    }

    @NotNull
    public OverlayType overlayType() {
        return this.overlayType;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float opacity) {
        if (phase() == Phase.POST) {
            throw new IllegalStateException("Cannot modify opacity in post phase!");
        }
        this.opacity = opacity;
    }
}
