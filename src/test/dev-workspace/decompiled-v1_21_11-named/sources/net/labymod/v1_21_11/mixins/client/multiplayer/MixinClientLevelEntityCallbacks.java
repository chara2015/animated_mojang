package net.labymod.v1_21_11.mixins.client.multiplayer;

import net.labymod.api.client.world.ClientWorld;
import net.labymod.core.main.LabyMod;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/multiplayer/MixinClientLevelEntityCallbacks.class */
@Mixin(targets = {"net.minecraft.client.multiplayer.ClientLevel$EntityCallbacks"})
public class MixinClientLevelEntityCallbacks {
    @Inject(method = {"<init>(Lnet/minecraft/client/multiplayer/ClientLevel;)V"}, at = {@At("TAIL")})
    private void labyMod$init(CallbackInfo ci) {
        getClientWorld().resetEntityList();
    }

    @Inject(method = {"onTrackingStart(Lnet/minecraft/world/entity/Entity;)V"}, at = {@At("TAIL")})
    private void labyMod$startEntityTracking(Entity entity, CallbackInfo ci) {
        getClientWorld().addEntity((net.labymod.api.client.entity.Entity) entity);
    }

    @Inject(method = {"onTrackingEnd(Lnet/minecraft/world/entity/Entity;)V"}, at = {@At("TAIL")})
    private void labyMod$stopEntityTracking(Entity entity, CallbackInfo ci) {
        getClientWorld().removeEntity((net.labymod.api.client.entity.Entity) entity);
    }

    private ClientWorld getClientWorld() {
        return LabyMod.getInstance().minecraft().clientWorld();
    }
}
