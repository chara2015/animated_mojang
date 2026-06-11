package net.labymod.v1_8_9.mixins.client.renderer.texture;

import java.nio.IntBuffer;
import net.labymod.api.client.gfx.GlConst;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/texture/MixinTextureUtil.class */
@Mixin({bml.class})
public abstract class MixinTextureUtil {
    @Overwrite
    public static void a(int glTextureId, int mipmapLevels, int width, int height) {
        bfl.i(glTextureId);
        if (mipmapLevels >= 0) {
            GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAX_LEVEL, mipmapLevels);
            GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_LOD, 0);
            GL11.glTexParameteri(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAX_LOD, mipmapLevels);
            GL11.glTexParameterf(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_LOD_BIAS, 0.0f);
        }
        for (int i = 0; i <= mipmapLevels; i++) {
            GL11.glTexImage2D(GlConst.GL_TEXTURE_2D, i, GlConst.GL_RGBA, width >> i, height >> i, 0, GlConst.GL_BGRA, GlConst.GL_UNSIGNED_INT_8_8_8_8_REV, (IntBuffer) null);
        }
    }
}
