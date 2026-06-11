package net.labymod.api.platform.launcher;

import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/platform/launcher/MinecraftLauncher.class */
public interface MinecraftLauncher {
    File getDirectory();

    LauncherVendorType currentType();

    boolean kill() throws IOException;
}
