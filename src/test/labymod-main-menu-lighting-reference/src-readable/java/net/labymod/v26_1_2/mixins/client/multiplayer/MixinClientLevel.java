package net.labymod.v26_1_2.mixins.client.multiplayer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.world.EntityDestructEvent;
import net.labymod.api.event.client.world.EntitySpawnEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/multiplayer/MixinClientLevel.class */
@Mixin({ClientLevel.class})
public class MixinClientLevel {
    @Insert(method = {"addEntity(Lnet/minecraft/world/entity/Entity;)V"}, at = @At("TAIL"))
    private void labyMod$addEntity(Entity entity, InsertInfo ci) {
        Laby.fireEvent(new EntitySpawnEvent(entity.getId(), (net.labymod.api.client.entity.Entity) entity));
    }

    @Redirect(method = {"removeEntity"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void labyMod$removeEntity(Entity entity, Entity.RemovalReason param0) {
        entity.setRemoved(param0);
        Laby.fireEvent(new EntityDestructEvent((net.labymod.api.client.entity.Entity) entity));
    }
}
