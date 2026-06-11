package net.labymod.v1_20_1.client.resources.pack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/resources/pack/PackUtil.class */
public final class PackUtil {
    public static boolean isModifiedPackResources(ajl resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof WrappedPackResources) {
            WrappedPackResources wrappedPackResources = (WrappedPackResources) resources;
            ajl delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }

    public static ajl unwrap(ajl resources) {
        if (!(resources instanceof WrappedPackResources)) {
            return resources;
        }
        WrappedPackResources wrapped = (WrappedPackResources) resources;
        return wrapped.delegate();
    }
}
