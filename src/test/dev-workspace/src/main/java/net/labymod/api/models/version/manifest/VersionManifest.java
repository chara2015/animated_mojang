package net.labymod.api.models.version.manifest;

import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/manifest/VersionManifest.class */
public class VersionManifest {
    public static final String MAPPINGS_DOWNLOAD_KEY = "client_mappings";
    private String id;
    private Map<String, ManifestDownload> downloads;

    public VersionManifest(String id, Map<String, ManifestDownload> downloads) {
        this.id = id;
        this.downloads = downloads;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, ManifestDownload> getDownloads() {
        return this.downloads;
    }

    public void setDownloads(Map<String, ManifestDownload> downloads) {
        this.downloads = downloads;
    }
}
