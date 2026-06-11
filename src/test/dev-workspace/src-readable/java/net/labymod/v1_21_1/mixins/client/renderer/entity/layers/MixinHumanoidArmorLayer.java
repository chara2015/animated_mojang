package net.labymod.v1_21_1.mixins.client.renderer.entity.layers;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimation;
import net.labymod.core.main.animation.old.animations.DamageOldAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/entity/layers/MixinHumanoidArmorLayer.class */
@Mixin({gol.class})
public class MixinHumanoidArmorLayer {
    private int labyMod$overlayCoords;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"}, at = @At("HEAD"))
    private void labyMod$getLivingEntity(fbi poseStack, gez bufferSource, int packedLight, btn entity, float param4, float param5, float param6, float param7, float param8, float param9, InsertInfo info) {
        int iC;
        OldAnimation animation = LabyMod.references().oldAnimationRegistry().get(DamageOldAnimation.NAME);
        if (animation != null && animation.isEnabled()) {
            iC = glk.c(entity, 0.0f);
        } else {
            iC = gqc.d;
        }
        this.labyMod$overlayCoords = iC;
    }

    @Redirect(method = {"renderModel"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void labyMod$enableOverlayTexture(fvx model, fbi stack, fbm consumer, int packedLight, int overlayTexture, int argb) {
        model.a(stack, consumer, packedLight, this.labyMod$overlayCoords, argb);
    }
}
