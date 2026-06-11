package net.labymod.v26_1_1.platform;

import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.platform.Platform;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/platform/VersionedPlatform.class */
@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform {
    @Override // net.labymod.core.platform.Platform
    public void onInitialization() {
        setMinecraft((Minecraft) net.minecraft.client.Minecraft.getInstance());
        setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.labyMod.eventBus().registerListener(new LinkerSetupHandler());
    }

    @Override // net.labymod.core.platform.Platform
    public void onPostStartup() {
    }
}
