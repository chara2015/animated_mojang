package net.labymod.core.main.user.shop.spray;

import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/SprayObject.class */
public class SprayObject {
    private final GameUser owner;
    private final short id;
    private final DoubleVector3 position;
    private final Direction direction;
    private final float rotation;
    private final long creationTime;
    private long adjustedCreationTime;
    private boolean adjustCreationTime;
    private float previousDuration;

    public SprayObject(GameUser owner, short id, double x, double y, double z, Direction direction, float rotation) {
        this(owner, id, new DoubleVector3(x, y, z), direction, rotation);
    }

    public SprayObject(GameUser owner, short id, DoubleVector3 position, Direction direction, float rotation) {
        this.owner = owner;
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.rotation = rotation;
        this.creationTime = TimeUtil.getMillis();
    }

    public GameUser getOwner() {
        return this.owner;
    }

    public short getId() {
        return this.id;
    }

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    public double getZ() {
        return this.position.getZ();
    }

    public DoubleVector3 position() {
        return this.position;
    }

    public Direction direction() {
        return this.direction;
    }

    public float getRotation() {
        return this.rotation;
    }

    public long getCreationTime() {
        if (this.adjustCreationTime) {
            long duration = getDuration();
            this.adjustedCreationTime = this.creationTime - ((60000 - duration) - getLastLifespan());
            this.adjustCreationTime = false;
        }
        return this.adjustedCreationTime == 0 ? this.creationTime : this.adjustedCreationTime;
    }

    public long getAdjustedCreationTime() {
        return this.adjustedCreationTime;
    }

    private long getLastLifespan() {
        Group group = this.owner.visibleGroup();
        if (group.isLabyModPlus() || group.isStaff()) {
            return SprayConstants.LABYMOD_PLUS_LAST_LIFESPAN;
        }
        return 0L;
    }

    private long getPlainCreationTime() {
        return this.creationTime;
    }

    public void setAdjustCreationTime(boolean adjustCreationTime) {
        this.adjustCreationTime = adjustCreationTime;
    }

    public boolean isExpired() {
        return getCreationTime() + 60000 < TimeUtil.getMillis();
    }

    public boolean isFadeIn() {
        return getCreationTime() > TimeUtil.getMillis() - 1000;
    }

    public boolean isNormal() {
        return getCreationTime() + 1000 < TimeUtil.getMillis() && TimeUtil.getMillis() < (getCreationTime() + 60000) - 1000;
    }

    public boolean isFadeOut() {
        return getCreationTime() < TimeUtil.getMillis() - 1000;
    }

    public long getDuration() {
        long time = getPlainCreationTime() + 60000;
        long lifespan = TimeUtil.getMillis() + 60000;
        return lifespan - time;
    }

    public float getPreviousDuration() {
        return this.previousDuration;
    }

    public void setPreviousDuration(float previousDuration) {
        this.previousDuration = previousDuration;
    }
}
