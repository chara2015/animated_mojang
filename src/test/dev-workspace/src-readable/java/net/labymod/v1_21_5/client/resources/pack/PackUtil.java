package net.labymod.v1_21_5.client.resources.pack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/resources/pack/PackUtil.class */
public final class PackUtil {
    public static boolean isModifiedPackResources(aua resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof WrappedPackResources) {
            WrappedPackResources wrappedPackResources = (WrappedPackResources) resources;
            aua delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }

    public static aua unwrap(aua resources) {
        if (!(resources instanceof WrappedPackResources)) {
            return resources;
        }
        WrappedPackResources wrapped = (WrappedPackResources) resources;
        return wrapped.delegate();
    }
}
