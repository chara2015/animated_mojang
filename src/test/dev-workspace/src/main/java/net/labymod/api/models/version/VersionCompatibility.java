package net.labymod.api.models.version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/VersionCompatibility.class */
public interface VersionCompatibility extends VersionComparison<Version> {
    @Override // net.labymod.api.models.version.VersionComparison
    boolean isCompatible(Version version);

    @Override // net.labymod.api.models.version.VersionComparison
    boolean isGreaterThan(Version version);

    @Override // net.labymod.api.models.version.VersionComparison
    boolean isLowerThan(Version version);
}
