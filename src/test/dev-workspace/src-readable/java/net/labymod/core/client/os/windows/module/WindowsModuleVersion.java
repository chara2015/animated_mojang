package net.labymod.core.client.os.windows.module;

import com.sun.jna.platform.win32.VerRsrc;
import net.labymod.core.client.os.module.ModuleVersion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/module/WindowsModuleVersion.class */
public final class WindowsModuleVersion implements ModuleVersion {
    private final int major;
    private final int minor;
    private final int revision;
    private final int build;

    private WindowsModuleVersion(int major, int minor, int revision, int build) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
        this.build = build;
    }

    public static ModuleVersion fileVersion(VerRsrc.VS_FIXEDFILEINFO info) {
        return new WindowsModuleVersion(info.getFileVersionMajor(), info.getFileVersionMinor(), info.getFileVersionRevision(), info.getFileVersionBuild());
    }

    public static ModuleVersion productVersion(VerRsrc.VS_FIXEDFILEINFO info) {
        return new WindowsModuleVersion(info.getProductVersionMajor(), info.getProductVersionMinor(), info.getProductVersionRevision(), info.getProductVersionBuild());
    }

    @Override // net.labymod.core.client.os.module.ModuleVersion
    public int getMajor() {
        return this.major;
    }

    @Override // net.labymod.core.client.os.module.ModuleVersion
    public int getMinor() {
        return this.minor;
    }

    @Override // net.labymod.core.client.os.module.ModuleVersion
    public int getRevision() {
        return this.revision;
    }

    @Override // net.labymod.core.client.os.module.ModuleVersion
    public int getBuild() {
        return this.build;
    }

    public String toString() {
        return getMajor() + "." + getMinor() + "." + getRevision() + "." + getBuild();
    }
}
