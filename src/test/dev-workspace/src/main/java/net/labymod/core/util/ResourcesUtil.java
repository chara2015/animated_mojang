package net.labymod.core.util;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.generated.ReferenceStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/ResourcesUtil.class */
public final class ResourcesUtil {
    private ResourcesUtil() {
    }

    public static boolean hasResource(ResourceLocation location) {
        ReferenceStorage ref = Laby.references();
        return ref.textureRepository().hasResource(location);
    }
}
