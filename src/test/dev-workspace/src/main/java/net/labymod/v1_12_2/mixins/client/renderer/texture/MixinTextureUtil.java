package net.labymod.v1_12_2.mixins.client.renderer.texture;

import java.nio.IntBuffer;
import net.labymod.api.client.gfx.GlConst;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/texture/MixinTextureUtil.class */
@Mixin({cdt.class})
public abstract class MixinTextureUtil {
    @Overwrite
    public static void a(int glTextureId, int mipmapLevels, int width, int height) {
        bus.i(glTextureId);
        if (mipmapLevels >= 0) {
            bus.b(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAX_LEVEL, mipmapLevels);
            bus.b(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_LOD, 0);
            bus.b(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAX_LOD, mipmapLevels);
            bus.b(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_LOD_BIAS, 0.0f);
        }
        for (int i = 0; i <= mipmapLevels; i++) {
            bus.a(GlConst.GL_TEXTURE_2D, i, GlConst.GL_RGBA, width >> i, height >> i, 0, GlConst.GL_BGRA, GlConst.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer) null);
        }
    }
}
