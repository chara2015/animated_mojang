package net.labymod.v1_21_5.mixins.mojang.blaze3d.opengl;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import javax.annotation.Nullable;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.v1_21_5.client.gfx.pipeline.CommandEncoderPipelineExt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/mojang/blaze3d/opengl/MixinGlCommandEncoder.class */
@Mixin({fjd.class})
public class MixinGlCommandEncoder implements CommandEncoderPipelineExt {

    @Shadow
    @Nullable
    private RenderPipeline e;

    @Shadow
    @Nullable
    private fjh g;

    @Override // net.labymod.v1_21_5.client.gfx.pipeline.CommandEncoderPipelineExt
    public void invalidatePipeline() {
        this.e = null;
        this.g = null;
    }

    @Inject(method = {"finishRenderPass"}, at = {@At("TAIL")})
    private void labyMod$resetActiveTexture(CallbackInfo ci) {
        GlStateManager._activeTexture(GlConst.GL_TEXTURE0);
    }
}
