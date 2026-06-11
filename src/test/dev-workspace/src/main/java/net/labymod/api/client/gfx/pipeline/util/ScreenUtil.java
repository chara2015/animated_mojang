package net.labymod.api.client.gfx.pipeline.util;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.ScreenRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/util/ScreenUtil.class */
public final class ScreenUtil {
    public static void wrapRender(ScreenContext screenContext, Runnable renderer) {
        FrameTimer frameTimer = Laby.references().frameTimer();
        if (frameTimer.isPaused() && !frameTimer.isPauseInterrupted()) {
            return;
        }
        fireScreenRenderEvent(screenContext, Phase.PRE);
        renderer.run();
        fireScreenRenderEvent(screenContext, Phase.POST);
        if (frameTimer.isPauseInterrupted()) {
            frameTimer.onPauseFrameRendered();
        }
    }

    public static void setScreenContext(boolean screenContext) {
        Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().setScreenContext(screenContext);
    }

    private static void fireScreenRenderEvent(ScreenContext screenContext, Phase phase) {
        Laby.fireEvent(new ScreenRenderEvent(screenContext, phase));
    }
}
