package net.labymod.v1_21_11.mixins.client.laby3d.textures;

import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/laby3d/textures/MixinGlTexture.class */
@Mixin({fxl.class})
public abstract class MixinGlTexture implements GlResource {
    @Shadow
    public abstract int a();

    public int getId() {
        return a();
    }

    public void flushChanges() {
        MinecraftUtil.applySamplerObjectWorkaround();
    }
}
