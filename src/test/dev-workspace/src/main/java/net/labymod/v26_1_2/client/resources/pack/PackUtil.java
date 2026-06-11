package net.labymod.v26_1_2.client.resources.pack;

import net.minecraft.server.packs.PackResources;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/pack/PackUtil.class */
public final class PackUtil {
    public static boolean isModifiedPackResources(PackResources resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof WrappedPackResources) {
            WrappedPackResources wrappedPackResources = (WrappedPackResources) resources;
            PackResources delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }

    public static PackResources unwrap(PackResources resources) {
        if (!(resources instanceof WrappedPackResources)) {
            return resources;
        }
        WrappedPackResources wrapped = (WrappedPackResources) resources;
        return wrapped.delegate();
    }
}
