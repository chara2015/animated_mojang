package net.labymod.v1_21_11.mixins.client.laby3d;

import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/laby3d/MixinGlCommandEncoder.class */
@Mixin({fxb.class})
public class MixinGlCommandEncoder {
    @Inject(method = {"createRenderPass(Ljava/util/function/Supplier;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalInt;Lcom/mojang/blaze3d/textures/GpuTextureView;Ljava/util/OptionalDouble;)Lcom/mojang/blaze3d/systems/RenderPass;"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlDebugLabel;pushDebugGroup(Ljava/util/function/Supplier;)V", shift = At.Shift.AFTER)})
    private void labyMod$createRenderPass(Supplier<String> $$0, GpuTextureView $$1, OptionalInt $$2, GpuTextureView $$3, OptionalDouble $$4, CallbackInfoReturnable<RenderPass> cir) {
        Laby.references().laby3D().renderDevice().invalidateRenderState();
    }
}
