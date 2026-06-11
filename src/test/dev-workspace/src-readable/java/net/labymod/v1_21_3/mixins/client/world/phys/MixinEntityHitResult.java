package net.labymod.v1_21_3.mixins.client.world.phys;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.phys.hit.EntityHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/world/phys/MixinEntityHitResult.class */
@Mixin({fbv.class})
public abstract class MixinEntityHitResult extends MixinHitResult implements EntityHitResult {
    private Entity labyMod$entity;

    @Shadow
    public abstract a d();

    @Insert(method = {"<init>(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V"}, at = @At("TAIL"))
    private void createApiEntity(bvk entity, fby position, InsertInfo info) {
        this.labyMod$entity = MinecraftUtil.unwrapEntity(entity);
    }

    @Override // net.labymod.api.client.world.phys.hit.EntityHitResult
    public Entity getEntity() {
        return this.labyMod$entity;
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public HitResult.HitType type() {
        return labyMod$getHitType(d());
    }
}
