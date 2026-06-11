package net.labymod.core.main.serverapi.protocol.intave.packet;

import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.user.permission.PermissionRegistry;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;
import net.labymod.core.main.animation.old.animations.SlowdownOldAnimation;
import net.labymod.serverapi.api.packet.Packet;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/intave/packet/IntaveClientConfigPacket.class */
public class IntaveClientConfigPacket implements Packet {
    private boolean legacySneakHeight;
    private boolean legacyOldRange;
    private boolean legacyOldSlowdown;

    public void readConfig() {
        LabyConfig config = Laby.labyAPI().config();
        PermissionRegistry permissionRegistry = Laby.references().permissionRegistry();
        ClassicPvPConfig classicPvP = config.multiplayer().classicPvP();
        boolean oldSneaking = permissionRegistry.isPermissionEnabled("animations", classicPvP.oldSword());
        boolean oldSlowdown = permissionRegistry.isPermissionEnabled(SlowdownOldAnimation.NAME, classicPvP.oldSlowdown());
        boolean oldRange = permissionRegistry.isPermissionEnabled(RangeOldAnimation.NAME, classicPvP.oldRange());
        setLegacySneakHeight(oldSneaking);
        setLegacyOldSlowdown(oldSlowdown);
        setLegacyOldRange(oldRange);
    }

    public boolean isLegacySneakHeight() {
        return this.legacySneakHeight;
    }

    public void setLegacySneakHeight(boolean legacySneakHeight) {
        this.legacySneakHeight = legacySneakHeight;
    }

    public boolean isLegacyOldRange() {
        return this.legacyOldRange;
    }

    public void setLegacyOldRange(boolean legacyOldRange) {
        this.legacyOldRange = legacyOldRange;
    }

    public boolean isLegacyOldSlowdown() {
        return this.legacyOldSlowdown;
    }

    public void setLegacyOldSlowdown(boolean legacyOldSlowdown) {
        this.legacyOldSlowdown = legacyOldSlowdown;
    }
}
