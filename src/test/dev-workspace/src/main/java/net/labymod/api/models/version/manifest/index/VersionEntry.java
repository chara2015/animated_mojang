package net.labymod.api.models.version.manifest.index;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/manifest/index/VersionEntry.class */
public class VersionEntry {
    private String id;
    private String type;
    private String url;
    private String time;
    private String releaseTime;

    public VersionEntry(String id, String type, String url, String time, String releaseTime) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.time = time;
        this.releaseTime = releaseTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReleaseTime() {
        return this.releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
}
