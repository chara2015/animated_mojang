package net.labymod.core.revision;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.models.version.Version;
import net.labymod.api.revision.Revision;
import net.labymod.api.revision.RevisionRegistry;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/revision/DefaultRevisionRegistry.class */
@Singleton
@Implements(RevisionRegistry.class)
public class DefaultRevisionRegistry implements RevisionRegistry {
    private final Map<String, Map<SemanticVersion, Revision>> revisions = new HashMap();

    @Inject
    public DefaultRevisionRegistry() {
        register(new PopupRevision("labymod", new SemanticVersion(4, 1, 0), "2023-09-25", "textures/revision/4.1.0.png", true));
        register(new SimpleRevision("labymod", new SemanticVersion(4, 1, 4), "2023-11-13"));
        register(new SimpleRevision("labymod", new SemanticVersion(4, 1, 11), "2023-12-12"));
        register(new PopupRevision("labymod", new SemanticVersion(4, 2, 0), "2024-04-01", "textures/revision/4.2.0.png", true));
        register(new PopupRevision("labymod", new SemanticVersion(4, 3, 0), "2025-09-15", "textures/revision/4.3.0.png", true));
        register(new PopupRevision("labymod", new SemanticVersion(4, 4, 0), "2026-04-02", "textures/revision/4.4.0.png", true));
        register(new SimpleRevision("labymod", new SemanticVersion(4, 4, 5), "2026-04-04"));
    }

    @Override // net.labymod.api.revision.RevisionRegistry
    public void register(Revision revision) {
        getRevisions(revision.getNamespace()).put(revision.version(), revision);
    }

    @Override // net.labymod.api.revision.RevisionRegistry
    @Nullable
    public Revision getLastRevision(String namespace) {
        Map<SemanticVersion, Revision> revisions = getRevisions(namespace);
        Version version = null;
        for (Version semanticVersion : revisions.keySet()) {
            if (version == null || semanticVersion.isGreaterThan(version)) {
                version = semanticVersion;
            }
        }
        if (version == null) {
            return null;
        }
        return revisions.get(version);
    }

    @Override // net.labymod.api.revision.RevisionRegistry
    @Nullable
    public Revision getRevision(String namespace, String version) {
        Version deserializedVersion = VersionDeserializer.from(version);
        Map<SemanticVersion, Revision> revisions = getRevisions(namespace);
        for (Map.Entry<SemanticVersion, Revision> entry : revisions.entrySet()) {
            if (entry.getKey().isCompatible(deserializedVersion)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override // net.labymod.api.revision.RevisionRegistry
    public Map<SemanticVersion, Revision> getRevisions(String namespace) {
        return this.revisions.computeIfAbsent(namespace, k -> {
            return new HashMap();
        });
    }

    @Override // net.labymod.api.revision.RevisionRegistry
    public boolean isRelevant(String namespace, String version) {
        Revision lastRevision = getRevision(namespace, version);
        return lastRevision != null && lastRevision.isRelevant();
    }
}
