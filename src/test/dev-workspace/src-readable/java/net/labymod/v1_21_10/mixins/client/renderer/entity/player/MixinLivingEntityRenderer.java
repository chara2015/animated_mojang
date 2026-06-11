package net.labymod.v1_21_10.mixins.client.renderer.entity.player;

import cew;
import gvi;
import hvn;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_10.client.render.LivingEntityRendererAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/renderer/entity/player/MixinLivingEntityRenderer.class */
@Mixin({hpa.class})
public abstract class MixinLivingEntityRenderer<T extends cew, S extends hvn, M extends gvi<? super S>> extends hnx<T, S> implements hpw<S, M>, LivingEntityRendererAccessor {

    @Shadow
    protected M f;

    @Shadow
    protected abstract boolean a(hso<S, M> hsoVar);

    protected MixinLivingEntityRenderer(a $$0) {
        super($$0);
    }

    @Inject(method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$showOwnName(cew entity, double $$1, CallbackInfoReturnable<Boolean> cir) {
        if (entity == fzz.W().s) {
            cir.setReturnValue(LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }

    @Override // net.labymod.v1_21_10.client.render.LivingEntityRendererAccessor
    public void addCustomLayer(hso layer) {
        a(layer);
    }
}
