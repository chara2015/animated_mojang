package net.labymod.core.event.client.render;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.laby3d.Laby3D;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/RenderHandEventCaller.class */
public final class RenderHandEventCaller {
    private static boolean previousScreenContext;

    public static RenderHandEvent call(Stack stack, Phase phase) {
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        RenderHandEvent event = new RenderHandEvent(stack, phase);
        RenderEnvironmentContext context = Laby.references().renderEnvironmentContext();
        if (phase == Phase.PRE) {
            previousScreenContext = context.isScreenContext();
            context.setScreenContext(true);
        } else {
            context.setScreenContext(previousScreenContext);
        }
        Laby.fireEvent(event);
        laby3D.restoreStates();
        return event;
    }
}
