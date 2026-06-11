package net.labymod.v1_17_1.mixins.client.world.phys;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.phys.hit.EntityHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.v1_17_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/world/phys/MixinEntityHitResult.class */
@Mixin({dmx.class})
@Implements({@Interface(iface = EntityHitResult.class, prefix = "entityHitResult$", remap = Interface.Remap.NONE)})
public abstract class MixinEntityHitResult extends MixinHitResult implements EntityHitResult {

    @Shadow
    @Final
    private atg b;

    @Shadow
    public abstract a c();

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public HitResult.HitType type() {
        return labyMod$getHitType(c());
    }

    @Intrinsic
    public Entity entityHitResult$getEntity() {
        if (this.b == null) {
            return null;
        }
        return MinecraftUtil.unwrapEntity(this.b);
    }
}
