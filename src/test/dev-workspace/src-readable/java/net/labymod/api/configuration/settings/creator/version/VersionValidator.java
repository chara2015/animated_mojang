package net.labymod.api.configuration.settings.creator.version;

import net.labymod.api.LabyAPI;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.version.serial.VersionCompatibilityDeserializer;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/settings/creator/version/VersionValidator.class */
public abstract class VersionValidator {
    public abstract boolean isSupportedVersion(String str, String str2);

    protected VersionValidator(LabyAPI labyAPI) {
    }

    protected boolean compareVersion(String currentVersion, String version) {
        return compareVersion(VersionDeserializer.from(currentVersion), version);
    }

    protected boolean compareVersion(Version currentVersion, String version) {
        if (version.contains("<") || version.contains(",")) {
            VersionCompatibility compatibility = VersionCompatibilityDeserializer.from(version);
            return compatibility.isCompatible(currentVersion);
        }
        return currentVersion.isCompatible(VersionDeserializer.from(version));
    }
}
