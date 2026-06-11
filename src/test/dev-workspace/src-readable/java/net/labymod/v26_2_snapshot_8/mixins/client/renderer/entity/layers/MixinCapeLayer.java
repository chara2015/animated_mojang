package net.labymod.v26_2_snapshot_8.mixins.client.renderer.entity.layers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.v26_2_snapshot_8.client.util.EntityRenderStateAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/entity/layers/MixinCapeLayer.class */
@Mixin({CapeLayer.class})
public class MixinCapeLayer {

    @Shadow
    @Final
    private HumanoidModel<AvatarRenderState> model;

    @WrapOperation(method = {"submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/rendertype/RenderType;IIILnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V")})
    private <S> void labyMod$a(SubmitNodeCollector instance, Model<? super S> $$0, S $$1, PoseStack $$2, RenderType $$3, int $$4, int $$5, int $$6, ModelFeatureRenderer.CrumblingOverlay $$7, Operation<Void> original) {
        if (labyMod$fireCapeRenderEvent($$2, (AvatarRenderState) $$1, Phase.PRE)) {
            return;
        }
        original.call(new Object[]{instance, this.model, $$1, $$2, $$3, Integer.valueOf($$4), Integer.valueOf($$5), Integer.valueOf($$6), $$7});
        labyMod$fireCapeRenderEvent($$2, (AvatarRenderState) $$1, Phase.POST);
    }

    private boolean labyMod$fireCapeRenderEvent(PoseStack poseStack, AvatarRenderState state, Phase phase) {
        Avatar avatarLabyMod$getEntity;
        EntityRenderStateAccessor<Avatar> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null || (avatarLabyMod$getEntity = playerState.labyMod$getEntity()) == null || !(avatarLabyMod$getEntity instanceof Player)) {
            return false;
        }
        net.labymod.api.client.entity.player.Player player = (Player) avatarLabyMod$getEntity;
        PlayerModel playerModel = ((RenderLayer) this).getParentModel();
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase))).isCancelled();
    }
}
