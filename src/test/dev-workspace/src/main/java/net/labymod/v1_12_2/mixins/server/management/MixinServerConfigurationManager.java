package net.labymod.v1_12_2.mixins.server.management;

import net.labymod.v1_12_2.server.VersionedServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/server/management/MixinServerConfigurationManager.class */
@Mixin({pl.class})
public class MixinServerConfigurationManager implements VersionedServerConfigurationManager {

    @Shadow
    private boolean u;

    @Override // net.labymod.v1_12_2.server.VersionedServerConfigurationManager
    public boolean isCommandsAllowedForAll() {
        return this.u;
    }
}
