package net.labymod.v1_17_1.laby3d.util;

import javax.inject.Singleton;
import net.labymod.api.laby3d.util.DirtyFramebufferClearer;
import net.labymod.api.models.Implements;
import org.lwjgl.opengl.GL11;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/laby3d/util/VersionedDirtyFramebufferClear.class */
@Singleton
@Implements(DirtyFramebufferClearer.class)
public class VersionedDirtyFramebufferClear implements DirtyFramebufferClearer {
    @Override // net.labymod.api.laby3d.util.DirtyFramebufferClearer
    public void clear() {
        GL11.glClear(256);
    }
}
