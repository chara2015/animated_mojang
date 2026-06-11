package net.labymod.v1_21_10.mixins.client.multiplayer;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.client.world.DefaultClientWorld;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/multiplayer/MixinClientLevelEntityCallbacks.class */
@Mixin(targets = {"net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks"})
public class MixinClientLevelEntityCallbacks {
    @Inject(method = {"<init>(Lnet/minecraft/client/multiplayer/ClientLevel;)V"}, at = {@At("TAIL")})
    private void labyMod$init(CallbackInfo ci) {
        ((DefaultClientWorld) getClientWorld()).resetEntityList();
    }

    @Inject(method = {"onTrackingStart(Lnet/minecraft/world/entity/Entity;)V"}, at = {@At("TAIL")})
    private void labyMod$startEntityTracking(cdv entity, CallbackInfo ci) {
        ((DefaultClientWorld) getClientWorld()).addEntity((Entity) entity);
    }

    @Inject(method = {"onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V"}, at = {@At("TAIL")})
    private void labyMod$stopEntityTracking(cdv entity, CallbackInfo ci) {
        ((DefaultClientWorld) getClientWorld()).removeEntity((Entity) entity);
    }

    private ClientWorld getClientWorld() {
        return LabyMod.getInstance().minecraft().clientWorld();
    }
}
