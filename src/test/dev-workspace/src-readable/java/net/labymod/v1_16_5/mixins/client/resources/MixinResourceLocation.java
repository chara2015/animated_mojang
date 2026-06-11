package net.labymod.v1_16_5.mixins.client.resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.util.ResourcesUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/resources/MixinResourceLocation.class */
@Mixin({vk.class})
@Implements({@Interface(iface = ResourceLocation.class, prefix = "rl$")})
public abstract class MixinResourceLocation implements ResourceLocation {
    private transient Metadata labyMod$metadata = Metadata.create();

    @Shadow
    public abstract String shadow$b();

    @Shadow
    public abstract String shadow$a();

    @Intrinsic
    public String rl$getNamespace() {
        return shadow$b();
    }

    @Intrinsic
    public String rl$getPath() {
        return shadow$a();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        ach resourceManager = djz.C().N();
        vk rl = (vk) getMinecraftLocation();
        if (resourceManager.a().contains(rl.b())) {
            return resourceManager.a((vk) getMinecraftLocation()).b();
        }
        String resourcePath = String.format(Locale.ROOT, "/assets/%s/%s", rl.b(), rl.a());
        InputStream stream = vk.class.getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new FileNotFoundException(resourcePath);
        }
        return stream;
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public boolean exists() {
        return ResourcesUtil.hasResource((ResourceLocation) getMinecraftLocation()) || djz.C().N().b((vk) getMinecraftLocation());
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
}
