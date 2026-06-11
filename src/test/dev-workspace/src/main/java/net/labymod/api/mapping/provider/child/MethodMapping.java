package net.labymod.api.mapping.provider.child;

import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/provider/child/MethodMapping.class */
public interface MethodMapping extends Mapping {
    @NotNull
    String getDescriptor();

    @NotNull
    String getMappedDescriptor();
}
