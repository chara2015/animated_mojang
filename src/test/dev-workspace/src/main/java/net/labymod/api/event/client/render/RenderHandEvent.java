package net.labymod.api.event.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/RenderHandEvent.class */
public class RenderHandEvent extends CancellableRenderEvent {
    public RenderHandEvent(@NotNull Stack stack, @NotNull Phase phase) {
        super(stack, phase);
    }
}
