package net.labymod.api.client.entity;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.volt.annotation.RenamedFrom;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/Entity.class */
public interface Entity {
    Position position();

    Position previousPosition();

    boolean isCrouching();

    boolean isInvisible();

    boolean isInvisibleFor(Player player);

    boolean isSprinting();

    UUID getUniqueId();

    AxisAlignedBoundingBox axisAlignedBoundingBox();

    FloatVector3 perspectiveVector(float f);

    EntityPose entityPose();

    boolean canEnterEntityPose(EntityPose entityPose);

    float getEyeHeight();

    float getRotationYaw();

    void setRotationYaw(float f);

    float getPreviousRotationYaw();

    void setPreviousRotationYaw(float f);

    float getRotationPitch();

    void setRotationPitch(float f);

    float getPreviousRotationPitch();

    void setPreviousRotationPitch(float f);

    DataWatcher dataWatcher();

    TagType nameTagType();

    void setNameTagType(TagType tagType);

    void setRendered(boolean z);

    boolean isRendered();

    boolean isInWater();

    boolean isInLava();

    boolean isUnderWater();

    boolean isOnFire();

    boolean isOnGround();

    Entity getVehicle();

    Component nameComponent();

    ResourceLocation entityId();

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosX() {
        return (float) position().getX();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosX() {
        return (float) previousPosition().getX();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosY() {
        return (float) position().getY();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosY() {
        return (float) previousPosition().getY();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPosZ() {
        return (float) position().getZ();
    }

    @Deprecated(forRemoval = true, since = "4.2.53")
    default float getPreviousPosZ() {
        return (float) previousPosition().getZ();
    }

    default boolean isCrouchingPose() {
        return entityPose() == EntityPose.CROUCHING;
    }

    default double getDistanceSquared(Entity entity) {
        return position().distanceSquared(entity.position());
    }

    default double getDistanceSquared(FloatVector3 location) {
        return getDistanceSquared(location.getX(), location.getY(), location.getZ());
    }

    default double getDistanceSquared(DoubleVector3 location) {
        return getDistanceSquared(location.getX(), location.getY(), location.getZ());
    }

    default double getDistanceSquared(double x, double y, double z) {
        return position().distanceSquared(x, y, z);
    }

    default boolean isInLiquid() {
        return isInWater() || isInLava();
    }

    default float getForwardMotion() {
        Position position = position();
        Position previousPosition = previousPosition();
        double motionX = previousPosition.getX() - position.getX();
        double motionZ = previousPosition.getZ() - position.getZ();
        float yawSin = MathHelper.sin(Math.toRadians(getRotationYaw()));
        float yawCos = -MathHelper.cos(Math.toRadians(getRotationYaw()));
        return (float) ((motionX * ((double) yawSin)) + (motionZ * ((double) yawCos)));
    }

    default FloatVector3 eyePosition() {
        return position().toFloatVector3().add(0.0f, getEyeHeight(), 0.0f);
    }

    default DoubleVector3 getTargetBlock(float maxDistance, float stepsPerBlock, float partialTicks) {
        DoubleVector3 source = position().toDoubleVector3().add(0.0d, getEyeHeight(), 0.0d);
        FloatVector3 direction = perspectiveVector(partialTicks);
        direction.multiply(1.0f / stepsPerBlock);
        ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
        int max = (int) (maxDistance * stepsPerBlock);
        for (int i = 0; i < max * stepsPerBlock && !world.hasSolidBlockAt(MathHelper.floor(source.getX()), MathHelper.floor(source.getY()), MathHelper.floor(source.getZ())); i++) {
            source.add(direction);
            if (i + 1 == max) {
                return null;
            }
        }
        return source;
    }

    @RenamedFrom("position()Lnet/labymod/api/util/math/vector/FloatVector3;")
    default FloatVector3 deprecated$position() {
        return position().toFloatVector3();
    }
}
