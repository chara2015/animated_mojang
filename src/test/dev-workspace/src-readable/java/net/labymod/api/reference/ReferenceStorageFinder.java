package net.labymod.api.reference;

import java.lang.System;
import java.util.Iterator;
import net.labymod.api.reference.util.ReferenceUtil;
import net.labymod.api.service.CustomServiceLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/ReferenceStorageFinder.class */
public final class ReferenceStorageFinder {
    private static final System.Logger LOGGER = System.getLogger("ReferenceStorageFinder");

    public static ReferenceStorageAccessor find(ClassLoader loader) {
        ReferenceUtil.setThrowableConsumer(throwable -> {
            LOGGER.log(System.Logger.Level.ERROR, "An error occurred while creating a reference", throwable);
        });
        CustomServiceLoader<ReferenceStorageAccessor> accessors = CustomServiceLoader.load(ReferenceStorageAccessor.class, loader, CustomServiceLoader.ServiceType.ADVANCED);
        ReferenceStorageAccessor finalStorage = null;
        Iterator<ReferenceStorageAccessor> it = accessors.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ReferenceStorageAccessor accessor = it.next();
            if (accessor.getClass().getClassLoader() == loader) {
                if (accessor.isVersion()) {
                    finalStorage = accessor;
                    break;
                }
                finalStorage = accessor;
            }
        }
        if (finalStorage == null) {
            throw new IllegalStateException("No ReferenceStorage could be found in the '" + String.valueOf(loader) + "' classloader.");
        }
        return finalStorage;
    }
}
