package net.labymod.v1_8_9.mixins.server.management;

import net.labymod.v1_8_9.server.VersionedServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/server/management/MixinServerConfigurationManager.class */
@Mixin({lx.class})
public class MixinServerConfigurationManager implements VersionedServerConfigurationManager {

    @Shadow
    private boolean t;

    @Override // net.labymod.v1_8_9.server.VersionedServerConfigurationManager
    public boolean isCommandsAllowedForAll() {
        return this.t;
    }
}
