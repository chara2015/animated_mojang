package net.labymod.core.platform.launcher;

import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.api.platform.launcher.MinecraftLauncher;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/DefaultMinecraftLauncherFactory.class */
@Singleton
@Implements(MinecraftLauncherFactory.class)
public class DefaultMinecraftLauncherFactory implements MinecraftLauncherFactory {
    @Override // net.labymod.api.platform.launcher.MinecraftLauncherFactory
    public MinecraftLauncher create(LauncherVendorType type) {
        switch (type) {
            case MOJANG:
                return new MojangMinecraftLauncher();
            case MULTIMC:
                return null;
            default:
                return null;
        }
    }
}
