package net.labymod.core.client.resources.texture.loader;

import net.labymod.api.client.resources.texture.TextureLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/LoaderEntry.class */
public class LoaderEntry {
    private final TextureLoader textureLoader;
    private final int priority;

    public LoaderEntry(TextureLoader textureLoader, int priority) {
        this.textureLoader = textureLoader;
        this.priority = priority;
    }

    public TextureLoader getTextureLoader() {
        return this.textureLoader;
    }

    public int getPriority() {
        return this.priority;
    }
}
