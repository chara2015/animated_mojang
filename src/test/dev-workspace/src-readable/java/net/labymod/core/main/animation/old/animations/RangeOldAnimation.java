package net.labymod.core.main.animation.old.animations;

import net.labymod.core.main.LabyMod;
import net.labymod.core.main.animation.old.AbstractOldAnimation;
import net.labymod.core.main.serverapi.protocol.intave.IntaveProtocol;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/RangeOldAnimation.class */
public class RangeOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "range";
    private static final float OLD_PICK_RADIUS = 0.1f;
    private final IntaveProtocol intaveProtocol;

    public RangeOldAnimation() {
        super(NAME);
        this.intaveProtocol = LabyMod.references().intaveProtocol();
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.intaveProtocol.hasPermission(this.permissionRegistry.isPermissionEnabled(NAME, this.classicPvPConfig.oldRange()));
    }

    public float getOldPickRadius() {
        if (isEnabled()) {
            return OLD_PICK_RADIUS;
        }
        return 0.0f;
    }
}
