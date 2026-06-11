package net.labymod.v1_19_4.mixins.client.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/entity/MixinEntity.class */
@Mixin({bfh.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity {
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;

    @Shadow
    private float bf;

    @Shadow
    private ede t;

    @Shadow
    public double I;

    @Shadow
    public double J;

    @Shadow
    public double K;

    @Shadow
    public cmi H;

    @Shadow
    private float aE;

    @Shadow
    public float L;

    @Shadow
    private float aF;

    @Shadow
    public float M;
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return ja.h.b(ae());
    });
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.t.c;
    }, x -> {
        this.t = this.t.b(x, 0.0d, 0.0d);
    }, () -> {
        return this.t.d;
    }, y -> {
        this.t = this.t.b(0.0d, y, 0.0d);
    }, () -> {
        return this.t.e;
    }, z -> {
        this.t = this.t.b(0.0d, 0.0d, z);
    });
    private final Position labyMod$previousPosition = new DynamicPosition(() -> {
        return this.I;
    }, x -> {
        this.I = x;
    }, () -> {
        return this.J;
    }, y -> {
        this.J = y;
    }, () -> {
        return this.K;
    }, z -> {
        this.K = z;
    });

    @Shadow
    public abstract boolean shadow$bT();

    @Shadow
    public abstract boolean shadow$bU();

    @Shadow
    public abstract boolean shadow$ca();

    @Shadow
    public abstract UUID shadow$cs();

    @Shadow
    protected abstract boolean d(bgj bgjVar);

    @Shadow
    public abstract float k(float f);

    @Shadow
    public abstract float l(float f);

    @Shadow
    public abstract Set<String> ag();

    @Shadow
    public abstract boolean shadow$aT();

    @Shadow
    public abstract bfh shadow$cV();

    @Shadow
    public abstract boolean shadow$bg();

    @Shadow
    public abstract boolean shadow$ax();

    @Shadow
    public abstract boolean shadow$aX();

    @Shadow
    public abstract boolean shadow$bK();

    @Shadow
    public abstract bfl<?> ae();

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
        return shadow$bT();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$bU();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$ca();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().d((bym) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return shadow$cs();
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return this.labyMod$boundingBox;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        return FloatVector3.calculateViewVector(k(partialTicks), l(partialTicks));
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        return mapper.fromMinecraft(labyMod$getEntity().al());
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        Object minecraftPose = mapper.toMinecraft(pose);
        if (minecraftPose == null) {
            return false;
        }
        return d((bgj) minecraftPose);
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return labyMod$getEntity().cE();
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.aE;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.aE = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.L;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.L = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.aF;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.aF = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.M;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.M = previousRotationPitch;
    }

    @Insert(method = {"setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"}, at = @At("TAIL"))
    private void labyMod$setBoundingBox(ecz aabb, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) aabb.a, (float) aabb.b, (float) aabb.c, (float) aabb.d, (float) aabb.e, (float) aabb.f);
    }

    @Insert(method = {"getEyeY()D"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(InsertInfoReturnable<Double> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bf));
        info.setReturnValue(Double.valueOf(this.t.d + ((double) event.getEyeHeight())));
    }

    @Insert(method = {"getEyeHeight()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(InsertInfoReturnable<Float> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.bf));
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

    private bfh labyMod$getEntity() {
        return (bfh) this;
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
            ag().remove(string);
        }
        if (type != null) {
            ag().add("labymod-nametag:" + String.valueOf(type));
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    @Intrinsic
    public Entity getVehicle() {
        return shadow$cV();
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$aT();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$bg();
    }

    @Intrinsic
    public boolean entity$isOnGround() {
        return shadow$ax();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Laby.references().componentMapper().fromMinecraftComponent(((bfh) this).Z());
    }

    private String getNameTagMatch() {
        for (String tag : ag()) {
            if (tag.startsWith(NAMETAG_IDENTIFIER)) {
                return tag;
            }
        }
        return null;
    }

    @Intrinsic
    public boolean entity$isUnderWater() {
        return shadow$aX();
    }

    @Intrinsic
    public boolean entity$isOnFire() {
        return shadow$bK();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
