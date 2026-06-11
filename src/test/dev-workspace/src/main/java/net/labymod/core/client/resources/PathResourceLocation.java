package net.labymod.core.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.debug.filewatcher.WatchablePath;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/PathResourceLocation.class */
public class PathResourceLocation extends AbstractResourceLocation {
    private final WatchablePath watchablePath;

    public PathResourceLocation(Path file, String namespace, String path) {
        super(namespace, path);
        this.watchablePath = LabyMod.references().watchablePathManager().create(file);
    }

    @Override // net.labymod.core.client.resources.AbstractResourceLocation, net.labymod.api.client.resources.ResourceLocation
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return this.watchablePath.openStream();
    }

    @Override // net.labymod.core.client.resources.AbstractResourceLocation, net.labymod.api.client.resources.ResourceLocation
    public boolean exists() {
        return this.watchablePath.exists();
    }

    public boolean isModified() {
        return this.watchablePath.isModified();
    }

    public String toString() {
        return getNamespace() + ":" + getPath();
    }
}
