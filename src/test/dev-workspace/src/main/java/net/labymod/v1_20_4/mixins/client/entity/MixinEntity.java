package net.labymod.v1_20_4.mixins.client.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/entity/MixinEntity.class */
@Mixin({blv.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity {
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;

    @Shadow
    private float bi;

    @Shadow
    private elt u;

    @Shadow
    public double K;

    @Shadow
    public double L;

    @Shadow
    public double M;

    @Shadow
    public ctp t;

    @Shadow
    private float aG;

    @Shadow
    public float N;

    @Shadow
    public float aH;

    @Shadow
    public float O;
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return kd.g.b(ai());
    });
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.u.c;
    }, x -> {
        this.u = this.u.b(x, 0.0d, 0.0d);
    }, () -> {
        return this.u.d;
    }, y -> {
        this.u = this.u.b(0.0d, y, 0.0d);
    }, () -> {
        return this.u.e;
    }, z -> {
        this.u = this.u.b(0.0d, 0.0d, z);
    });
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.K;
    }, x -> {
        this.K = x;
    }, () -> {
        return this.L;
    }, y -> {
        this.L = y;
    }, () -> {
        return this.M;
    }, z -> {
        this.M = z;
    });

    @Shadow
    public abstract boolean shadow$bX();

    @Shadow
    public abstract boolean shadow$bY();

    @Shadow
    public abstract boolean shadow$ce();

    @Shadow
    public abstract UUID shadow$cw();

    @Shadow
    public abstract float g(float f);

    @Shadow
    public abstract float h(float f);

    @Shadow
    public abstract Set<String> ak();

    @Shadow
    public abstract boolean shadow$aZ();

    @Shadow
    public abstract blv shadow$cZ();

    @Shadow
    public abstract boolean shadow$bn();

    @Shadow
    public abstract boolean shadow$aC();

    @Shadow
    public abstract boolean shadow$be();

    @Shadow
    public abstract boolean shadow$bN();

    @Shadow
    public abstract blw a(bmx bmxVar);

    @Shadow
    public abstract elt shadow$dk();

    @Shadow
    public abstract blz<?> ai();

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
        return shadow$bX();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$bY();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$ce();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().d((cfi) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return shadow$cw();
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        return FloatVector3.calculateViewVector(g(partialTicks), h(partialTicks));
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(labyMod$getEntity().ap());
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        Object minecraftPose = mapper.toMinecraft(pose);
        if (minecraftPose == null) {
            return false;
        }
        return this.t.a((blv) this, a((bmx) minecraftPose).a(shadow$dk()).h(1.0E-7d));
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return labyMod$getEntity().cI();
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.aG;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.aG = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.N;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.N = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.aH;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.aH = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.O;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.O = previousRotationPitch;
    }

    @Insert(method = {"setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"}, at = @At("TAIL"))
    private void labyMod$setBoundingBox(elo aabb, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) aabb.a, (float) aabb.b, (float) aabb.c, (float) aabb.d, (float) aabb.e, (float) aabb.f);
    }

    @Insert(method = {"getEyeY()D"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(InsertInfoReturnable<Double> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bi));
        info.setReturnValue(Double.valueOf(this.u.d + ((double) event.getEyeHeight())));
    }

    @Insert(method = {"getEyeHeight()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(InsertInfoReturnable<Float> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bi));
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

    private blv labyMod$getEntity() {
        return (blv) this;
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
            ak().remove(string);
        }
        if (type != null) {
            ak().add("labymod-nametag:" + String.valueOf(type));
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    @Intrinsic
    public Entity getVehicle() {
        return shadow$cZ();
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$aZ();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$bn();
    }

    @Intrinsic
    public boolean entity$isOnGround() {
        return shadow$aC();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Laby.references().componentMapper().fromMinecraftComponent(((blv) this).ad());
    }

    private String getNameTagMatch() {
        for (String tag : ak()) {
            if (tag.startsWith(NAMETAG_IDENTIFIER)) {
                return tag;
            }
        }
        return null;
    }

    @Intrinsic
    public boolean entity$isUnderWater() {
        return shadow$be();
    }

    @Intrinsic
    public boolean entity$isOnFire() {
        return shadow$bN();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
