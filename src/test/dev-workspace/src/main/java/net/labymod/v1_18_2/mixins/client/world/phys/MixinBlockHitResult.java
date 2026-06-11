package net.labymod.v1_18_2.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/world/phys/MixinBlockHitResult.class */
@Mixin({dpk.class})
@Implements({@Interface(iface = BlockHitResult.class, prefix = "blockHitResult$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockHitResult extends MixinHitResult implements BlockHitResult {
    private Direction labyMod$direction;
    private FloatVector3 labyMod$blockPosition;

    @Shadow
    @Final
    private gj c;

    @Shadow
    @Final
    private go b;

    @Shadow
    public abstract boolean d();

    @Shadow
    public abstract a c();

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public FloatVector3 getBlockPosition() {
        if (this.labyMod$blockPosition == null && this.c != null) {
            this.labyMod$blockPosition = new FloatVector3(this.c.u(), this.c.v(), this.c.w());
        }
        return this.labyMod$blockPosition;
    }

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public Direction getBlockDirection() {
        if (this.labyMod$direction == null && this.b != null) {
            this.labyMod$direction = MinecraftUtil.fromMinecraft(this.b);
        }
        return this.labyMod$direction;
    }

    @Intrinsic
    public boolean blockHitResult$isInside() {
        return d();
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public HitResult.HitType type() {
        return labyMod$getHitType(c());
    }
}
