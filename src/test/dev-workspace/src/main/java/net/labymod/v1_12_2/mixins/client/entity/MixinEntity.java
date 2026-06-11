package net.labymod.v1_12_2.mixins.client.entity;

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
import net.labymod.v1_12_2.client.util.VersionedWailaUtil;
import net.labymod.v1_12_2.forge.entity.ForgeEntity;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/MixinEntity.class */
@Mixin({vg.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity, ForgeEntity {
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";

    @Shadow
    public double p;

    @Shadow
    public double q;

    @Shadow
    public double r;

    @Shadow
    public double o;

    @Shadow
    public double n;

    @Shadow
    public double m;

    @Shadow
    private bhb av;

    @Shadow
    public float v;

    @Shadow
    public float x;

    @Shadow
    public boolean z;

    @Shadow
    public vg au;

    @Shadow
    public float w;

    @Shadow
    private int h;

    @Shadow
    public float y;

    @Shadow
    public amu l;

    @Unique
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
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.m;
    }, x -> {
        this.m = x;
    }, () -> {
        return this.n;
    }, y -> {
        this.n = y;
    }, () -> {
        return this.o;
    }, z -> {
        this.o = z;
    });

    @Shadow
    public abstract boolean shadow$aV();

    @Shadow
    public abstract boolean shadow$aX();

    @Shadow
    public abstract float by();

    @Shadow
    public abstract boolean shadow$ao();

    @Shadow
    public abstract boolean shadow$au();

    @Shadow
    public abstract boolean shadow$aR();

    @Shadow
    public abstract boolean a(bcz bczVar);

    @Shadow
    public abstract bhe e(float f);

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
        return labyMod$getEntity().aU();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$aV();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$aX();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().e((aed) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return labyMod$getEntity().bm();
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        vg player = labyMod$getEntity();
        if (player.aU()) {
            return EntityPose.CROUCHING;
        }
        if (player.F) {
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
        return this.v;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.v = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.x;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.x = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.w;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.w = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.y;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.y = previousRotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        bhe look = e(partialTicks);
        return new FloatVector3((float) look.b, (float) look.c, (float) look.d);
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return by();
    }

    @Insert(method = {"setEntityBoundingBox"}, at = @At("TAIL"))
    private void labyMod$setAxisAlignedBoundingBox(bhb box, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) box.a, (float) box.b, (float) box.c, (float) box.d, (float) box.e, (float) box.f);
    }

    private vg labyMod$getEntity() {
        return (vg) this;
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
        return shadow$ao();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$au();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Entity getVehicle() {
        return this.au;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnGround() {
        return this.z;
    }

    @Override // net.labymod.v1_12_2.forge.entity.ForgeEntity
    public fy getForgeEntityData() {
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
    public Component nameComponent() {
        return Component.text(VersionedWailaUtil.mapEntityName((vg) this));
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isUnderWater() {
        return a(bcz.h);
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnFire() {
        return shadow$aR();
    }

    @Inject(method = {"isEntityInsideOpaqueBlock"}, at = {@At("HEAD")})
    private void labyMod$getViewBlockingStateHeadSetup(CallbackInfoReturnable<Boolean> cir) {
        if (!this.l.G) {
            return;
        }
        this.labymod$stack.push();
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.PRE));
        Laby.fireEvent(new CameraSetupEvent(this.labymod$stack, Phase.POST));
        this.labymod$offset = this.labymod$stack.transformVector(0.0f, 0.0f, 0.0f);
        this.labymod$stack.pop();
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 1)})
    private int labyMod$getViewBlockingStateX(double x, Operation<Integer> original) {
        if (!this.l.G) {
            return ((Integer) original.call(new Object[]{Double.valueOf(x)})).intValue();
        }
        return ((Integer) original.call(new Object[]{Double.valueOf(x - ((double) this.labymod$offset.getX()))})).intValue();
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 0)})
    private int labyMod$getViewBlockingStateY(double y, Operation<Integer> original) {
        if (!this.l.G) {
            return ((Integer) original.call(new Object[]{Double.valueOf(y)})).intValue();
        }
        return rk.c(y - ((double) this.labymod$offset.getY()));
    }

    @WrapOperation(method = {"isEntityInsideOpaqueBlock"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(D)I", ordinal = 2)})
    private int labyMod$getViewBlockingStateZ(double z, Operation<Integer> original) {
        if (!this.l.G) {
            return ((Integer) original.call(new Object[]{Double.valueOf(z)})).intValue();
        }
        return ((Integer) original.call(new Object[]{Double.valueOf(z - ((double) this.labymod$offset.getZ()))})).intValue();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
