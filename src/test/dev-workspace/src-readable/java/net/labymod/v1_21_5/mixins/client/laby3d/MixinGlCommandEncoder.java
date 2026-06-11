package net.labymod.v1_21_5.mixins.client.laby3d;

import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuTexture;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/laby3d/MixinGlCommandEncoder.class */
@Mixin({fjd.class})
public class MixinGlCommandEncoder {
    @Inject(method = {"createRenderPass(Lcom/mojang/blaze3d/textures/GpuTexture;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTexture;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glBindFramebuffer(II)V", shift = At.Shift.AFTER)})
    private void labyMod$createRenderPass(GpuTexture $$0, OptionalInt $$1, GpuTexture $$2, OptionalDouble $$3, CallbackInfoReturnable<RenderPass> cir) {
        Laby.references().laby3D().renderDevice().invalidateRenderState();
    }
}
