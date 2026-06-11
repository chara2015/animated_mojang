package net.labymod.v1_12_2.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/layers/MixinLayerCape.class */
@Mixin({cbr.class})
public class MixinLayerCape {
    private static final CapeParticleController labyMod$capeParticleController = LabyMod.references().capeParticleController();

    @Shadow
    @Final
    private cct a;

    @Inject(method = {"doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V", shift = At.Shift.AFTER)}, cancellable = true)
    private void labyMod$firePreCapeRenderEvent(bua player, float p_doRenderLayer_2_, float p_doRenderLayer_3_, float p_doRenderLayer_4_, float p_doRenderLayer_5_, float p_doRenderLayer_6_, float p_doRenderLayer_7_, float p_doRenderLayer_8_, CallbackInfo ci) {
        if (labyMod$fireCapeRenderEvent(player, Phase.PRE)) {
            bus.H();
            ci.cancel();
        }
    }

    @Inject(method = {"doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPlayer;renderCape(F)V", shift = At.Shift.AFTER)})
    private void labyMod$firePostCapeRenderEvent(bua player, float p_doRenderLayer_2_, float p_doRenderLayer_3_, float partialTicks, float p_doRenderLayer_5_, float p_doRenderLayer_6_, float p_doRenderLayer_7_, float p_doRenderLayer_8_, CallbackInfo ci) {
        labyMod$fireCapeRenderEvent(player, Phase.POST);
    }

    @Inject(method = {"doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPlayer;renderCape(F)V", shift = At.Shift.AFTER)})
    private void labyMod$renderCapeParticles(bua player, float p_doRenderLayer_2_, float p_doRenderLayer_3_, float partialTicks, float p_doRenderLayer_5_, float p_doRenderLayer_6_, float p_doRenderLayer_7_, float p_doRenderLayer_8_, CallbackInfo ci) {
        if ((player instanceof OptiFinePlayer) && ((OptiFinePlayer) player).getOptiFineCapeLocation() != null) {
            return;
        }
        labyMod$capeParticleController.spawn(VersionedStackProvider.DEFAULT_STACK.getProvider().getPose(), (Player) player, partialTicks);
    }

    private boolean labyMod$fireCapeRenderEvent(bua clientPlayer, Phase phase) {
        Player player = (Player) clientPlayer;
        PlayerModel playerModel = this.a.h();
        return ((PlayerCapeRenderEvent) Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, VersionedStackProvider.DEFAULT_STACK, phase))).isCancelled();
    }
}
