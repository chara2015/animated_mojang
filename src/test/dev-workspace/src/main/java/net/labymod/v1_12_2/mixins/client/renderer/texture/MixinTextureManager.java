package net.labymod.v1_12_2.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({cdr.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Final
    private Map<nf, cds> c;

    @Shadow
    public abstract boolean a(nf nfVar, cds cdsVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((nf) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        cds textureObject = (cds) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), cds.class);
        cds currentTexture = this.c.get((nf) location);
        a((nf) location, textureObject);
        if (currentTexture != null) {
            cdt.a(currentTexture.b());
        }
    }

    @Inject(method = {"deleteTexture"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureUtil;deleteTexture(I)V", shift = At.Shift.BEFORE)})
    private void labyMod$deleteTexture(nf location, CallbackInfo ci) {
        this.c.remove(location);
    }
}
