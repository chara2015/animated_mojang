package net.labymod.v1_21_11.mixins.client.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/multiplayer/MixinClientLevel.class */
@Mixin({hif.class})
public class MixinClientLevel {
    @Insert(method = {"addEntity(Lnet/minecraft/world/entity/Entity;)V"}, at = @At("TAIL"))
    private void labyMod$addEntity(cgk entity, InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entity.aA(), (Entity) entity));
    }

    @Redirect(method = {"removeEntity"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void labyMod$removeEntity(cgk entity, e param0) {
        entity.c(param0);
        Laby.fireEvent(new EntityDestructEvent((Entity) entity));
    }
}
