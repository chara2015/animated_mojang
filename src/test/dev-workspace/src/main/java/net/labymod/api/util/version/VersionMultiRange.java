package net.labymod.api.util.version;

import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;
import net.labymod.api.util.version.comparison.VersionMultiRangeComparison;
import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/VersionMultiRange.class */
public class VersionMultiRange implements VersionCompatibility {
    private final VersionMultiRangeComparison<Version> comparison;

    public VersionMultiRange(@NotNull String ranges) {
        this.comparison = VersionMultiRangeComparison.parse(ranges, VersionDeserializer::from, VersionRange::verifyVersionFormat);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(Version version) {
        return this.comparison.isCompatible(version);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(Version version) {
        return this.comparison.isGreaterThan(version);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(Version version) {
        return this.comparison.isLowerThan(version);
    }

    public String toString() {
        return this.comparison.toString();
    }
}
