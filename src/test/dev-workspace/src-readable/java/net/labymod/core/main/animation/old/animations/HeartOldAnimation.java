package net.labymod.core.main.animation.old.animations;

import net.labymod.api.Laby;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.AbstractOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/animations/HeartOldAnimation.class */
public class HeartOldAnimation extends AbstractOldAnimation {
    public static final String NAME = "heart";
    private static final int FULL_FLASH = 70;
    private static final int HALF_FLASH = 79;
    private static final int FLASHING_BACKGROUND = 25;
    private final PermissionRegistry permissionRegistry;

    public HeartOldAnimation() {
        super(NAME);
        this.permissionRegistry = Laby.references().permissionRegistry();
    }

    public int getU(int u) {
        if (!isEnabled()) {
            return u;
        }
        if (u == FULL_FLASH || u == HALF_FLASH) {
            return 25;
        }
        return u;
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public boolean isEnabled() {
        return this.permissionRegistry.isPermissionEnabled("animations", this.classicPvPConfig.oldHeart());
    }
}
