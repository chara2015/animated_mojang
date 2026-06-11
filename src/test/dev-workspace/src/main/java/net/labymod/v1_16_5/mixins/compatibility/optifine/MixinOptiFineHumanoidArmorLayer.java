package net.labymod.v1_16_5.mixins.compatibility.optifine;

import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_16_5.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/compatibility/optifine/MixinOptiFineHumanoidArmorLayer.class */
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({eik.class})
public class MixinOptiFineHumanoidArmorLayer {
    private int labyMod$overlayCoords;

    @Insert(method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V"}, at = @At("HEAD"))
    private void labyMod$getLivingEntity(dfm poseStack, eag bufferSource, int packedLight, aqm entity, float param4, float param5, float param6, float param7, float param8, float param9, InsertInfo info) {
        this.labyMod$overlayCoords = efr.c(entity, 0.0f);
    }

    @Redirect(method = {"renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IZLnet/minecraft/client/model/HumanoidModel;FFFLnet/minecraft/resources/ResourceLocation;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"))
    private void labyMod$enableOverlayTexture(dum model, dfm stack, dfq consumer, int packedLight, int overlayTexture, float red, float green, float blue, float alpha) {
        model.a(stack, consumer, packedLight, this.labyMod$overlayCoords, red, green, blue, alpha);
    }
}
