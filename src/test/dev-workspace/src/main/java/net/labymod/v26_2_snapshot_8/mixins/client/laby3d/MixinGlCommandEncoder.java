package net.labymod.v26_2_snapshot_8.mixins.client.laby3d;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.systems.RenderPassBackend;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/laby3d/MixinGlCommandEncoder.class */
@Mixin({GlCommandEncoder.class})
public class MixinGlCommandEncoder {
    @Inject(method = {"createRenderPass"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlDebugLabel;pushDebugGroup(Ljava/util/function/Supplier;)V", shift = At.Shift.AFTER)})
    private void labyMod$createRenderPass(CallbackInfoReturnable<RenderPassBackend> cir) {
        Laby.references().laby3D().renderDevice().invalidateRenderState();
    }
}
