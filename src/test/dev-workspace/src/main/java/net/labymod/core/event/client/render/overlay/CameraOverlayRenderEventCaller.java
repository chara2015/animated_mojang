package net.labymod.core.event.client.render.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.CameraOverlayRenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/overlay/CameraOverlayRenderEventCaller.class */
public final class CameraOverlayRenderEventCaller {
    @NotNull
    public static CameraOverlayRenderEvent callPre(@NotNull ScreenContext screenContext, @NotNull CameraOverlayRenderEvent.OverlayType overlayType, float opacity) {
        return (CameraOverlayRenderEvent) Laby.fireEvent(new CameraOverlayRenderEvent(overlayType, screenContext, Phase.PRE, opacity));
    }

    @NotNull
    public static CameraOverlayRenderEvent callPre(@NotNull ScreenContext screenContext, @NotNull String path, float opacity) {
        return callPre(screenContext, determineOverlayType(path), opacity);
    }

    public static void callPost(@NotNull ScreenContext screenContext, @NotNull CameraOverlayRenderEvent.OverlayType overlayType, float opacity) {
        Laby.fireEvent(new CameraOverlayRenderEvent(overlayType, screenContext, Phase.POST, opacity));
    }

    public static void callPost(@NotNull ScreenContext screenContext, @NotNull String path, float opacity) {
        callPost(screenContext, determineOverlayType(path), opacity);
    }

    @NotNull
    private static CameraOverlayRenderEvent.OverlayType determineOverlayType(@NotNull String path) {
        if (path.equals("textures/misc/powder_snow_outline.png")) {
            return CameraOverlayRenderEvent.OverlayType.POWDER_SNOW;
        }
        if (path.equals("textures/misc/pumpkinblur.png")) {
            return CameraOverlayRenderEvent.OverlayType.PUMPKIN;
        }
        return CameraOverlayRenderEvent.OverlayType.UNKNOWN;
    }
}
