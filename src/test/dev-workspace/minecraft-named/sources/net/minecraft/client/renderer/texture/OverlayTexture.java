package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.util.ARGB;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/OverlayTexture.class */
public class OverlayTexture implements AutoCloseable {
    private static final int SIZE = 16;
    public static final int NO_WHITE_U = 0;
    public static final int RED_OVERLAY_V = 3;
    public static final int WHITE_OVERLAY_V = 10;
    public static final int NO_OVERLAY = pack(0, 10);
    private final DynamicTexture texture = new DynamicTexture("Entity Color Overlay", 16, 16, false);

    public OverlayTexture() {
        NativeImage $$0 = this.texture.getPixels();
        for (int $$1 = 0; $$1 < 16; $$1++) {
            for (int $$2 = 0; $$2 < 16; $$2++) {
                if ($$1 < 8) {
                    $$0.setPixel($$2, $$1, -1291911168);
                } else {
                    int $$3 = (int) ((1.0f - (($$2 / 15.0f) * 0.75f)) * 255.0f);
                    $$0.setPixel($$2, $$1, ARGB.white($$3));
                }
            }
        }
        this.texture.upload();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.texture.close();
    }

    public static int u(float $$0) {
        return (int) ($$0 * 15.0f);
    }

    public static int v(boolean $$0) {
        return $$0 ? 3 : 10;
    }

    public static int pack(int $$0, int $$1) {
        return $$0 | ($$1 << 16);
    }

    public static int pack(float $$0, boolean $$1) {
        return pack(u($$0), v($$1));
    }

    public GpuTextureView getTextureView() {
        return this.texture.getTextureView();
    }
}
