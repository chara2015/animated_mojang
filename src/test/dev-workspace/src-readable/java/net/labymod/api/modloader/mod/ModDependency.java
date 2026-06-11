package net.labymod.api.modloader.mod;

import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionComparison;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/modloader/mod/ModDependency.class */
public interface ModDependency {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/modloader/mod/ModDependency$Kind.class */
    public enum Kind {
        DEPENDS,
        RECOMMENDS,
        SUGGESTS,
        CONFLICTS,
        BREAKS
    }

    @NotNull
    String getModId();

    @NotNull
    VersionComparison<Version> versionCompatibility();

    @NotNull
    Kind kind();
}
