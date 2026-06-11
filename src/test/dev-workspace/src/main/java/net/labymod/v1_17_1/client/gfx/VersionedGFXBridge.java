package net.labymod.v1_17_1.client.gfx;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;
import net.labymod.gfx_lwjgl3.client.gfx.LWJGL3GFXBridge;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gfx/VersionedGFXBridge.class */
@Singleton
@Implements(value = GFXBridge.class, key = InjectionNames.GFX_BRIDGE_PRODUCTION)
public class VersionedGFXBridge extends LWJGL3GFXBridge {
    @Inject
    public VersionedGFXBridge(Blaze3DGlStatePipeline blaze3DGlStatePipeline) {
        super(blaze3DGlStatePipeline);
    }
}
