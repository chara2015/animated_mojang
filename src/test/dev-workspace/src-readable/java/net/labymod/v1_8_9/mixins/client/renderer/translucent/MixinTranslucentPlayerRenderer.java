package net.labymod.v1_8_9.mixins.client.renderer.translucent;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/translucent/MixinTranslucentPlayerRenderer.class */
@Mixin({bln.class})
public class MixinTranslucentPlayerRenderer {
    @Inject(method = {"doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", shift = At.Shift.BEFORE)})
    private void labyMod$fix$enableBlending(bet lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        if (!Laby.labyAPI().config().ingame().translucentSkins().get().booleanValue()) {
            return;
        }
        bfl.l();
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
    }

    @Inject(method = {"doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", shift = At.Shift.AFTER)})
    private void labyMod$fix$disableBlending(bet lvt_1_1_, double lvt_2_1_, double lvt_4_1_, double lvt_6_1_, float lvt_8_1_, float lvt_9_1_, CallbackInfo ci) {
        if (!Laby.labyAPI().config().ingame().translucentSkins().get().booleanValue()) {
            return;
        }
        bfl.k();
    }
}
