package net.labymod.api.platform.launcher;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/platform/launcher/MinecraftLauncherFactory.class */
@Referenceable
public interface MinecraftLauncherFactory {
    @Nullable
    MinecraftLauncher create(LauncherVendorType launcherVendorType);
}
