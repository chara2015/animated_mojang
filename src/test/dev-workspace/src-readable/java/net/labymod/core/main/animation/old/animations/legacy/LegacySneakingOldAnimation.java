package net.labymod.core.main.animation.old.animations.legacy;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/legacy/LegacySneakingOldAnimation.class */
public class LegacySneakingOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "legacy_sneaking";
    public static final boolean IS_LEGACY_VERSION = MinecraftVersions.V1_12_2.orOlder();
    public static final float LEGACY_CROUCH_OFFSET = 0.2f;
    public static final float LEGACY_CROUCH_SPEED = 0.4f;
    private final IntaveProtocol intaveProtocol;
    private float legacyOffset;
    private float prevLegacyOffset;

    public LegacySneakingOldAnimation() {
        super(NAME);
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }

    public static float getLegacyCrouchOffset(Entity entity) {
        return entity.isCrouching() ? 0.080000006f : 0.0f;
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        Entity cameraEntity;
        if (!isEnabled() || event.phase() != Phase.PRE || (cameraEntity = Laby.labyAPI().minecraft().getCameraEntity()) == null || Laby.labyAPI().minecraft().isPaused()) {
            return;
        }
        this.prevLegacyOffset = this.legacyOffset;
        if (cameraEntity.isCrouching() && this.legacyOffset < 0.2f) {
            this.legacyOffset = 0.2f;
        }
        this.legacyOffset *= 0.4f;
    }

    @Subscribe
    public void onCameraEyeHeight(CameraEyeHeightEvent event) {
        Entity cameraEntity;
        if (!isEnabled() || (cameraEntity = Laby.labyAPI().minecraft().getCameraEntity()) == null) {
            return;
        }
        float vanillaCrouchOffset = getLegacyCrouchOffset(cameraEntity);
        float legacyCrouchOffset = MathHelper.lerp(this.legacyOffset, this.prevLegacyOffset, event.getPartialTicks());
        event.setEyeHeight((event.getEyeHeight() + vanillaCrouchOffset) - legacyCrouchOffset);
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(IS_LEGACY_VERSION && this.permissionRegistry.isPermissionEnabled("sneaking_visual", this.classicPvPConfig.oldSneaking()));
    }
}
