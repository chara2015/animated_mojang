package net.labymod.v1_21_11.mixins.client.entity;

import java.util.Set;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.entity.DefaultDataWatcher;
import net.labymod.core.event.client.render.entity.EntityEyeHeightEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.OldAnimationRegistry;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/entity/MixinEntity.class */
@Mixin({cgk.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity {
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;

    @Shadow
    private float bA;

    @Shadow
    private ftm aV;

    @Shadow
    public double Y;

    @Shadow
    public double Z;

    @Shadow
    public double aa;

    @Shadow
    public dwo aU;

    @Shadow
    private float aZ;

    @Shadow
    public float ab;

    @Shadow
    public float ba;

    @Shadow
    public float ac;
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return mi.g.b(ay());
    });
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.aV.g;
    }, x -> {
        this.aV = this.aV.b(x, 0.0d, 0.0d);
    }, () -> {
        return this.aV.h;
    }, y -> {
        this.aV = this.aV.b(0.0d, y, 0.0d);
    }, () -> {
        return this.aV.i;
    }, z -> {
        this.aV = this.aV.b(0.0d, 0.0d, z);
    });
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.Y;
    }, x -> {
        this.Y = x;
    }, () -> {
        return this.Z;
    }, y -> {
        this.Z = y;
    }, () -> {
        return this.aa;
    }, z -> {
        this.aa = z;
    });

    @Shadow
    public abstract boolean shadow$cz();

    @Shadow
    public abstract boolean shadow$cA();

    @Shadow
    public abstract boolean shadow$cG();

    @Shadow
    public abstract UUID shadow$cY();

    @Shadow
    public abstract float i(float f);

    @Shadow
    public abstract float j(float f);

    @Shadow
    public abstract Set<String> aB();

    @Shadow
    public abstract boolean shadow$by();

    @Shadow
    public abstract cgk shadow$dz();

    @Shadow
    public abstract boolean shadow$bN();

    @Shadow
    public abstract boolean shadow$aV();

    @Shadow
    public abstract boolean shadow$bC();

    @Shadow
    public abstract boolean shadow$cp();

    @Shadow
    public abstract ftm shadow$dI();

    @Shadow
    public abstract cgn a(chx chxVar);

    @Shadow
    public abstract cgu<?> ay();

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().e((ddm) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position position() {
        return this.labyMod$position;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position previousPosition() {
        return this.labyMod$previousPosition;
    }

    @Intrinsic
    public boolean entity$isCrouching() {
        return shadow$cz();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$cA();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$cG();
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return shadow$cY();
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        return FloatVector3.calculateViewVector(i(partialTicks), j(partialTicks));
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(labyMod$getEntity().aF());
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        Object minecraftPose = mapper.toMinecraft(pose);
        if (minecraftPose == null) {
            return false;
        }
        return this.aU.a((cgk) this, a((chx) minecraftPose).a(shadow$dI()).h(1.0E-7d));
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return labyMod$getEntity().dk();
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.aZ;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.aZ = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.ab;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.ab = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.ba;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.ba = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.ac;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.ac = previousRotationPitch;
    }

    @Insert(method = {"setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"}, at = @At("TAIL"))
    private void labyMod$setBoundingBox(fth aabb, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) aabb.a, (float) aabb.b, (float) aabb.c, (float) aabb.d, (float) aabb.e, (float) aabb.f);
    }

    @Insert(method = {"getEyeY()D"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(InsertInfoReturnable<Double> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bA));
        info.setReturnValue(Double.valueOf(this.aV.h + ((double) event.getEyeHeight())));
    }

    @Insert(method = {"getEyeHeight()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(InsertInfoReturnable<Float> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bA));
        info.setReturnValue(Float.valueOf(event.getEyeHeight()));
    }

    @Insert(method = {"getPickRadius()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$oldRange(InsertInfoReturnable<Float> info) {
        OldAnimationRegistry oldAnimationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
        RangeOldAnimation animation = (RangeOldAnimation) oldAnimationRegistry.get(RangeOldAnimation.NAME);
        if (animation != null && animation.isEnabled()) {
            info.setReturnValue(Float.valueOf(animation.getOldPickRadius()));
        }
    }

    private cgk labyMod$getEntity() {
        return (cgk) this;
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

    @Override // net.labymod.api.client.entity.Entity
    public TagType nameTagType() {
        String string = getNameTagMatch();
        if (string == null) {
            return TagType.CUSTOM;
        }
        return TagType.fromString(string.substring(NAMETAG_IDENTIFIER.length()));
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setNameTagType(TagType type) {
        String string = getNameTagMatch();
        if (string != null) {
            aB().remove(string);
        }
        if (type != null) {
            aB().add("labymod-nametag:" + String.valueOf(type));
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    @Intrinsic
    public Entity getVehicle() {
        return shadow$dz();
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$by();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$bN();
    }

    @Intrinsic
    public boolean entity$isOnGround() {
        return shadow$aV();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Laby.references().componentMapper().fromMinecraftComponent(((cgk) this).ap());
    }

    private String getNameTagMatch() {
        for (String tag : aB()) {
            if (tag.startsWith(NAMETAG_IDENTIFIER)) {
                return tag;
            }
        }
        return null;
    }

    @Intrinsic
    public boolean entity$isUnderWater() {
        return shadow$bC();
    }

    @Intrinsic
    public boolean entity$isOnFire() {
        return shadow$cp();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
