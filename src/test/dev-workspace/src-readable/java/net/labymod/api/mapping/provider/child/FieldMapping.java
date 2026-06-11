package net.labymod.api.mapping.provider.child;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/provider/child/FieldMapping.class */
public interface FieldMapping extends Mapping {
    @Nullable
    String getDescriptor();

    @Nullable
    String getMappedDescriptor();
}
