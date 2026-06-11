package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.render.matrix.NOPStack;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/Blaze3DGlStatePipeline.class */
@Referenceable
public interface Blaze3DGlStatePipeline {
    public static final NOPStack NOP_STACK = new NOPStack();

    void bindTexture(DeviceTextureView deviceTextureView);

    default Stack getModelViewStack() {
        return NOP_STACK;
    }

    default void applyModelViewMatrix() {
    }

    @Deprecated(forRemoval = true, since = "4.2")
    default void setupFlatLighting(Runnable runnable) {
        runnable.run();
    }
}
