package net.labymod.core.client.resources.texture;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/AbstractGameImageProvider.class */
public abstract class AbstractGameImageProvider implements GameImageProvider {
    protected abstract GameImage create(int i, int i2, int i3);
}
