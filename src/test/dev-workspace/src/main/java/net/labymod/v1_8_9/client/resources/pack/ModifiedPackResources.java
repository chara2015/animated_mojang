package net.labymod.v1_8_9.client.resources.pack;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.pack.PackTypes;
import net.labymod.api.client.resources.pack.ResourcePack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/resources/pack/ModifiedPackResources.class */
public class ModifiedPackResources implements bnk {
    private final ResourcePack resourcePack;

    public ModifiedPackResources(ResourcePack resourcePack) {
        this.resourcePack = resourcePack;
    }

    public InputStream a(jy resourceLocation) throws IOException {
        return this.resourcePack.getResource(PackTypes.CLIENT_RESOURCES, ResourceLocation.create(resourceLocation.b(), resourceLocation.a()));
    }

    public boolean b(jy resourceLocation) {
        return this.resourcePack.hasResource(PackTypes.CLIENT_RESOURCES, ResourceLocation.create(resourceLocation.b(), resourceLocation.a()));
    }

    public Set<String> c() {
        return this.resourcePack.getNamespaces(PackTypes.CLIENT_RESOURCES);
    }

    public <T extends bnw> T a(bny iMetadataSerializer, String s) throws IOException {
        return null;
    }

    public BufferedImage a() throws IOException {
        return null;
    }

    public String b() {
        return this.resourcePack.getName();
    }
}
