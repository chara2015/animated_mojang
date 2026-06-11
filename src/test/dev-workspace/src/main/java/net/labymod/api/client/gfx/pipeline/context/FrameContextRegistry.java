package net.labymod.api.client.gfx.pipeline.context;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/context/FrameContextRegistry.class */
@Referenceable
public interface FrameContextRegistry {
    void register(FrameContext frameContext);

    void beginFrame();

    void endFrame();
}
