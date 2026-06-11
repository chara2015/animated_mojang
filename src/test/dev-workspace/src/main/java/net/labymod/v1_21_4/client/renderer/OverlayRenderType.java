package net.labymod.v1_21_4.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/renderer/OverlayRenderType.class */
public class OverlayRenderType extends gmj {
    public static final OverlayRenderType INSTANCE = new OverlayRenderType();

    public OverlayRenderType() {
        super("labymod_overlay", fft.f, c.h, 256, false, true, () -> {
            Laby.references().laby3D().storeStates();
            RenderSystem.setShader(glk.e);
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
        }, () -> {
            Laby.references().laby3D().restoreStates();
        });
    }
}
