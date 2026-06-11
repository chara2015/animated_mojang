package net.labymod.api.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.models.version.Version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/LabyModLoader.class */
public interface LabyModLoader {
    void gameOptions(List<String> list, Path path, Path path2, String str, boolean z, boolean z2, String str2);

    void loadAddons();

    void loadIsolatedLibraries();

    InputStream getMixinServiceResourceAsStream(String str, InputStream inputStream);

    byte[] getMixinServiceClassBytes(String str, String str2, byte[] bArr) throws IOException;

    void registerMixinResourceSource(String str, URL url);

    Version version();

    List<String> getArguments();

    Path getGameDirectory();

    Path getAssetsDirectory();

    String getProfile();

    String getEffectiveReleaseChannel();

    boolean isLabyModDevelopmentEnvironment();

    boolean isAddonDevelopmentEnvironment();

    default boolean isDevelopmentEnvironment() {
        return isLabyModDevelopmentEnvironment() || isAddonDevelopmentEnvironment();
    }

    default boolean isExplicitLabyModDevelopmentEnvironment() {
        return isLabyModDevelopmentEnvironment() && !isAddonDevelopmentEnvironment();
    }

    default boolean isDevelopmentEnvironment(String namespace) {
        boolean isLabyModNamespace = namespace.equals("labymod");
        LabyModLoader loader = Laby.labyAPI().labyModLoader();
        if (isLabyModNamespace) {
            return loader.isLabyModDevelopmentEnvironment();
        }
        return loader.isAddonDevelopmentEnvironment();
    }
}
