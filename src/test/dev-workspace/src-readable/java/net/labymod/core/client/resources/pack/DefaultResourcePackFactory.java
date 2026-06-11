package net.labymod.core.client.resources.pack;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.resources.pack.ResourcePack;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/pack/DefaultResourcePackFactory.class */
@Singleton
@Implements(ResourcePack.Factory.class)
public class DefaultResourcePackFactory implements ResourcePack.Factory {
    @Inject
    public DefaultResourcePackFactory() {
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack.Factory
    public ResourcePack classpath(String name) {
        return new ClasspathResourcePack(name);
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack.Factory
    public ResourcePack classpath(String name, LoadedAddon addon) {
        return new ClasspathResourcePack(name, addon);
    }

    @Override // net.labymod.api.client.resources.pack.ResourcePack.Factory
    public ResourcePack folder(String namespace, Path directory) {
        if (directory == null || !Files.isDirectory(directory, new LinkOption[0])) {
            throw new IllegalArgumentException("The given directory is not a valid directory.");
        }
        return new FolderResourcePack(namespace, directory);
    }
}
