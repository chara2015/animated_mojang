package net.labymod.api.models.addon.info.dependency;

import java.util.Optional;
import net.labymod.api.models.version.Version;
import net.labymod.api.models.version.VersionCompatibility;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/info/dependency/AddonDependency.class */
public class AddonDependency {
    private final String namespace;
    private final VersionCompatibility compatability;
    private final boolean optional;

    @Deprecated
    public AddonDependency(String namespace, boolean optional) {
        this(namespace, null, optional);
    }

    public AddonDependency(String namespace, Version compatability, boolean optional) {
        this.namespace = namespace;
        this.compatability = compatability;
        this.optional = optional;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Optional<VersionCompatibility> getCompatability() {
        return Optional.ofNullable(this.compatability);
    }

    public boolean isOptional() {
        return this.optional;
    }
}
