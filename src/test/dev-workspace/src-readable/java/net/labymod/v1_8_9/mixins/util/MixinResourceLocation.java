package net.labymod.v1_8_9.mixins.util;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.util.ResourcesUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/util/MixinResourceLocation.class */
@Mixin({jy.class})
public abstract class MixinResourceLocation implements ResourceLocation {
    private transient Metadata labyMod$metadata = Metadata.create();

    @Shadow
    public abstract String a();

    @Shadow
    public abstract String b();

    @Override // net.labymod.api.client.resources.ResourceLocation
    public String getNamespace() {
        return b();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public String getPath() {
        return a();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return ave.A().Q().a((jy) getMinecraftLocation()).b();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public boolean exists() {
        return ResourcesUtil.hasResource((ResourceLocation) getMinecraftLocation()) || labyMod$hasResource();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.resources.ResourceLocation
    public <T> T getMinecraftLocation() {
        return this;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public void metadata(Metadata metadata) {
        this.labyMod$metadata = metadata;
    }

    @Override // net.labymod.api.metadata.MetadataExtension
    public Metadata metadata() {
        return this.labyMod$metadata;
    }

    private boolean labyMod$hasResource() {
        try {
            bnh resource = ave.A().Q().a((jy) getMinecraftLocation());
            InputStream stream = resource.b();
            IOUtil.closeSilent(stream);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
