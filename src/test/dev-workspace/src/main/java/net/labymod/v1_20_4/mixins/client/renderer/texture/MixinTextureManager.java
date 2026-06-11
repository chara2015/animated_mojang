package net.labymod.v1_20_4.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({geo.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<ahg, gdy> c;

    @Shadow
    public abstract void a(ahg ahgVar, gdy gdyVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((ahg) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        gdy textureObject = (gdy) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), gdy.class);
        gdy currentTexture = this.c.get((ahg) location);
        a((ahg) location, textureObject);
        if (currentTexture != null) {
            TextureUtil.releaseTextureId(currentTexture.a());
        }
    }
}
