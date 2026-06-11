package net.labymod.v1_21_4.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({hev.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<akv, hee> c;

    @Shadow
    public abstract void a(akv akvVar, hee heeVar);

    @Shadow
    public abstract void a(akv akvVar, hek hekVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((akv) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        Object texture2 = MinecraftUtil.toMinecraft(texture);
        hee textureObject = (hee) CastUtil.requireInstanceOf(texture2, hee.class);
        hee currentTexture = this.c.get(location.getMinecraftLocation());
        if (texture2 instanceof hek) {
            hek reloadableTexture = (hek) texture2;
            a((akv) location.getMinecraftLocation(), reloadableTexture);
        } else {
            a((akv) location.getMinecraftLocation(), textureObject);
        }
        if (currentTexture != null) {
            TextureUtil.releaseTextureId(currentTexture.a());
        }
    }
}
