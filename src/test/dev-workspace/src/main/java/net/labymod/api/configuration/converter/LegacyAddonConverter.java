package net.labymod.api.configuration.converter;

import java.nio.file.Path;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyAddonConverter.class */
public abstract class LegacyAddonConverter<T> extends LegacyConverter<T> {
    private final String fileName;
    private Version version;

    protected LegacyAddonConverter(String fileName, Class<? extends T> type) {
        super((Path) null, type);
        this.fileName = fileName;
    }

    public final void load(Version version) {
        this.version = version;
        this.path = version.getConfigPath().resolve(this.fileName);
        load();
        this.path = null;
    }

    public Version getVersion() {
        return this.version;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyAddonConverter$Version.class */
    public enum Version {
        V1_8("1.8"),
        V1_12("1.12"),
        V1_16("1.16");

        private final String version;
        private final Path path;
        private final Path configPath;

        Version(String version) {
            this.version = version;
            this.path = LegacyConverter.LEGACY_PATH.resolve("addons-" + version);
            this.configPath = this.path.resolve("config");
        }

        public String getVersion() {
            return this.version;
        }

        public Path getPath() {
            return this.path;
        }

        public Path getConfigPath() {
            return this.configPath;
        }
    }
}
