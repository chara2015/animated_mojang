package net.labymod.core.client.os.windows.util;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/windows/util/WindowsVersion.class */
public final class WindowsVersion extends Record {
    private final int majorVersion;
    private final int minorVersion;
    private final int buildNumber;
    private final boolean debuggingBuild;

    public WindowsVersion(int majorVersion, int minorVersion, int buildNumber, boolean debuggingBuild) {
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.buildNumber = buildNumber;
        this.debuggingBuild = debuggingBuild;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WindowsVersion.class), WindowsVersion.class, "majorVersion;minorVersion;buildNumber;debuggingBuild", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->majorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->minorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->buildNumber:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->debuggingBuild:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WindowsVersion.class), WindowsVersion.class, "majorVersion;minorVersion;buildNumber;debuggingBuild", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->majorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->minorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->buildNumber:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->debuggingBuild:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WindowsVersion.class, Object.class), WindowsVersion.class, "majorVersion;minorVersion;buildNumber;debuggingBuild", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->majorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->minorVersion:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->buildNumber:I", "FIELD:Lnet/labymod/core/client/os/windows/util/WindowsVersion;->debuggingBuild:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public int majorVersion() {
        return this.majorVersion;
    }

    public int minorVersion() {
        return this.minorVersion;
    }

    public int buildNumber() {
        return this.buildNumber;
    }

    public boolean debuggingBuild() {
        return this.debuggingBuild;
    }
}
