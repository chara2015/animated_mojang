package net.labymod.v1_21_1.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_21_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({gqm.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<akr, gpw> c;

    @Shadow
    public abstract void a(akr akrVar, gpw gpwVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((akr) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        gpw textureObject = (gpw) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), gpw.class);
        gpw currentTexture = this.c.get((akr) location);
        a((akr) location, textureObject);
        if (currentTexture != null) {
            TextureUtil.releaseTextureId(currentTexture.a());
        }
    }
}
