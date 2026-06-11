package net.labymod.api.models.version.manifest.index;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/manifest/index/LatestVersion.class */
public class LatestVersion {
    private String release;
    private String snapshot;

    public LatestVersion(String release, String snapshot) {
        this.release = release;
        this.snapshot = snapshot;
    }

    public String getRelease() {
        return this.release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getSnapshot() {
        return this.snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }
}
