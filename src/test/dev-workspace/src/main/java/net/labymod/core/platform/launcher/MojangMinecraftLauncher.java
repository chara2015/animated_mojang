package net.labymod.core.platform.launcher;

import java.io.File;
import java.io.IOException;
import net.labymod.api.Namespaces;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.api.platform.launcher.MinecraftLauncher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/MojangMinecraftLauncher.class */
public class MojangMinecraftLauncher implements MinecraftLauncher {
    @Override // net.labymod.api.platform.launcher.MinecraftLauncher
    public File getDirectory() {
        return getWorkingDirectory(Namespaces.MINECRAFT);
    }

    @Override // net.labymod.api.platform.launcher.MinecraftLauncher
    public LauncherVendorType currentType() {
        return LauncherVendorType.MOJANG;
    }

    @Override // net.labymod.api.platform.launcher.MinecraftLauncher
    public boolean kill() throws IOException {
        OperatingSystem currentPlatform = OperatingSystem.getPlatform();
        switch (currentPlatform) {
            case LINUX:
            case SOLARIS:
            case UNKNOWN:
                return false;
            case WINDOWS:
                killOnWindows();
                return true;
            case MACOS:
                killOnMacOS();
                return true;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(currentPlatform));
        }
    }

    private File getWorkingDirectory(String applicationName) {
        File workingDirectory;
        String userHome = System.getProperty("user.home", ".");
        switch (OperatingSystem.getPlatform()) {
            case LINUX:
                workingDirectory = new File(userHome, "." + applicationName + "/");
                break;
            case SOLARIS:
                String applicationDataW = System.getenv("APPDATA");
                if (applicationDataW != null) {
                    workingDirectory = new File(applicationDataW, "." + applicationName + "/");
                } else {
                    workingDirectory = new File(userHome, "." + applicationName + "/");
                }
                break;
            case UNKNOWN:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            case WINDOWS:
                String applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + "/");
                } else {
                    workingDirectory = new File(userHome, "." + applicationName + "/");
                }
                break;
            case MACOS:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, applicationName + "/");
                break;
        }
        if (!workingDirectory.exists() && !workingDirectory.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + String.valueOf(workingDirectory));
        }
        return workingDirectory;
    }

    private void killOnWindows() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        runtime.exec("taskkill /IM Minecraft.exe");
        runtime.exec("taskkill /IM MinecraftLauncher.exe");
    }

    private void killOnMacOS() throws IOException {
        Runtime.getRuntime().exec(new String[]{"osascript", "-e", "quit app \"Minecraft Launcher\""});
    }
}
