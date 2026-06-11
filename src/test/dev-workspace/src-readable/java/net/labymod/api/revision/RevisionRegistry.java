package net.labymod.api.revision;

import java.util.Map;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.version.SemanticVersion;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/revision/RevisionRegistry.class */
@Referenceable
public interface RevisionRegistry {
    void register(Revision revision);

    @Nullable
    Revision getLastRevision(String str);

    @Nullable
    Revision getRevision(String str, String str2);

    Map<SemanticVersion, Revision> getRevisions(String str);

    boolean isRelevant(String str, String str2);
}
