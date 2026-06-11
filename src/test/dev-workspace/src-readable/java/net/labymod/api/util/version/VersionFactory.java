package net.labymod.api.util.version;

import java.util.List;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionParser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/VersionFactory.class */
public final class VersionFactory {
    private final List<VersionParser> parsers;

    public VersionFactory(List<VersionParser> parsers) {
        this.parsers = parsers;
    }

    public Version from(String version) {
        for (VersionParser parser : this.parsers) {
            Version parsedVersion = parser.tryParse(version);
            if (parsedVersion != null) {
                return parsedVersion;
            }
        }
        return new UnknownVersion(version);
    }
}
