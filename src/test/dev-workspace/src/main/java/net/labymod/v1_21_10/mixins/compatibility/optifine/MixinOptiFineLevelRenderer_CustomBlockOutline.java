package net.labymod.v1_21_10.mixins.compatibility.optifine;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import javax.annotation.Nullable;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.v1_21_10.client.util.BlockOutlineUtil;
import net.labymod.v1_21_10.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/compatibility/optifine/MixinOptiFineLevelRenderer_CustomBlockOutline.class */
@Pseudo
@DynamicMixin(value = OptiFine.NAMESPACE, applier = OptiFineDynamicMixinApplier.class)
@Mixin({hfq.class})
public class MixinOptiFineLevelRenderer_CustomBlockOutline {

    @Shadow
    @Nullable
    private gzn s;

    @Shadow
    @Final
    private hgh m;

    @WrapOperation(method = {"renderLevel"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/state/LevelRenderState;blockOutlineRenderState:Lnet/minecraft/client/renderer/state/BlockOutlineRenderState;", opcode = 180)})
    private ibn labyMod$disableForgeParity(ibp instance, Operation<ibn> original) {
        hep player = fzz.W().s;
        if (player == null) {
            return (ibn) original.call(new Object[]{instance});
        }
        drn gameType = player.a();
        if (gameType == null) {
            return (ibn) original.call(new Object[]{instance});
        }
        if (gameType.f()) {
            return null;
        }
        return (ibn) original.call(new Object[]{instance});
    }

    @WrapOperation(method = {"renderBlockOutline"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderHitOutline(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;DDDLnet/minecraft/client/renderer/state/BlockOutlineRenderState;I)V")})
    @Dynamic
    private void labyMod$renderCustomBlockOutline(hfq instance, fua poseStack, fud lineConsumer, double x, double y, double z, ibn state, int color, Operation<Void> original) {
        BlockOutlineUtil.renderBlockOutline(this.s, this.m.c(), lineConsumer, poseStack, x, y, z, state, color);
    }
}
