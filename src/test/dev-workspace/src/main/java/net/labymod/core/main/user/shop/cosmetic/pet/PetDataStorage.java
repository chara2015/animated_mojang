package net.labymod.core.main.user.shop.cosmetic.pet;

import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.render.model.animation.DefaultAnimationController;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/PetDataStorage.class */
public class PetDataStorage {
    private final DoubleVector3 position;
    private final DoubleVector3 offset;
    private final FloatVector3 rotation;
    private final DoubleVector3 previousPosition;
    private final FloatVector3 previousRotation;
    private final AnimationController animationController;
    private DoubleVector3 ownerPosition;
    private boolean moving;
    private long flyingChangedAt;
    private boolean attached;
    private long aboveShoulderChangedAt;
    private boolean aboveShoulder;
    private long timeMovedChangedAt;
    private boolean rightShoulder;
    private float lastPartialTicks;
    private boolean fakeIdle;
    private boolean wasFallFlying;
    private long fallFlyingChangedAt;

    private PetDataStorage() {
        this.attached = true;
        this.position = new DoubleVector3();
        this.offset = new DoubleVector3();
        this.rotation = new FloatVector3();
        this.previousPosition = new DoubleVector3();
        this.previousRotation = new FloatVector3();
        this.animationController = new DefaultAnimationController(DefaultAnimationController.SHARED_MODEL_ANIMATION);
        this.ownerPosition = new DoubleVector3();
    }

    private PetDataStorage(@NotNull PetDataStorage other) {
        this.position = other.position;
        this.offset = other.offset;
        this.rotation = other.rotation;
        this.previousPosition = other.previousPosition;
        this.previousRotation = other.previousRotation;
        this.animationController = other.animationController;
        this.ownerPosition = other.ownerPosition;
        this.moving = other.moving;
        this.flyingChangedAt = other.flyingChangedAt;
        this.attached = other.attached;
        this.aboveShoulderChangedAt = other.aboveShoulderChangedAt;
        this.aboveShoulder = other.aboveShoulder;
        this.timeMovedChangedAt = other.timeMovedChangedAt;
        this.rightShoulder = other.rightShoulder;
        this.lastPartialTicks = other.lastPartialTicks;
        this.fakeIdle = other.fakeIdle;
        this.wasFallFlying = other.wasFallFlying;
        this.fallFlyingChangedAt = other.fallFlyingChangedAt;
    }

    @NotNull
    public static PetDataStorage create() {
        return new PetDataStorage();
    }

    @NotNull
    public static PetDataStorage copyOf(PetDataStorage other) {
        return new PetDataStorage(other);
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void teleport(double x, double y, double z, float rotationX, float rotationY, float rotationZ) {
        this.previousPosition.set(this.position);
        this.previousRotation.set(this.rotation);
        this.position.set(x, y, z);
        this.rotation.set(rotationX, rotationY, rotationZ);
    }

    public boolean isAttachedToOwner() {
        return !(this.moving || this.animationController.isPlaying()) || (this.attached && this.animationController.isPlaying());
    }

    public void updateMovingState(boolean moving) {
        this.moving = moving;
        this.flyingChangedAt = TimeUtil.getMillis();
    }

    public DoubleVector3 getPosition() {
        return this.position;
    }

    public DoubleVector3 getOffset() {
        return this.offset;
    }

    public FloatVector3 getRotation() {
        return this.rotation;
    }

    public DoubleVector3 getPreviousPosition() {
        return this.previousPosition;
    }

    public FloatVector3 getPreviousRotation() {
        return this.previousRotation;
    }

    public long getFlyingChangedAt() {
        return this.flyingChangedAt;
    }

    public AnimationController getAnimationController() {
        return this.animationController;
    }

    public boolean isAttached() {
        return this.attached;
    }

    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    public long getAboveShoulderChangedAt() {
        return this.aboveShoulderChangedAt;
    }

    public void setAboveShoulderChangedAt(long aboveShoulderChangedAt) {
        this.aboveShoulderChangedAt = aboveShoulderChangedAt;
    }

    public boolean isAboveShoulder() {
        return this.aboveShoulder;
    }

    public void setAboveShoulder(boolean aboveShoulder) {
        this.aboveShoulder = aboveShoulder;
    }

    public long getTimeMovedChangedAt() {
        return this.timeMovedChangedAt;
    }

    public void setTimeMovedChangedAt(long timeMovedChangedAt) {
        this.timeMovedChangedAt = timeMovedChangedAt;
    }

    public boolean isRightShoulder() {
        return this.rightShoulder;
    }

    public void setRightShoulder(boolean rightShoulder) {
        this.rightShoulder = rightShoulder;
    }

    public DoubleVector3 getOwnerPosition() {
        return this.ownerPosition;
    }

    public void setOwnerPosition(DoubleVector3 ownerPosition) {
        this.ownerPosition = ownerPosition;
    }

    public float getLastPartialTicks() {
        return this.lastPartialTicks;
    }

    public void setLastPartialTicks(float lastPartialTicks) {
        this.lastPartialTicks = lastPartialTicks;
    }

    public void setFakeIdle(boolean fakeIdle) {
        this.fakeIdle = fakeIdle;
    }

    public boolean isFakeIdle() {
        return this.fakeIdle;
    }

    public boolean wasFallFlying() {
        return this.wasFallFlying;
    }

    public void updateFallFlyingState(boolean fallFlying) {
        this.wasFallFlying = fallFlying;
        this.fallFlyingChangedAt = TimeUtil.getMillis();
    }

    public long getFallFlyingChangedAt() {
        return this.fallFlyingChangedAt;
    }
}
