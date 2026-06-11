package net.labymod.v1_17_1.mixins.client.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/client/entity/MixinEntity.class */
@Mixin({atg.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity {
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;

    @Shadow
    private float aX;

    @Shadow
    private dna av;

    @Shadow
    public double u;

    @Shadow
    public double v;

    @Shadow
    public double w;

    @Shadow
    public bwq t;

    @Shadow
    private float ay;

    @Shadow
    public float x;

    @Shadow
    public float az;

    @Shadow
    public float y;
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return gw.Y.b(Y());
    });
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.av.b;
    }, x -> {
        this.av = this.av.b(x, 0.0d, 0.0d);
    }, () -> {
        return this.av.c;
    }, y -> {
        this.av = this.av.b(0.0d, y, 0.0d);
    }, () -> {
        return this.av.d;
    }, z -> {
        this.av = this.av.b(0.0d, 0.0d, z);
    });
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.u;
    }, x -> {
        this.u = x;
    }, () -> {
        return this.v;
    }, y -> {
        this.v = y;
    }, () -> {
        return this.w;
    }, z -> {
        this.w = z;
    });

    @Shadow
    public abstract boolean shadow$bI();

    @Shadow
    public abstract boolean shadow$bJ();

    @Shadow
    public abstract boolean shadow$bP();

    @Shadow
    public abstract UUID shadow$ch();

    @Shadow
    protected abstract boolean c(aug augVar);

    @Shadow
    public abstract float f(float f);

    @Shadow
    public abstract float g(float f);

    @Shadow
    public abstract Set<String> aa();

    @Shadow
    public abstract boolean shadow$aL();

    @Shadow
    public abstract atg shadow$cI();

    @Shadow
    public abstract boolean shadow$aX();

    @Shadow
    public abstract boolean shadow$ar();

    @Shadow
    public abstract boolean shadow$aP();

    @Shadow
    public abstract boolean shadow$bz();

    @Shadow
    public abstract atk<?> Y();

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
        return shadow$bI();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$bJ();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$bP();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().c((bke) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return shadow$ch();
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        return FloatVector3.calculateViewVector(f(partialTicks), g(partialTicks));
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(labyMod$getEntity().af());
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        Object minecraftPose = mapper.toMinecraft(pose);
        if (minecraftPose == null) {
            return false;
        }
        return c((aug) minecraftPose);
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return labyMod$getEntity().ct();
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.ay;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.ay = rotationYaw;
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
        return this.az;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.az = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.y;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.y = previousRotationPitch;
    }

    @Insert(method = {"setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"}, at = @At("TAIL"))
    private void labyMod$setBoundingBox(dmv aabb, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) aabb.a, (float) aabb.b, (float) aabb.c, (float) aabb.d, (float) aabb.e, (float) aabb.f);
    }

    @Insert(method = {"getEyeY()D"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(InsertInfoReturnable<Double> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.aX));
        info.setReturnValue(Double.valueOf(this.av.c + ((double) event.getEyeHeight())));
    }

    @Insert(method = {"getEyeHeight()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(InsertInfoReturnable<Float> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.aX));
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

    private atg labyMod$getEntity() {
        return (atg) this;
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
            aa().remove(string);
        }
        if (type != null) {
            aa().add("labymod-nametag:" + String.valueOf(type));
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    @Intrinsic
    public Entity getVehicle() {
        return shadow$cI();
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$aL();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$aX();
    }

    @Intrinsic
    public boolean entity$isOnGround() {
        return shadow$ar();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Laby.references().componentMapper().fromMinecraftComponent(((atg) this).S());
    }

    private String getNameTagMatch() {
        for (String tag : aa()) {
            if (tag.startsWith(NAMETAG_IDENTIFIER)) {
                return tag;
            }
        }
        return null;
    }

    @Intrinsic
    public boolean entity$isUnderWater() {
        return shadow$aP();
    }

    @Intrinsic
    public boolean entity$isOnFire() {
        return shadow$bz();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
