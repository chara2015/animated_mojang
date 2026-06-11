package net.labymod.v1_17_1.mixins.client.renderer.entity.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.v1_17_1.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_17_1.client.renderer.entity.layers.VersionedShopItemLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/renderer/entity/player/MixinPlayerRenderer.class */
@Mixin({ewt.class})
public class MixinPlayerRenderer {
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addNewLayers(a context, boolean slim, CallbackInfo ci) {
        LivingEntityRendererAccessor livingEntityRendererAccessor = (ewt) this;
        livingEntityRendererAccessor.addCustomLayer(new VersionedShopItemLayer(livingEntityRendererAccessor));
    }

    @Inject(method = {"renderHand"}, at = {@At("HEAD")})
    private void labyMod$preHandRender(dql poseStack, eni bufferSource, int packedLight, emj clientPlayer, eir modelPart, eir layerModelPart, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.PRE, packedLight);
    }

    @Inject(method = {"renderHand"}, at = {@At("TAIL")})
    private void labyMod$postHandRender(dql poseStack, eni bufferSource, int packedLight, emj clientPlayer, eir modelPart, eir layerModelPart, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.POST, packedLight);
    }

    private void labyMod$firePlayerModelRenderEvent(emj clientPlayer, dql poseStack, eni bufferSource, eir modelPart, Phase phase, int packedLight) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.b();
        }
        PlayerModel playerModel = (ehc) ((ewt) this).a();
        MainHand hand = ((ehc) playerModel).l.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player) clientPlayer, playerModel, stack, phase, hand, packedLight));
    }
}
