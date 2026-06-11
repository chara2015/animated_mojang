package net.labymod.core.thirdparty.optifine;

import net.labymod.api.thirdparty.optifine.OptiFineConfig;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/optifine/NOPOptiFineConfig.class */
public class NOPOptiFineConfig implements OptiFineConfig {
    @Override // net.labymod.api.thirdparty.optifine.OptiFineConfig
    public boolean hasShaders() {
        return false;
    }

    @Override // net.labymod.api.thirdparty.optifine.OptiFineConfig
    public int getActiveProgramId() {
        return -1;
    }
}
