package net.labymod.api.client.resources.texture;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/TextureResourceWrapper.class */
public abstract class TextureResourceWrapper<T> implements Texture {
    private final T delegate;

    @Nullable
    public abstract GameImage getImage();

    public TextureResourceWrapper(T delegate) {
        this.delegate = delegate;
    }

    public T getDelegate() {
        return this.delegate;
    }
}
