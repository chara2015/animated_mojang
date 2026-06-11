package net.labymod.core.main.animation.old.animations;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/SlowdownOldAnimation.class */
public class SlowdownOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "slowdown";
    private final IntaveProtocol intaveProtocol;

    public SlowdownOldAnimation() {
        super(NAME);
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled(NAME, this.classicPvPConfig.oldSlowdown()));
    }

    public boolean isEnabled(float forwardImpulse) {
        return MinecraftVersions.V25w02a.orNewer() ? forwardImpulse > 0.7f : forwardImpulse > 0.8f;
    }
}
