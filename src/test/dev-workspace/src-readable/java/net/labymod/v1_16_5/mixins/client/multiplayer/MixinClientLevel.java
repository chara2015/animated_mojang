package net.labymod.v1_16_5.mixins.client.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.world.DefaultClientWorld;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/multiplayer/MixinClientLevel.class */
@Mixin({dwt.class})
public class MixinClientLevel {
    @Insert(method = {"addEntity(ILnet/minecraft/world/entity/Entity;)V"}, at = @At("TAIL"))
    private void labyMod$addEntity(int entityId, aqa entity, InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entityId, (Entity) entity));
        clientWorld().addEntity((Entity) entity);
    }

    @Inject(method = {"onEntityRemoved"}, at = {@At("TAIL")})
    private void labyMod$removeEntity(aqa entity, CallbackInfo ci) {
        Laby.fireEvent(new EntityDestructEvent((Entity) entity));
        clientWorld().removeEntity((Entity) entity);
    }

    private DefaultClientWorld clientWorld() {
        return (DefaultClientWorld) LabyMod.getInstance().minecraft().clientWorld();
    }
}
