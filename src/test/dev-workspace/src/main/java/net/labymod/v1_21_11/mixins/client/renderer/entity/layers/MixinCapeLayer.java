package net.labymod.v1_21_11.mixins.client.renderer.entity.layers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.v1_21_11.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({iaj.class})
public class MixinCapeLayer {

    @Shadow
    @Final
    private gzo<ick> a;

    @WrapOperation(method = {"submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V")})
    private <S> void labyMod$a(hpo instance, gzp<? super S> $$0, S $$1, fzm $$2, ijs $$3, int $$4, int $$5, int $$6, a $$7, Operation<Void> original) {
        if (labyMod$fireCapeRenderEvent($$2, (ick) $$1, Phase.PRE)) {
            return;
        }
        original.call(new Object[]{instance, this.a, $$1, $$2, $$3, Integer.valueOf($$4), Integer.valueOf($$5), Integer.valueOf($$6), $$7});
        labyMod$fireCapeRenderEvent($$2, (ick) $$1, Phase.POST);
    }

    private boolean labyMod$fireCapeRenderEvent(fzm poseStack, ick state, Phase phase) {
        cgc cgcVarLabyMod$getEntity;
        EntityRenderStateAccessor<cgc> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (cgcVarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !(cgcVarLabyMod$getEntity instanceof ddm)) {
            return false;
        }
        Player player = (ddm) cgcVarLabyMod$getEntity;
        PlayerModel playerModel = ((ibj) this).d();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
