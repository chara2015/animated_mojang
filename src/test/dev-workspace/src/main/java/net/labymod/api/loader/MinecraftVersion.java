package net.labymod.api.loader;

import java.util.Objects;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionComparison;
import net.labymod.api.util.version.serial.VersionDeserializer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/loader/MinecraftVersion.class */
public final class MinecraftVersion implements VersionComparison<MinecraftVersion> {
    private final Version version;
    private final long releaseTime;
    private final long time;
    private final long complianceLevel;
    private final String rawVersion;
    private final String formattedVersion;
    private boolean current;
    private boolean older;
    private boolean newer;

    public MinecraftVersion(Version version, long releaseTime, long time, long complianceLevel) {
        this.version = version;
        this.releaseTime = releaseTime;
        this.time = time;
        this.complianceLevel = complianceLevel;
        this.rawVersion = version.toString();
        this.formattedVersion = "v" + this.rawVersion.replace(".", "_").replace("-", "_");
    }

    public void initialize(MinecraftVersion current) {
        long thisReleaseTime = getReleaseTime();
        long currentReleaseTime = current.getReleaseTime();
        this.current = thisReleaseTime == currentReleaseTime;
        this.older = currentReleaseTime <= thisReleaseTime;
        this.newer = currentReleaseTime >= thisReleaseTime;
    }

    public boolean orNewer() {
        return this.newer;
    }

    public boolean orOlder() {
        return this.older;
    }

    public boolean isCurrent() {
        return this.current;
    }

    public static MinecraftVersion of(String version, long releaseTime, long time, long complianceLevel) {
        return new MinecraftVersion(VersionDeserializer.from(version), releaseTime, time, complianceLevel);
    }

    public Version version() {
        return this.version;
    }

    public long getReleaseTime() {
        return this.releaseTime;
    }

    public long getTime() {
        return this.time;
    }

    public long getComplianceLevel() {
        return this.complianceLevel;
    }

    public String getRawVersion() {
        return this.rawVersion;
    }

    public String getFormattedVersion() {
        return this.formattedVersion;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(MinecraftVersion other) {
        return this.releaseTime == other.releaseTime;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(MinecraftVersion other) {
        return this.releaseTime > other.releaseTime;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(MinecraftVersion other) {
        return this.releaseTime < other.releaseTime;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        MinecraftVersion that = (MinecraftVersion) obj;
        return Objects.equals(this.version, that.version) && Objects.equals(Long.valueOf(this.releaseTime), Long.valueOf(that.releaseTime)) && Objects.equals(Long.valueOf(this.time), Long.valueOf(that.time)) && this.complianceLevel == that.complianceLevel;
    }

    public int hashCode() {
        int result = this.version != null ? this.version.hashCode() : 0;
        return (31 * ((31 * ((31 * result) + Long.hashCode(this.releaseTime))) + Long.hashCode(this.time))) + Long.hashCode(this.complianceLevel);
    }

    public String toString() {
        String strValueOf = String.valueOf(this.version);
        long j = this.releaseTime;
        long j2 = this.time;
        long j3 = this.complianceLevel;
        return "MinecraftVersion[version=" + strValueOf + ", releaseTime=" + j + ", time=" + strValueOf + ", complianceLevel=" + j2 + "]";
    }
}
