package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/SimpleTexture.class */
public class SimpleTexture extends ReloadableTexture {
    public SimpleTexture(Identifier $$0) {
        super($$0);
    }

    @Override // net.minecraft.client.renderer.texture.ReloadableTexture
    public TextureContents loadContents(ResourceManager $$0) throws IOException {
        return TextureContents.load($$0, resourceId());
    }
}
