package net.labymod.v1_8_9.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_8_9.client.resources.texture.VersionedLabyTexture;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({bmj.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Final
    private Map<jy, bmk> b;

    @Shadow
    public abstract boolean a(jy jyVar, bmk bmkVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.b.get((jy) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        bmk textureObject = (bmk) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), bmk.class);
        VersionedLabyTexture versionedLabyTexture = (bmk) this.b.get((jy) location);
        a((jy) location, textureObject);
        if (versionedLabyTexture != null) {
            if (versionedLabyTexture instanceof VersionedLabyTexture) {
                VersionedLabyTexture labyTexture = versionedLabyTexture;
                labyTexture.c();
            } else {
                bml.a(versionedLabyTexture.b());
            }
        }
    }

    @Inject(method = {"deleteTexture"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureUtil;deleteTexture(I)V", shift = At.Shift.BEFORE)})
    private void labyMod$deleteTexture(jy location, CallbackInfo ci) {
        this.b.remove(location);
    }
}
