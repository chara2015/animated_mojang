package net.labymod.v1_21_8.mixins.mojang.blaze3d.opengl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.util.RenderUtil;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.v1_21_8.client.gfx.pipeline.CommandEncoderPipelineExt;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/mojang/blaze3d/opengl/MixinGlCommandEncoder.class */
@Mixin({fmb.class})
public class MixinGlCommandEncoder implements CommandEncoderPipelineExt {

    @Shadow
    @Nullable
    private RenderPipeline e;

    @Shadow
    @Nullable
    private fmg g;

    @Override // net.labymod.v1_21_8.client.gfx.pipeline.CommandEncoderPipelineExt
    public void invalidatePipeline() {
        this.e = null;
        this.g = null;
    }

    @Inject(method = {"createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;"}, at = {@At("HEAD")})
    private void labyMod$setProjectionMatrix(Supplier<String> $$0, GpuTextureView $$1, OptionalInt $$2, GpuTextureView $$3, OptionalDouble $$4, CallbackInfoReturnable<RenderPass> cir) {
        RenderTarget offscreenTarget = RenderUtil.getOffscreenTarget();
        if (offscreenTarget != null) {
            RenderSystem.setProjectionMatrix(MinecraftUtil.ORTHO.a(offscreenTarget.width(), offscreenTarget.height()), fle.b);
        }
    }

    @WrapOperation(method = {"createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glBindFramebuffer(II)V")})
    private void labyMod$bindCustomFramebuffer(int $$0, int $$1, Operation<Void> original) {
        GlResource offscreenTarget = RenderUtil.getOffscreenTarget();
        if (offscreenTarget != null) {
            GlResource glResource = offscreenTarget;
            original.call(new Object[]{Integer.valueOf(GlConst.GL_DRAW_FRAMEBUFFER), Integer.valueOf(glResource.getId())});
        } else {
            original.call(new Object[]{Integer.valueOf($$0), Integer.valueOf($$1)});
        }
    }

    @WrapOperation(method = {"createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_viewport(IIII)V")})
    private void labyMod$bindCustomFramebuffer(int $$0, int $$1, int $$2, int $$3, Operation<Void> original) {
        RenderTarget offscreenTarget = RenderUtil.getOffscreenTarget();
        if (offscreenTarget == null) {
            original.call(new Object[]{Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3)});
        }
    }

    @Inject(method = {"finishRenderPass"}, at = {@At("TAIL")})
    private void labyMod$resetActiveTexture(CallbackInfo ci) {
        GlStateManager._activeTexture(GlConst.GL_TEXTURE0);
    }
}
