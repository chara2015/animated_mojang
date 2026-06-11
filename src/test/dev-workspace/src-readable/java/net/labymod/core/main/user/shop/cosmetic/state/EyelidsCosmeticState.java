package net.labymod.core.main.user.shop.cosmetic.state;

import java.util.Random;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/EyelidsCosmeticState.class */
public class EyelidsCosmeticState extends CosmeticState {
    private final AnimationData animationData;

    public EyelidsCosmeticState(AnimationStorage animationStorage) {
        super(animationStorage);
        this.animationData = new AnimationData();
    }

    public EyelidsCosmeticState(EyelidsCosmeticState other) {
        super(other);
        this.animationData = AnimationData.copyOf(other.animationData);
    }

    public AnimationData animationData() {
        return this.animationData;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/EyelidsCosmeticState$AnimationData.class */
    public static class AnimationData {
        private static final Random RANDOM = new Random();
        private float lastRenderedPercentage;
        private long nextBlinkTimestamp;
        private long blinkPauseDuration;
        private double lastPositionHash;
        private long lastPositionChanged;

        public AnimationData() {
            this.nextBlinkTimestamp = TimeUtil.getMillis();
        }

        private AnimationData(AnimationData other) {
            this.nextBlinkTimestamp = TimeUtil.getMillis();
            this.lastRenderedPercentage = other.lastRenderedPercentage;
            this.nextBlinkTimestamp = other.nextBlinkTimestamp;
            this.blinkPauseDuration = other.blinkPauseDuration;
            this.lastPositionHash = other.lastPositionHash;
            this.lastPositionChanged = other.lastPositionChanged;
        }

        public static AnimationData copyOf(AnimationData other) {
            return new AnimationData(other);
        }

        public long getBlinkTimeLeft() {
            long currentTime = TimeUtil.getMillis();
            long nextBlinkTime = this.nextBlinkTimestamp - currentTime;
            if (nextBlinkTime < 0) {
                this.blinkPauseDuration = 800 + (RANDOM.nextInt(8) * SubmissionOrders.DEBUG);
                this.nextBlinkTimestamp = currentTime + this.blinkPauseDuration;
            }
            return nextBlinkTime;
        }

        public long getIdleDuration(Entity entity) {
            Position position = entity.position();
            double hash = position.getX() + position.getY() + position.getZ() + ((double) entity.getRotationYaw()) + ((double) entity.getRotationPitch()) + ((double) (entity.isCrouching() ? 1 : 0));
            if (entity instanceof Player) {
                Player player = (Player) entity;
                hash += (double) player.getLimbSwing();
            }
            if (this.lastPositionHash != hash) {
                this.lastPositionHash = hash;
                this.lastPositionChanged = TimeUtil.getMillis();
            }
            if (entity instanceof DummyPlayer) {
                this.lastPositionChanged = TimeUtil.getMillis();
            }
            return TimeUtil.getMillis() - this.lastPositionChanged;
        }

        public float getLastRenderedPercentage() {
            return this.lastRenderedPercentage;
        }

        public void setLastRenderedPercentage(float lastRenderedPercentage) {
            this.lastRenderedPercentage = lastRenderedPercentage;
        }

        public long getBlinkPauseDuration() {
            return this.blinkPauseDuration;
        }
    }
}
