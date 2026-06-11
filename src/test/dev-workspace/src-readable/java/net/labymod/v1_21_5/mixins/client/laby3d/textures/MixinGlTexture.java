package net.labymod.v1_21_5.mixins.client.laby3d.textures;

import net.labymod.laby3d.api.opengl.GlResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/laby3d/textures/MixinGlTexture.class */
@Mixin({fjl.class})
public abstract class MixinGlTexture implements GlResource {
    @Shadow
    public abstract int b();

    @Shadow
    public abstract void a();

    public int getId() {
        return b();
    }

    public void flushChanges() {
        super.flushChanges();
        a();
    }
}
