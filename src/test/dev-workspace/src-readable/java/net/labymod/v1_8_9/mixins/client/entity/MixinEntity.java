package net.labymod.v1_8_9.mixins.client.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.entity.DefaultDataWatcher;
import net.labymod.core.util.LegacyEntityTypeRegistry;
import net.labymod.v1_8_9.client.util.VersionedWailaUtil;
import net.labymod.v1_8_9.forge.entity.ForgeEntity;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/entity/MixinEntity.class */
@Mixin({pk.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity, ForgeEntity {
    private boolean labyMod$rendered;
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";

    @Shadow
    public double s;

    @Shadow
    public double t;

    @Shadow
    public double u;

    @Shadow
    public double r;

    @Shadow
    public double q;

    @Shadow
    public double p;

    @Shadow
    private aug f;

    @Shadow
    public float y;

    @Shadow
    public float A;

    @Shadow
    public boolean C;

    @Shadow
    public pk m;

    @Shadow
    public float z;

    @Shadow
    public float B;

    @Shadow
    public adm o;
    private final AxisAlignedBoundingBox labyMod$boundingBox = new AxisAlignedBoundingBox();
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return LegacyEntityTypeRegistry.getId(getClass());
    });

    @ApiStatus.ScheduledForRemoval(inVersion = "forge support")
    @Deprecated
    private TagType labyMod$tagType = TagType.CUSTOM;

    @Unique
    private final Stack labymod$stack = Stack.create((StackProvider) new DefaultStackProvider());

    @Unique
    private FloatVector3 labymod$offset = FloatVector3.ZERO;
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.s;
    }, x -> {
        this.s = x;
    }, () -> {
        return this.t;
    }, y -> {
        this.t = y;
    }, () -> {
        return this.u;
    }, z -> {
        this.u = z;
    });
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.p;
    }, x -> {
        this.p = x;
    }, () -> {
        return this.q;
    }, y -> {
        this.q = y;
    }, () -> {
        return this.r;
    }, z -> {
        this.r = z;
    });

    @Shadow
    public abstract boolean shadow$aw();

    @Shadow
    public abstract boolean shadow$ax();

    @Shadow
    public abstract float aS();

    @Shadow
    public abstract boolean shadow$V();

    @Shadow
    public abstract boolean shadow$ab();

    @Shadow
    public abstract boolean shadow$at();

    @Shadow
    public abstract boolean a(arm armVar);

    @Shadow
    public aui d(float partialTicks) {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position position() {
        return this.labyMod$position;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position previousPosition() {
        return this.labyMod$previousPosition;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isCrouching() {
        return labyMod$getEntity().av();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$aw();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$ax();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().f((wn) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return labyMod$getEntity().aK();
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        pk player = labyMod$getEntity();
        if (player.av()) {
            return EntityPose.CROUCHING;
        }
        if (player.I) {
            return EntityPose.DYING;
        }
        return EntityPose.STANDING;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        return true;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.y;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.y = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.A;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.A = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.z;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.z = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.B;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.B = previousRotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        aui look = d(partialTicks);
        return new FloatVector3((float) look.a, (float) look.b, (float) look.c);
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return aS();
    }

    @Insert(method = {"setEntityBoundingBox(Lnet/minecraft/util/AxisAlignedBB;)V"}, at = @At("TAIL"))
    private void labyMod$setAxisAlignedBoundingBox(aug box, InsertInfo info) {
        this.labyMod$boundingBox.set((float) box.a, (float) box.b, (float) box.c, (float) box.d, (float) box.e, (float) box.f);
    }

    private pk labyMod$getEntity() {
        return (pk) this;
    }

    @Override // net.labymod.api.client.entity.Entity
    public DataWatcher dataWatcher() {
        return this.labyMod$dataWatcher;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRendered(boolean rendered) {
        this.labyMod$rendered = rendered;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isRendered() {
        return this.labyMod$rendered;
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$V();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$ab();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Entity getVehicle() {
        return this.m;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnGround() {
        return this.C;
    }

    @Override // net.labymod.v1_8_9.forge.entity.ForgeEntity
    public dn getForgeEntityData() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public TagType nameTagType() {
        return this.labyMod$tagType;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setNameTagType(TagType type) {
        if (type == null) {
            this.labyMod$tagType = TagType.CUSTOM;
        } else {
            this.labyMod$tagType = type;
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isUnderWater() {
        return a(arm.h);
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnFire() {
        return shadow$at();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Component.text(VersionedWailaUtil.mapEntityName((pk) this));
    }

    @Inject(method = {"isEntityInsideOpaqueBlock"}, at = {@At("HEAD")})
    private void labyMod$getViewBlockingStateHeadSetup(CallbackInfoReturnable<Boolean> cir) {
        if (!this.o.D) {
            return;
        }
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f);
        this.labymod$stack.pop();
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 1)})
    private int labyMod$getViewBlockingStateX(double x, Operation<Integer> original) {
        if (!this.o.D) {
            return ((Integer) original.call(new Object[]{Double.valueOf(x)})).intValue();
        }
        return ((Integer) original.call(new Object[]{Double.valueOf(x - ((double) this.labymod$offset.getX()))})).intValue();
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 0)})
    private int labyMod$getViewBlockingStateY(double y, Operation<Integer> original) {
        if (!this.o.D) {
            return ((Integer) original.call(new Object[]{Double.valueOf(y)})).intValue();
        }
        return ((Integer) original.call(new Object[]{Double.valueOf(y - ((double) this.labymod$offset.getY()))})).intValue();
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/MathHelper;floor_double(D)I", ordinal = 2)})
    private int labyMod$getViewBlockingStateZ(double z, Operation<Integer> original) {
        if (!this.o.D) {
            return ((Integer) original.call(new Object[]{Double.valueOf(z)})).intValue();
        }
        return ((Integer) original.call(new Object[]{Double.valueOf(z - ((double) this.labymod$offset.getZ()))})).intValue();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
