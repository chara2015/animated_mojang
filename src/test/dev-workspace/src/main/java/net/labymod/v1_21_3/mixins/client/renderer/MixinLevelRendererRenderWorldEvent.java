package net.labymod.v1_21_3.mixins.client.renderer;

import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({glh.class})
public class MixinLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$1"}, at = {@At("TAIL")})
    private void labyMod$fireWorldRenderEvent$transparencyChain(gkz $$0, flw $$1, flp $$2, bpt $$3, Matrix4f $$4, Matrix4f $$5, fge $$6, fge $$7, fge $$8, fge $$9, boolean $$10, gpc $$11, fge $$12, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), $$1.a(false));
    }
}
