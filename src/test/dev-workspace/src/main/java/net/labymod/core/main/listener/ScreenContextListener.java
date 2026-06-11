package net.labymod.core.main.listener;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.GameRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/ScreenContextListener.class */
public class ScreenContextListener {
    private static final RenderEnvironmentContext ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();

    @Subscribe(126)
    public void onGameRender(GameRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        ENVIRONMENT_CONTEXT.screenContext().render();
    }
}
