package net.labymod.api.event.client.render.camera;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/camera/CameraSetupEvent.class */
public class CameraSetupEvent extends RenderEvent {
    public CameraSetupEvent(@NotNull Stack stack, @NotNull Phase phase) {
        super(stack, phase);
    }
}
