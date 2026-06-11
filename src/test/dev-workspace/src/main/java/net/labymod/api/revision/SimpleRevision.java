package net.labymod.api.revision;

import java.text.SimpleDateFormat;
import net.labymod.api.util.version.SemanticVersion;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/revision/SimpleRevision.class */
public class SimpleRevision implements Revision {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final String namespace;
    private final SemanticVersion version;
    private long releaseDate;

    public SimpleRevision(String namespace, SemanticVersion version, String releaseDate) {
        this.releaseDate = 0L;
        this.namespace = namespace;
        this.version = version;
        try {
            this.releaseDate = DATE_FORMAT.parse(releaseDate).getTime();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.revision.Revision
    public String getNamespace() {
        return this.namespace;
    }

    @Override // net.labymod.api.revision.Revision
    public SemanticVersion version() {
        return this.version;
    }

    @Override // net.labymod.api.revision.Revision
    public long getReleaseDate() {
        return this.releaseDate;
    }
}
