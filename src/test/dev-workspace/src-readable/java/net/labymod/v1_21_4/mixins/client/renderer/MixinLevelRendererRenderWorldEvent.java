package net.labymod.v1_21_4.mixins.client.renderer;

import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/MixinLevelRendererRenderWorldEvent.class */
@Mixin({glv.class})
public class MixinLevelRendererRenderWorldEvent {
    @Inject(method = {"lambda$addMainPass$1"}, at = {@At("TAIL")})
    private void labyMod$fireWorldRenderEvent$transparencyChain(glo $$0, fla $$1, fks $$2, bou $$3, Matrix4f $$4, Matrix4f $$5, ffh $$6, ffh $$7, ffh $$8, ffh $$9, boolean $$10, gpr $$11, ffh $$12, CallbackInfo ci) {
        RenderWorldEventCaller.callPost(MinecraftUtil.levelRenderContext().getPoseStack().stack(), $$1.a(false));
    }
}
