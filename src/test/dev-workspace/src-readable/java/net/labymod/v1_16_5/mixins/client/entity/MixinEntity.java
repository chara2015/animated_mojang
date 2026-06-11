package net.labymod.v1_16_5.mixins.client.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/entity/MixinEntity.class */
@Mixin({aqa.class})
@Implements({@Interface(iface = Entity.class, prefix = "entity$", remap = Interface.Remap.NONE)})
public abstract class MixinEntity implements Entity {
    private static final String NAMETAG_IDENTIFIER = "labymod-nametag:";
    private AxisAlignedBoundingBox labyMod$boundingBox;
    private boolean labyMod$rendered;

    @Shadow
    private float aD;

    @Shadow
    private dcn ai;

    @Shadow
    public double m;

    @Shadow
    public double n;

    @Shadow
    public double o;

    @Shadow
    public brx l;

    @Shadow
    private float p;

    @Shadow
    public float r;

    @Shadow
    public float q;

    @Shadow
    public float s;
    private final DataWatcher labyMod$dataWatcher = new DefaultDataWatcher();
    private final Lazy<ResourceLocation> labyMod$entityId = Lazy.of(() -> {
        return gm.S.b(X());
    });
    private final Position labyMod$position = new DynamicPosition(() -> {
        return this.ai.b;
    }, x -> {
        this.ai = this.ai.b(x, 0.0d, 0.0d);
    }, () -> {
        return this.ai.c;
    }, y -> {
        this.ai = this.ai.b(0.0d, y, 0.0d);
    }, () -> {
        return this.ai.d;
    }, z -> {
        this.ai = this.ai.b(0.0d, 0.0d, z);
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
    public abstract boolean shadow$bz();

    @Shadow
    public abstract boolean shadow$bA();

    @Shadow
    public abstract boolean shadow$bF();

    @Shadow
    public abstract UUID shadow$bS();

    @Shadow
    protected abstract boolean c(aqx aqxVar);

    @Shadow
    public abstract float g(float f);

    @Shadow
    public abstract float h(float f);

    @Shadow
    public abstract Set<String> Z();

    @Shadow
    public abstract boolean shadow$aE();

    @Shadow
    public abstract aqa shadow$ct();

    @Shadow
    public abstract boolean shadow$aQ();

    @Shadow
    public abstract boolean shadow$ao();

    @Shadow
    public abstract boolean shadow$aI();

    @Shadow
    public abstract boolean shadow$bq();

    @Shadow
    public abstract void ad();

    @Shadow
    public abstract aqe<?> X();

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
        return shadow$bz();
    }

    @Intrinsic
    public boolean entity$isSprinting() {
        return shadow$bA();
    }

    @Intrinsic
    public boolean entity$isInvisible() {
        return shadow$bF();
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return labyMod$getEntity().c((bfw) player);
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return shadow$bS();
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
        return mapper.fromMinecraft(labyMod$getEntity().ae());
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        EntityPoseMapper mapper = Laby.references().entityPoseMapper();
        Object minecraftPose = mapper.toMinecraft(pose);
        if (minecraftPose == null) {
            return false;
        }
        return c((aqx) minecraftPose);
    }

    @Intrinsic
    public float entity$getEyeHeight() {
        return labyMod$getEntity().ce();
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return this.p;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
        this.p = rotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return this.r;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
        this.r = previousRotationYaw;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return this.q;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
        this.q = rotationPitch;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return this.s;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
        this.s = previousRotationPitch;
    }

    @Insert(method = {"setBoundingBox(Lnet/minecraft/world/phys/AABB;)V"}, at = @At("TAIL"))
    private void labyMod$setBoundingBox(dci aabb, InsertInfo info) {
        this.labyMod$boundingBox = new AxisAlignedBoundingBox((float) aabb.a, (float) aabb.b, (float) aabb.c, (float) aabb.d, (float) aabb.e, (float) aabb.f);
    }

    @Insert(method = {"getEyeY()D"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeY(InsertInfoReturnable<Double> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.aD));
        info.setReturnValue(Double.valueOf(this.ai.c + ((double) event.getEyeHeight())));
    }

    @Insert(method = {"getEyeHeight()F"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireEntityEyeHeightEventEyeHeight(InsertInfoReturnable<Float> info) {
        EntityEyeHeightEvent event = (EntityEyeHeightEvent) Laby.fireEvent(new EntityEyeHeightEvent(this, this.aD));
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

    private aqa labyMod$getEntity() {
        return (aqa) this;
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
            Z().remove(string);
        }
        if (type != null) {
            Z().add("labymod-nametag:" + String.valueOf(type));
        }
    }

    @Override // net.labymod.api.client.entity.Entity
    @Intrinsic
    public Entity getVehicle() {
        return shadow$ct();
    }

    @Intrinsic
    public boolean entity$isInWater() {
        return shadow$aE();
    }

    @Intrinsic
    public boolean entity$isInLava() {
        return shadow$aQ();
    }

    @Intrinsic
    public boolean entity$isOnGround() {
        return shadow$ao();
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Laby.references().componentMapper().fromMinecraftComponent(((aqa) this).R());
    }

    private String getNameTagMatch() {
        for (String tag : Z()) {
            if (tag.startsWith(NAMETAG_IDENTIFIER)) {
                return tag;
            }
        }
        return null;
    }

    @Intrinsic
    public boolean entity$isUnderWater() {
        return shadow$aI();
    }

    @Intrinsic
    public boolean entity$isOnFire() {
        return shadow$bq();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return this.labyMod$entityId.get();
    }
}
