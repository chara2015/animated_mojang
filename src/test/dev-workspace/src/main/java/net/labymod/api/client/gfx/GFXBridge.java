package net.labymod.api.client.gfx;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/GFXBridge.class */
@Referenceable
@Deprecated(forRemoval = true, since = "4.3.0")
public interface GFXBridge {
    @Deprecated(forRemoval = true, since = "4.3.28")
    void storeBlaze3DStates();

    @Deprecated(forRemoval = true, since = "4.3.28")
    void restoreBlaze3DStates();

    Blaze3DGlStatePipeline blaze3DGlStatePipeline();

    void bindTexture2D(DeviceTextureView deviceTextureView);

    void setGLFWIcon(long j, InputStream inputStream) throws IOException;

    default void storeAndRestoreBlaze3DStates(Runnable renderer) {
        if (renderer == null) {
            return;
        }
        storeBlaze3DStates();
        renderer.run();
        restoreBlaze3DStates();
    }
}
