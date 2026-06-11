package net.labymod.v1_21_10.mixins.client.renderer;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({hfq.class})
public class MixinLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$1(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLnet/minecraft/client/renderer/culling/Frustum;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V"}, at = {@At("TAIL")})
    private void labyMod$fireWorldRenderEvent$transparencyChain(GpuBufferSlice $$0, ibp $$1, bya $$2, Matrix4f $$3, ftn $$4, ftn $$5, boolean $$6, hlh $$7, ftn $$8, ftn $$9, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), fzz.W().aD().a(false));
    }
}
