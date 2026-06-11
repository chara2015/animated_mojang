package net.labymod.api.laby3d.renderer.snapshot;

import it.unimi.dsi.fastutil.objects.Object2ReferenceMap;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/ExtraKeyCache.class */
final class ExtraKeyCache {
    static final Object2ReferenceMap<String, ExtraKey<?>> CACHE = new Object2ReferenceOpenHashMap();

    ExtraKeyCache() {
    }
}
