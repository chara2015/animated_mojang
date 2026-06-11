package net.labymod.v1_21_8.mixins.client.resources;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.util.ResourcesUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/resources/MixinResourceLocation.class */
@Mixin({ame.class})
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
        return ((axm) fue.R().ad().getResource((ame) getMinecraftLocation()).orElseThrow(IOException::new)).d();
    }

    @Override // net.labymod.api.client.resources.ResourceLocation
    public boolean exists() {
        return ResourcesUtil.hasResource((ResourceLocation) getMinecraftLocation()) || fue.R().ad().getResource((ame) getMinecraftLocation()).isPresent();
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
