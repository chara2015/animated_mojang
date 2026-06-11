package net.labymod.v1_21_11.mixins.client.resources;

import java.io.IOException;
import java.io.InputStream;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.core.util.ResourcesUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/resources/MixinResourceLocation.class */
@Mixin({Identifier.class})
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

    public InputStream openStream() throws IOException {
        IdeUtil.ensureResourcesLoaded(this);
        return ((Resource) Minecraft.getInstance().getResourceManager().getResource((Identifier) getMinecraftLocation()).orElseThrow(IOException::new)).open();
    }

    public boolean exists() {
        return ResourcesUtil.hasResource((ResourceLocation) getMinecraftLocation()) || Minecraft.getInstance().getResourceManager().getResource((Identifier) getMinecraftLocation()).isPresent();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> T getMinecraftLocation() {
        return this;
    }

    public void metadata(Metadata metadata) {
        this.labyMod$metadata = metadata;
    }

    public Metadata metadata() {
        return this.labyMod$metadata;
    }
}
