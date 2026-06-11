package net.labymod.core.main.updater.manifest;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/manifest/UpdaterManifest.class */
public final class UpdaterManifest {
    private String latest;
    private final List<String> versions = new ArrayList();

    public String getLatest() {
        return this.latest;
    }

    public List<String> getVersions() {
        return this.versions;
    }
}
