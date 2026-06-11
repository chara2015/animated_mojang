package net.labymod.core.main.animation.old;

import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/animation/old/AbstractOldAnimation.class */
public abstract class AbstractOldAnimation implements OldAnimation {
    protected static final float UNIT = 0.0625f;
    protected final PermissionRegistry permissionRegistry = Laby.references().permissionRegistry();
    protected final OldAnimationRegistry animationRegistry = LabyMod.getInstance().getOldAnimationRegistry();
    protected final ClassicPvPConfig classicPvPConfig = Laby.labyAPI().config().multiplayer().classicPvP();
    private final String name;

    public AbstractOldAnimation(String name) {
        this.name = name;
    }

    @Override // net.labymod.core.main.animation.old.OldAnimation
    public String getName() {
        return this.name;
    }

    protected <T extends OldAnimation> T getAnimation(String str) {
        return (T) this.animationRegistry.get(str);
    }
}
