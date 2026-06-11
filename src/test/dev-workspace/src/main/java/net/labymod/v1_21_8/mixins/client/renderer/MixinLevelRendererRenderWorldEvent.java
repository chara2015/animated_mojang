package net.labymod.v1_21_8.mixins.client.renderer;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({gxh.class})
public class MixinLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$2"}, at = {@At("TAIL")})
    private void labyMod$fireWorldRenderEvent$transparencyChain(GpuBufferSlice $$0, ftu $$1, ftm $$2, btt $$3, Matrix4f $$4, fnr $$5, fnr $$6, boolean $$7, hbq $$8, fnr $$9, fnr $$10, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), $$1.a(false));
    }
}
