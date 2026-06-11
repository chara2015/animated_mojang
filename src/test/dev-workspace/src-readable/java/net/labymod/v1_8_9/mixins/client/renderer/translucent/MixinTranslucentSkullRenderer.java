package net.labymod.v1_8_9.mixins.client.renderer.translucent;

import com.mojang.authlib.GameProfile;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/translucent/MixinTranslucentSkullRenderer.class */
@Mixin({bhk.class})
public class MixinTranslucentSkullRenderer {
    @Inject(method = {"renderSkull"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.BEFORE)})
    private void labyMod$fix$enableBlend(float lvt_1_1_, float lvt_2_1_, float lvt_3_1_, cq lvt_4_1_, float lvt_5_1_, int lvt_6_1_, GameProfile lvt_7_1_, int lvt_8_1_, CallbackInfo ci) {
        if (!Laby.labyAPI().config().ingame().translucentSkins().get().booleanValue()) {
            return;
        }
        bfl.l();
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
    }

    @Inject(method = {"renderSkull"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER)})
    private void labyMod$fix$disableBlend(float lvt_1_1_, float lvt_2_1_, float lvt_3_1_, cq lvt_4_1_, float lvt_5_1_, int lvt_6_1_, GameProfile lvt_7_1_, int lvt_8_1_, CallbackInfo ci) {
        if (!Laby.labyAPI().config().ingame().translucentSkins().get().booleanValue()) {
            return;
        }
        bfl.k();
    }
}
