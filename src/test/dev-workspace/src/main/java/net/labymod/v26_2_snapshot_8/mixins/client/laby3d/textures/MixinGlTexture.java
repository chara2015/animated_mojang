package net.labymod.v26_2_snapshot_8.mixins.client.laby3d.textures;

import com.mojang.blaze3d.opengl.GlTexture;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/laby3d/textures/MixinGlTexture.class */
@Mixin({GlTexture.class})
public abstract class MixinGlTexture implements GlResource {
    @Shadow
    public abstract int glId();

    public int getId() {
        return glId();
    }

    public void flushChanges() {
        MinecraftUtil.applySamplerObjectWorkaround();
    }
}
