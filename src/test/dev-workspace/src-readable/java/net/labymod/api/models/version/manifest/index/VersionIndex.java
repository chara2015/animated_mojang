package net.labymod.api.models.version.manifest.index;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/manifest/index/VersionIndex.class */
public class VersionIndex {
    private LatestVersion latest;
    private VersionEntry[] versions;

    public VersionIndex(LatestVersion latest, VersionEntry[] versions) {
        this.latest = latest;
        this.versions = versions;
    }

    public LatestVersion getLatest() {
        return this.latest;
    }

    public void setLatest(LatestVersion latest) {
        this.latest = latest;
    }

    public VersionEntry[] getVersions() {
        return this.versions;
    }

    public void setVersions(VersionEntry[] versions) {
        this.versions = versions;
    }
}
