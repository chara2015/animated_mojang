package net.labymod.v1_20_1.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({fuw.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<acq, fug> c;

    @Shadow
    public abstract void a(acq acqVar, fug fugVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((acq) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        fug textureObject = (fug) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), fug.class);
        fug currentTexture = this.c.get((acq) location);
        a((acq) location, textureObject);
        if (currentTexture != null) {
            TextureUtil.releaseTextureId(currentTexture.a());
        }
    }
}
