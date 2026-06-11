package net.labymod.api.client.resources;

import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/Resources.class */
@Referenceable
public interface Resources {
    ResourceLocationFactory resourceLocationFactory();

    TextureRepository textureRepository();
}
