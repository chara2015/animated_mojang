package net.labymod.core.main.updater.manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/manifest/LabyModManifest.class */
public final class LabyModManifest {
    private Version latest;
    private final List<Version> versions = new ArrayList();

    public Version getLatest() {
        return this.latest;
    }

    public List<Version> getVersions() {
        return this.versions;
    }

    @Nullable
    public String getMinorVersion(Version labyModVersion, String commitReference) {
        for (Version version : this.versions) {
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/manifest/LabyModManifest$Version.class */
    public static final class Version {
        private String labyModVersion;
        private String commitReference;
        private Map<String, String> assets;
        private Metadata[] minecraftVersions;

        public String getLabyModVersion() {
            return this.labyModVersion;
        }

        public String getCommitReference() {
            return this.commitReference;
        }

        public Metadata[] getMinecraftVersions() {
            return this.minecraftVersions;
        }

        public Map<String, String> getAssets() {
            if (this.assets == null) {
                this.assets = new HashMap();
            }
            return this.assets;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/manifest/LabyModManifest$Metadata.class */
    public static final class Metadata {
        private String tag;
        private String version;

        public Metadata(String tag, String version) {
            this.tag = tag;
            this.version = version;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
