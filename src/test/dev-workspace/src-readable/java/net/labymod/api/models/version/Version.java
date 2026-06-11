package net.labymod.api.models.version;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/Version.class */
public interface Version extends VersionCompatibility {
    int getMajor();

    int getMinor();

    int getPatch();

    String getExtension();

    int getBuild();

    default Class<?> getFormat() {
        return getClass();
    }

    default boolean compareExtension(Version version) {
        return Objects.equals(getExtension(), version.getExtension());
    }

    default boolean compareFormat(Version version) {
        return Objects.equals(getFormat(), version.getFormat());
    }
}
