package net.labymod.v1_21_10.mixins.client.laby3d.textures;

import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.opengl.GlResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/laby3d/textures/MixinGlTexture.class */
@Mixin({fsd.class})
public abstract class MixinGlTexture implements GlResource {
    @Shadow
    public abstract int a();

    @Shadow
    public abstract void a(int i);

    public int getId() {
        return a();
    }

    public void flushChanges() {
        a(GlConst.GL_TEXTURE_2D);
    }
}
