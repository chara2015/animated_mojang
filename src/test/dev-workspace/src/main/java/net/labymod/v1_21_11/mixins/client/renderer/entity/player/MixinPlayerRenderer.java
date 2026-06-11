package net.labymod.v1_21_11.mixins.client.renderer.entity.player;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.v1_21_11.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_21_11.client.renderer.entity.layers.VersionedShopItemLayer;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/player/MixinPlayerRenderer.class */
@Mixin({icd.class})
public class MixinPlayerRenderer {
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addNewLayers(a context, boolean slim, CallbackInfo ci) {
        LivingEntityRendererAccessor livingEntityRendererAccessor = (icd) this;
        livingEntityRendererAccessor.addCustomLayer(new VersionedShopItemLayer(livingEntityRendererAccessor));
    }

    @Inject(method = {"renderHand"}, at = {@At("HEAD")})
    private void labyMod$preHandRender(fzm poseStack, hpo collector, int packedLightCoords, amo skinTexture, hdg modelPart, boolean secondLayer, CallbackInfo ci) {
        cgk cameraEntity = gfj.V().au();
        if (!(cameraEntity instanceof hne)) {
            return;
        }
        hne player = (hne) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, collector, modelPart, Phase.PRE, packedLightCoords);
    }

    @Inject(method = {"renderHand"}, at = {@At("TAIL")})
    private void labyMod$postHandRender(fzm poseStack, hpo collector, int packedLightCoords, amo skinTexture, hdg modelPart, boolean secondLayer, CallbackInfo ci) {
        cgk cameraEntity = gfj.V().au();
        if (!(cameraEntity instanceof hne)) {
            return;
        }
        hne player = (hne) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, collector, modelPart, Phase.POST, packedLightCoords);
    }

    private void labyMod$firePlayerModelRenderEvent(hne clientPlayer, fzm poseStack, hpo collector, hdg modelPart, Phase phase, int packedLight) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        DefaultStackProvider provider = new DefaultStackProvider();
        Stack modelStack = Stack.create((StackProvider) provider);
        modelStack.mul(stack.getProvider().getPose());
        PlayerModel playerModel = (hht) ((icd) this).c();
        MainHand hand = ((hht) playerModel).l.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        PlayerModelRenderHandEvent event = new PlayerModelRenderHandEvent((Player) clientPlayer, playerModel, modelStack, phase, hand, packedLight);
        if (phase == Phase.PRE) {
            MinecraftUtil.prePlayerModelRenderHandEvent = event;
        } else {
            MinecraftUtil.postPlayerModelRenderHandEvent = event;
        }
    }
}
