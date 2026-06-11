package net.labymod.core.event.client.render;

import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/RenderTypeAttachmentEventCaller.class */
public final class RenderTypeAttachmentEventCaller {
    public static boolean firePre(String name, RenderTypeAttachmentEvent.State state) {
        return ((RenderTypeAttachmentEvent) Laby.fireEvent(new RenderTypeAttachmentEvent(name, state, Phase.PRE))).isCancelled();
    }

    public static void firePost(String name, RenderTypeAttachmentEvent.State state) {
        Laby.fireEvent(new RenderTypeAttachmentEvent(name, state, Phase.POST));
    }
}
