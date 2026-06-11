package net.labymod.api.loader.platform;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/platform/PlatformEnvironment.class */
public final class PlatformEnvironment {
    public static final boolean NO_VULKAN_BACKEND = true;
    private static PlatformClassloader platformClassloader;
    private static Version version;
    private static String runningVersion;
    private static Path clientJarPath;
    private static Path obfuscatedJarPath;
    private static boolean ancientPvPVersion;
    private static boolean ancientOpenGL;
    private static boolean noMojangMappings;
    private static boolean noShader;
    private static boolean newLogo;
    public static final boolean ITEM_COMPONENTS = MinecraftVersions.V24w09a.orNewer();
    public static final boolean Z_ZERO_TO_ONE = MinecraftVersions.V26_2_snapshot_6.orNewer();
    private static final Version ANCIENT_PVP_VERSION = VersionDeserializer.from("1.8.9");
    private static final Version ANCIENT_OPENGL = VersionDeserializer.from("1.12.2");
    private static final Version NO_MOJANG_MAPPINGS = VersionDeserializer.from("1.13.2");
    private static final Version NO_SHADER = VersionDeserializer.from("1.16.5");
    private static final Version NEW_LOGO = VersionDeserializer.from("23w14a");
    private static final Version NEW_LOGO_1_20 = VersionDeserializer.from("1.20-pre3");
    private static final boolean FRESH_UI = MinecraftVersions.V24w09a.orNewer();

    public static PlatformClassloader getPlatformClassloader() {
        return platformClassloader;
    }

    public static void setPlatformClassloader(PlatformClassloader platformClassloader2) {
        platformClassloader = platformClassloader2;
    }

    public static Platform getCurrentPlatform() {
        return platformClassloader.getPlatform();
    }

    @Deprecated(forRemoval = true, since = "4.3.51")
    public static String getRunningVersion() {
        return runningVersion;
    }

    public static void setRunningVersion(String runningVersion2) {
        runningVersion = runningVersion2;
        version = VersionDeserializer.from(runningVersion2);
        ancientPvPVersion = isLowerThan(ANCIENT_PVP_VERSION);
        ancientOpenGL = isLowerThan(ANCIENT_OPENGL);
        noMojangMappings = isLowerThan(NO_MOJANG_MAPPINGS);
        noShader = isLowerThan(NO_SHADER);
        newLogo = isGreaterThan(NEW_LOGO) || isGreaterThan(NEW_LOGO_1_20);
    }

    public static Path getClientJarPath() {
        return clientJarPath;
    }

    public static void setClientJarPath(Path clientJarPath2) {
        clientJarPath = clientJarPath2;
    }

    public static Path getObfuscatedJarPath() {
        return obfuscatedJarPath;
    }

    public static void setObfuscatedJarPath(Path obfuscatedJarPath2) {
        obfuscatedJarPath = obfuscatedJarPath2;
    }

    public static boolean isAncientPvPVersion() {
        return ancientPvPVersion;
    }

    public static boolean isAncientOpenGL() {
        return ancientOpenGL;
    }

    public static boolean isNoMojangMappings() {
        return noMojangMappings;
    }

    public static boolean isNoShader() {
        return noShader;
    }

    public static boolean isFreshUI() {
        return FRESH_UI;
    }

    public static Enumeration<URL> getResources(ClassLoader loader, String name) throws IOException {
        PlatformClassloader classloader = getPlatformClassloader();
        if (classloader == null) {
            return Collections.emptyEnumeration();
        }
        return classloader.getResources(loader, name);
    }

    @Deprecated
    public static boolean isNewLogo() {
        return newLogo;
    }

    private static boolean isLowerThan(Version version2) {
        Version platformVersion = version;
        return platformVersion.isLowerThan(version2) || platformVersion.equals(version2);
    }

    private static boolean isGreaterThan(Version version2) {
        Version platformVersion = version;
        return platformVersion.isGreaterThan(version2) || platformVersion.equals(version2);
    }
}
