package net.labymod.api.event.client.render.world;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.RenderEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/world/RenderWorldEvent.class */
public class RenderWorldEvent extends RenderEvent {
    private final MinecraftCamera camera;
    private final float partialTicks;

    public RenderWorldEvent(@NotNull Stack stack, @NotNull Phase phase, @NotNull MinecraftCamera camera, float partialTicks) {
        super(stack, phase);
        this.camera = camera;
        this.partialTicks = partialTicks;
    }

    @NotNull
    public MinecraftCamera camera() {
        return this.camera;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    @Deprecated
    public float delta() {
        return getPartialTicks();
    }
}
