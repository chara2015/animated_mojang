package net.labymod.api.laby3d.util;

import net.labymod.api.models.NullableReference;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/DirtyFramebufferClearer.class */
@NullableReference
@ApiStatus.Internal
@Referenceable
public interface DirtyFramebufferClearer {
    void clear();
}
