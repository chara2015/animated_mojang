package net.labymod.v1_20_6.mixins.client.renderer.entity.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.v1_20_6.client.render.LivingEntityRendererAccessor;
import net.labymod.v1_20_6.client.renderer.entity.layers.VersionedShopItemLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/renderer/entity/player/MixinPlayerRenderer.class */
@Mixin({goe.class})
public class MixinPlayerRenderer {
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$addNewLayers(a context, boolean slim, CallbackInfo ci) {
        LivingEntityRendererAccessor livingEntityRendererAccessor = (goe) this;
        livingEntityRendererAccessor.addCustomLayer(new VersionedShopItemLayer(livingEntityRendererAccessor));
    }

    @Inject(method = {"renderHand"}, at = {@At("HEAD")})
    private void labyMod$preHandRender(faa poseStack, gdq bufferSource, int packedLight, gcp clientPlayer, fxc modelPart, fxc layerModelPart, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.PRE, packedLight);
    }

    @Inject(method = {"renderHand"}, at = {@At("TAIL")})
    private void labyMod$postHandRender(faa poseStack, gdq bufferSource, int packedLight, gcp clientPlayer, fxc modelPart, fxc layerModelPart, CallbackInfo callbackInfo) {
        labyMod$firePlayerModelRenderEvent(clientPlayer, poseStack, bufferSource, modelPart, Phase.POST, packedLight);
    }

    private void labyMod$firePlayerModelRenderEvent(gcp clientPlayer, faa poseStack, gdq bufferSource, fxc modelPart, Phase phase, int packedLight) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack(bufferSource);
        if (bufferSource instanceof a) {
            a source = (a) bufferSource;
            source.b();
        }
        PlayerModel playerModel = (fvh) ((goe) this).a();
        MainHand hand = ((fvh) playerModel).o.equals(modelPart) ? MainHand.LEFT : MainHand.RIGHT;
        Laby.fireEvent(new PlayerModelRenderHandEvent((Player) clientPlayer, playerModel, stack, phase, hand, packedLight));
    }
}
