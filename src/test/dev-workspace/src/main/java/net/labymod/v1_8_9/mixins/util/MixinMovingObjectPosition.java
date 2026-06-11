package net.labymod.v1_8_9.mixins.util;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.EntityHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinMovingObjectPosition.class */
@Mixin({auh.class})
public class MixinMovingObjectPosition implements HitResult, EntityHitResult, BlockHitResult {

    @Shadow
    public a a;

    @Shadow
    public cq b;
    private FloatVector3 labyMod$hitLocation;
    private FloatVector3 labyMod$blockPosition;
    private Direction labyMod$blockHitDirection;
    private HitResult.HitType labyMod$hitType;
    private Entity labyMod$entity;

    @Inject(method = {"<init>(Lnet/minecraft/util/MovingObjectPosition$MovingObjectType;Lnet/minecraft/util/Vec3;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/util/BlockPos;)V"}, at = {@At("RETURN")})
    private void labyMod$createApiVector(a type, aui location, cq facing, cj blockPosition, CallbackInfo ci) {
        this.labyMod$hitLocation = new FloatVector3((float) location.a, (float) location.b, (float) location.c);
        this.labyMod$blockPosition = new FloatVector3(blockPosition.n(), blockPosition.o(), blockPosition.p());
        if (this.b != null) {
            this.labyMod$blockHitDirection = labyMod$getDirection(this.b);
        }
        if (this.a != null) {
            this.labyMod$hitType = labyMod$getHitType(this.a);
        }
    }

    @Inject(method = {"<init>(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Vec3;)V"}, at = {@At("RETURN")})
    private void labyMod$createApiVectorEntity(pk entity, aui location, CallbackInfo ci) {
        this.labyMod$hitLocation = new FloatVector3((float) location.a, (float) location.b, (float) location.c);
        this.labyMod$entity = MinecraftUtil.unwrapEntity(entity);
        this.labyMod$hitType = labyMod$getHitType(this.a);
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public FloatVector3 location() {
        return this.labyMod$hitLocation;
    }

    @Override // net.labymod.api.client.world.phys.hit.HitResult
    public HitResult.HitType type() {
        return this.labyMod$hitType;
    }

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public FloatVector3 getBlockPosition() {
        return this.labyMod$blockPosition;
    }

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public Direction getBlockDirection() {
        return this.labyMod$blockHitDirection;
    }

    @Override // net.labymod.api.client.world.phys.hit.BlockHitResult
    public boolean isInside() {
        return false;
    }

    @Override // net.labymod.api.client.world.phys.hit.EntityHitResult
    public Entity getEntity() {
        return this.labyMod$entity;
    }

    private HitResult.HitType labyMod$getHitType(a type) {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[type.ordinal()]) {
            case 1:
                return HitResult.HitType.MISS;
            case 2:
                return HitResult.HitType.BLOCK;
            case 3:
                return HitResult.HitType.ENTITY;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_8_9.mixins.util.MixinMovingObjectPosition$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinMovingObjectPosition$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[cq.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.a.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.c.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.d.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.e.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[cq.f.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType = new int[a.values().length];
            try {
                $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$util$MovingObjectPosition$MovingObjectType[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    private Direction labyMod$getDirection(cq direction) {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$util$EnumFacing[direction.ordinal()]) {
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.NORTH;
            case 4:
                return Direction.SOUTH;
            case 5:
                return Direction.WEST;
            case 6:
                return Direction.EAST;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(direction));
        }
    }
}
