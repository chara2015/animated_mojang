package net.labymod.v1_8_9.mixins.client.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/multiplayer/MixinWorldClient.class */
@Mixin({bdb.class})
public class MixinWorldClient {
    @Inject(method = {"spawnEntityInWorld"}, at = {@At("TAIL")})
    private void labyMod$addEntity(pk entity, CallbackInfoReturnable<Boolean> cir) {
        Laby.fireEvent(new EntitySpawnEvent(entity.F(), (Entity) entity));
    }

    @Inject(method = {"removeEntity(Lnet/minecraft/entity/Entity;)V"}, at = {@At("HEAD")})
    private void labyMod$removeEntity(pk entity, CallbackInfo ci) {
        Laby.fireEvent(new EntityDestructEvent((Entity) entity));
    }
}
