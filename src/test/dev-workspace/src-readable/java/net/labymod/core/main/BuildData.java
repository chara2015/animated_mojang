package net.labymod.core.main;

import net.labymod.api.models.version.Version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/BuildData.class */
@Deprecated(forRemoval = true, since = "4.2.55")
public class BuildData {
    public static String commitReference() {
        return net.labymod.api.BuildData.commitReference();
    }

    public static String branchName() {
        return net.labymod.api.BuildData.branchName();
    }

    public static String releaseType() {
        return net.labymod.api.BuildData.releaseType();
    }

    public static String getVersion() {
        return net.labymod.api.BuildData.getVersion();
    }

    public static Version version() {
        return net.labymod.api.BuildData.version();
    }

    public static String getUserAgent() {
        return net.labymod.api.BuildData.getUserAgent();
    }

    public static int getBuildNumber() {
        return net.labymod.api.BuildData.getBuildNumber();
    }

    public static boolean hasRealms() {
        return net.labymod.api.BuildData.hasRealms();
    }
}
