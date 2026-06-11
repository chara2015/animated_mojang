package net.labymod.v1_21_11.platform;

import net.labymod.api.client.Minecraft;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.core.platform.Platform;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/platform/VersionedPlatform.class */
@AutoService(value = Platform.class, versionSpecific = true)
public class VersionedPlatform extends Platform {
    public void onInitialization() {
        setMinecraft((Minecraft) net.minecraft.client.Minecraft.getInstance());
        setPlatformScreenHandler(new VersionedPlatformScreenHandler());
        this.labyMod.eventBus().registerListener(new LinkerSetupHandler());
    }

    public void onPostStartup() {
    }
}
