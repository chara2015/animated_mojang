package net.labymod.v1_21_5.mixins.client.renderer.entity.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.v1_21_5.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_21_5.client.renderer.entity.layers.VersionedShopItemLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/entity/player/MixinPlayerRenderer.class */
@Mixin({hdc.class})
public class MixinPlayerRenderer {
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addNewLayers(a context, boolean slim, CallbackInfo ci) {
        LivingEntityRendererAccessor livingEntityRendererAccessor = (hdc) this;
        livingEntityRendererAccessor.addCustomLayer(new VersionedShopItemLayer(livingEntityRendererAccessor));
    }

    @Inject(method = {"renderHand"}, at = {@At("HEAD")})
    private void labyMod$preHandRender(fld poseStack, grn bufferSource, int packedLightCoords, alr skinTexture, gkr modelPart, boolean secondLayer, CallbackInfo ci) {
        bxe cameraEntity = fqq.Q().ao();
        if (!(cameraEntity instanceof gqj)) {
            return;
        }
        gqj player = (gqj) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, bufferSource, modelPart, Phase.PRE, packedLightCoords);
    }

    @Inject(method = {"renderHand"}, at = {@At("TAIL")})
    private void labyMod$postHandRender(fld poseStack, grn bufferSource, int packedLightCoords, alr skinTexture, gkr modelPart, boolean secondLayer, CallbackInfo ci) {
        bxe cameraEntity = fqq.Q().ao();
        if (!(cameraEntity instanceof gqj)) {
            return;
        }
        gqj player = (gqj) cameraEntity;
        labyMod$firePlayerModelRenderEvent(player, poseStack, bufferSource, modelPart, Phase.POST, packedLightCoords);
    }

    private void labyMod$firePlayerModelRenderEvent(gqj clientPlayer, fld poseStack, grn bufferSource, gkr modelPart, Phase phase, int packedLight) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.b();
        }
        PlayerModel playerModel = (git) ((hdc) this).c();
        MainHand hand = ((git) playerModel).s.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player) clientPlayer, playerModel, stack, phase, hand, packedLight));
    }
}
