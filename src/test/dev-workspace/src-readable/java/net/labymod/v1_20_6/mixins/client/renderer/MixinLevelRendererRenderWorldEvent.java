package net.labymod.v1_20_6.mixins.client.renderer;

import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({gdo.class})
public class MixinLevelRendererRenderWorldEvent {

    @Shadow
    @Nullable
    private gdt Q;

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER)})
    private void labyMod$fireWorldRenderEvent$transparencyChain(float partialTicks, long $$1, boolean $$2, fes $$3, gdj $$4, gdp $$5, Matrix4f $$6, Matrix4f $$7, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), partialTicks);
    }

    @Inject(method = {"renderLevel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE)})
    private void labyMod$fireWorldRenderEvent(float partialTicks, long $$1, boolean $$2, fes $$3, gdj $$4, gdp $$5, Matrix4f $$6, Matrix4f $$7, CallbackInfo ci) {
        if (this.Q == null) {
            RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), partialTicks);
        }
    }
}
