package net.labymod.v26_1_1.mixins.client.world.phys;

import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v26_1_1.client.util.MinecraftUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/world/phys/MixinBlockHitResult.class */
@Mixin({BlockHitResult.class})
@Implements({@Interface(iface = net.labymod.api.client.world.phys.hit.BlockHitResult.class, prefix = "blockHitResult$", remap = Interface.Remap.NONE)})
public abstract class MixinBlockHitResult extends MixinHitResult implements net.labymod.api.client.world.phys.hit.BlockHitResult {
    private Direction labyMod$direction;
    private FloatVector3 labyMod$blockPosition;

    @Shadow
    @Final
    private BlockPos blockPos;

    @Shadow
    @Final
    private net.minecraft.core.Direction direction;

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    @Shadow
    public abstract boolean isInside();

    @Shadow
    public abstract HitResult.Type getType();

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public FloatVector3 getBlockPosition() {
        if (this.labyMod$blockPosition == null && this.blockPos != null) {
            this.labyMod$blockPosition = new FloatVector3(this.blockPos.getX(), this.blockPos.getY(), this.blockPos.getZ());
        }
        return this.labyMod$blockPosition;
    }

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public Direction getBlockDirection() {
        if (this.labyMod$direction == null && this.direction != null) {
            this.labyMod$direction = MinecraftUtil.fromMinecraft(this.direction);
        }
        return this.labyMod$direction;
    }

    @Intrinsic
    public boolean blockHitResult$isInside() {
        return isInside();
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public HitResult.HitType type() {
        return labyMod$getHitType(getType());
    }
}
