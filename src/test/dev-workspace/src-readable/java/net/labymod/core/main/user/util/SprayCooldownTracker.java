package net.labymod.core.main.user.util;

import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.Group;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.spray.SprayConstants;
import net.labymod.core.main.user.shop.spray.SprayObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/util/SprayCooldownTracker.class */
public class SprayCooldownTracker {
    private static final long NETWORK_DELAY = 50;
    private final GameUser user;
    private long lastServerTimeSprayed;
    private long lastClientTimeSprayed;
    private long lastTimeSoundPlayed;

    public SprayCooldownTracker(GameUser user) {
        this.user = user;
    }

    public void sprayClient() {
        this.lastClientTimeSprayed = TimeUtil.getCurrentTimeMillis();
        this.lastClientTimeSprayed += getNextSprayTime();
    }

    public boolean canSprayClient() {
        return this.lastClientTimeSprayed <= TimeUtil.getCurrentTimeMillis();
    }

    public long getClientDuration() {
        return this.lastClientTimeSprayed - TimeUtil.getCurrentTimeMillis();
    }

    public void sprayServer() {
        this.lastServerTimeSprayed = TimeUtil.getCurrentTimeMillis();
    }

    public void playSound(SprayObject object, boolean force) {
        if (!canPlaySound() && !force) {
            return;
        }
        this.lastTimeSoundPlayed = TimeUtil.getCurrentTimeMillis();
        Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_SPRAY_CAN_SPRAY, 1.0f, 1.0f, object.position());
    }

    public boolean canSprayServer() {
        long nextSpray = getNextSprayTime();
        long remainingCooldown = this.lastServerTimeSprayed + (nextSpray - NETWORK_DELAY);
        return remainingCooldown <= TimeUtil.getCurrentTimeMillis();
    }

    private long getNextSprayTime() {
        Group group = this.user.visibleGroup();
        if (!group.isDefault()) {
            return SprayConstants.LABYMOD_PLUS_NEXT_SPRAY;
        }
        return 60000L;
    }

    public boolean canPlaySound() {
        return this.lastTimeSoundPlayed + SprayConstants.SPRAY_SOUND_INTERVAL <= TimeUtil.getCurrentTimeMillis();
    }
}
