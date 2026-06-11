package net.labymod.api.modloader;

import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.DefaultRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/modloader/ModLoaderRegistry.class */
@Singleton
@Referenceable
public class ModLoaderRegistry extends DefaultRegistry<ModLoader> {
    public static ModLoaderRegistry instance() {
        return Laby.references().modLoaderRegistry();
    }
}
