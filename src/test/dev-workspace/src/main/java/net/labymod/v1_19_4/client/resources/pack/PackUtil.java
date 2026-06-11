package net.labymod.v1_19_4.client.resources.pack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/resources/pack/PackUtil.class */
public final class PackUtil {
    public static boolean isModifiedPackResources(ajv resources) {
        if (resources instanceof ModifiedPackResources) {
            return true;
        }
        if (resources instanceof WrappedPackResources) {
            WrappedPackResources wrappedPackResources = (WrappedPackResources) resources;
            ajv delegate = wrappedPackResources.delegate();
            return isModifiedPackResources(delegate);
        }
        return false;
    }

    public static ajv unwrap(ajv resources) {
        if (!(resources instanceof WrappedPackResources)) {
            return resources;
        }
        WrappedPackResources wrapped = (WrappedPackResources) resources;
        return wrapped.delegate();
    }
}
