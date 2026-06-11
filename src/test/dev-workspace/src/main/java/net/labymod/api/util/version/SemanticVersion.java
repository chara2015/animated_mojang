package net.labymod.api.util.version;

import net.labymod.api.models.version.Version;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/SemanticVersion.class */
public class SemanticVersion implements Version {
    protected int major;
    protected int minor;
    protected int patch;
    protected String extension;

    @Nullable
    protected String separator;
    protected int build;

    public SemanticVersion() {
    }

    @Deprecated(forRemoval = true, since = "4.3.44")
    public SemanticVersion(String version) throws NumberFormatException {
        String[] parts = version.split("-");
        String[] numbers = parts[0].split("\\.");
        this.major = numbers.length > 0 ? Integer.parseInt(numbers[0]) : 0;
        this.minor = numbers.length > 1 ? Integer.parseInt(numbers[1]) : 0;
        if (numbers.length > 2) {
            this.patch = Integer.parseInt(numbers[2]);
        }
        this.extension = parts.length > 1 ? parts[1] : null;
    }

    public SemanticVersion(int major, int minor) {
        this(major, minor, 0);
    }

    public SemanticVersion(int major, int minor, int patch) {
        this(major, minor, patch, null);
    }

    public SemanticVersion(int major, int minor, int patch, String extension) {
        this(major, minor, patch, extension, 0);
    }

    public SemanticVersion(int major, int minor, int patch, String extension, int build) {
        this(major, minor, patch, extension, null, build);
    }

    public SemanticVersion(int major, int minor, int patch, String extension, @Nullable String separator, int build) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.extension = extension;
        this.separator = separator;
        this.build = build;
    }

    @Override // net.labymod.api.models.version.Version
    public int getMajor() {
        return this.major;
    }

    @Override // net.labymod.api.models.version.Version
    public int getMinor() {
        return this.minor;
    }

    @Override // net.labymod.api.models.version.Version
    public int getPatch() {
        return this.patch;
    }

    @Override // net.labymod.api.models.version.Version
    public String getExtension() {
        return this.extension;
    }

    @Override // net.labymod.api.models.version.Version
    public int getBuild() {
        return this.build;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(Version other) {
        return other != null && compareFormat(other) && this.major == other.getMajor() && this.minor == other.getMinor() && this.patch == other.getPatch() && compareExtension(other);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(Version other) {
        if (other == null) {
            return true;
        }
        return this.major != other.getMajor() ? this.major > other.getMajor() : this.minor != other.getMinor() ? this.minor > other.getMinor() : this.patch != other.getPatch() ? this.patch > other.getPatch() : compareExtension(other) && this.build > other.getBuild();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.models.version.VersionCompatibility, net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(Version other) {
        return (isCompatible(other) || isGreaterThan(other)) ? false : true;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SemanticVersion that = (SemanticVersion) o;
        return this.major == that.major && this.minor == that.minor && this.patch == that.patch && compareExtension(that);
    }

    public int hashCode() {
        int result = this.major;
        return (31 * ((31 * ((31 * ((31 * ((31 * result) + this.minor)) + this.patch)) + getFormat().hashCode())) + (this.extension != null ? this.extension.hashCode() : 0))) + this.build;
    }

    public String toString() {
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append(this.major).append(".").append(this.minor);
        if (this.patch > 0) {
            bobTheBuilder.append(".").append(this.patch);
        }
        if (this.extension != null) {
            bobTheBuilder.append("-").append(this.extension);
            if (this.build > 0) {
                bobTheBuilder.append(this.separator == null ? "" : this.separator).append(this.build);
            }
        }
        return bobTheBuilder.toString();
    }
}
