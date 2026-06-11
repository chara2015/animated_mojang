package net.labymod.v1_20_4.client.resources.pack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/resources/pack/PackUtil.class */
public final class PackUtil {
    public static boolean isModifiedPackResources(aow resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof WrappedPackResources) {
            WrappedPackResources wrappedPackResources = (WrappedPackResources) resources;
            aow delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }

    public static aow unwrap(aow resources) {
        if (!(resources instanceof WrappedPackResources)) {
            return resources;
        }
        WrappedPackResources wrapped = (WrappedPackResources) resources;
        return wrapped.delegate();
    }
}
