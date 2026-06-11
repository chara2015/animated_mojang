package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.core.event.client.render.entity.EntityEyeHeightEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.animation.old.animations.legacy.LegacySneakingOldAnimation;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/SneakingOldAnimation.class */
public class SneakingOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "sneaking";
    private static final float MODERN_CROUCH_OFFSET = 0.35f;
    private static final float MODERN_CROUCH_SPEED = 0.5f;
    private final IntaveProtocol intaveProtocol;
    private float legacyOffset;
    private float prevLegacyOffset;
    private float modernOffset;
    private float prevModernOffset;

    public SneakingOldAnimation() {
        super(NAME);
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }

    public static float getModernCrouchOffset(Entity entity) {
        if (entity.isCrouchingPose()) {
            return MODERN_CROUCH_OFFSET;
        }
        return 0.0f;
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        Entity cameraEntity;
        float modernCrouchOffset;
        if (!isEnabled() || event.phase() != Phase.PRE || (cameraEntity = Laby.labyAPI().minecraft().getCameraEntity()) == null || Laby.labyAPI().minecraft().isPaused()) {
            return;
        }
        boolean stuckUnderBlock = !cameraEntity.canEnterEntityPose(EntityPose.STANDING);
        boolean isSwimming = cameraEntity.entityPose() == EntityPose.SWIMMING;
        this.prevLegacyOffset = this.legacyOffset;
        if (stuckUnderBlock && !isSwimming) {
            this.legacyOffset = MODERN_CROUCH_OFFSET;
        } else {
            if (cameraEntity.isCrouching() && this.legacyOffset < 0.2f) {
                this.legacyOffset = 0.2f;
            }
            this.legacyOffset *= 0.4f;
        }
        this.prevModernOffset = this.modernOffset;
        if (isActualPermissionEnabled() && !stuckUnderBlock) {
            modernCrouchOffset = LegacySneakingOldAnimation.getLegacyCrouchOffset(cameraEntity);
        } else {
            modernCrouchOffset = getModernCrouchOffset(cameraEntity);
        }
        float crouchOffset = modernCrouchOffset;
        this.modernOffset += (crouchOffset - this.modernOffset) * 0.5f;
    }

    @Subscribe
    public void onCameraEyeHeight(CameraEyeHeightEvent event) {
        if (!isEnabled()) {
            return;
        }
        Entity cameraEntity = Laby.labyAPI().minecraft().getCameraEntity();
        if (cameraEntity == null) {
            return;
        }
        float partialTicks = event.getPartialTicks();
        float vanillaCrouchOffset = MathHelper.lerp(this.modernOffset, this.prevModernOffset, partialTicks);
        float legacyCrouchOffset = MathHelper.lerp(this.legacyOffset, this.prevLegacyOffset, partialTicks);
        event.setEyeHeight((event.getEyeHeight() + vanillaCrouchOffset) - legacyCrouchOffset);
    }

    @Subscribe
    public void onEntityEyeHeight(EntityEyeHeightEvent event) {
        Entity cameraEntity;
        if (!isEnabled() || !isActualPermissionEnabled() || (cameraEntity = Laby.labyAPI().minecraft().getCameraEntity()) == null || event.entity() != cameraEntity) {
            return;
        }
        boolean stuckUnderBlock = !cameraEntity.canEnterEntityPose(EntityPose.STANDING);
        if (stuckUnderBlock) {
            return;
        }
        float vanillaCrouchOffset = getModernCrouchOffset(cameraEntity);
        float legacyCrouchOffset = LegacySneakingOldAnimation.getLegacyCrouchOffset(cameraEntity);
        event.setEyeHeight((event.getEyeHeight() + vanillaCrouchOffset) - legacyCrouchOffset);
    }

    public boolean isActualPermissionEnabled() {
        return this.permissionRegistry.isPermissionEnabled(NAME);
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(!LegacySneakingOldAnimation.IS_LEGACY_VERSION && this.permissionRegistry.isPermissionEnabled(NAME, this.classicPvPConfig.oldSneaking()));
    }
}
