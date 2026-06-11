package net.labymod.api.models.version.manifest;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/manifest/ManifestDownload.class */
public class ManifestDownload {
    private String sha1;
    private int size;
    private String url;

    public ManifestDownload(String sha1, int size, String url) {
        this.sha1 = sha1;
        this.size = size;
        this.url = url;
    }

    public String getSha1() {
        return this.sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
