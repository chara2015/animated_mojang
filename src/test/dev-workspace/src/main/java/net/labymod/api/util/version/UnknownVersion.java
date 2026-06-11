package net.labymod.api.util.version;

import java.util.Objects;
import net.labymod.api.models.version.Version;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/UnknownVersion.class */
public class UnknownVersion implements Version {
    protected final String identifier;

    public UnknownVersion(@NotNull String identifier) {
        this.identifier = identifier;
    }

    @Override // net.labymod.api.models.version.Version
    public int getMajor() {
        return 0;
    }

    @Override // net.labymod.api.models.version.Version
    public int getMinor() {
        return 0;
    }

    @Override // net.labymod.api.models.version.Version
    public int getPatch() {
        return 0;
    }

    @Override // net.labymod.api.models.version.Version
    public String getExtension() {
        return this.identifier;
    }

    @Override // net.labymod.api.models.version.Version
    public int getBuild() {
        return 0;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(Version version) {
        return equals(version);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(Version version) {
        return false;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(Version version) {
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnknownVersion that = (UnknownVersion) o;
        return Objects.equals(this.identifier, that.identifier);
    }

    public int hashCode() {
        return this.identifier.hashCode();
    }

    public String toString() {
        return this.identifier;
    }
}
