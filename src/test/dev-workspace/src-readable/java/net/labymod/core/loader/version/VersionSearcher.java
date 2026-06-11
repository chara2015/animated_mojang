package net.labymod.core.loader.version;

import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.loader.platform.PlatformClassloader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/version/VersionSearcher.class */
public interface VersionSearcher {
    void searchClasspath(PlatformClassloader platformClassloader);

    String getMinecraftVersion();

    Path getClientJarPath();

    Collection<String> getMainClasses();
}
