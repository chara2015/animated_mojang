package net.labymod.v1_8_9.mixins.client.renderer.entity.layers;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/layers/MixinLayerArmorBase.class */
@Mixin({bkn.class})
public class MixinLayerArmorBase {
    @Insert(method = {"shouldCombineTextures()Z"}, at = @At("HEAD"))
    private void labyMod$oldDamageAnimation(InsertInfoReturnable<Boolean> returnable) {
        DamageOldAnimation animation = (DamageOldAnimation) LabyMod.getInstance().getOldAnimationRegistry().get(DamageOldAnimation.NAME);
        returnable.setReturnValue(Boolean.valueOf(animation != null && animation.shouldCombineTextures()));
    }
}
