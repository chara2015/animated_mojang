package net.labymod.v1_16_5.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({ekd.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<vk, ejq> c;

    @Shadow
    public abstract void a(vk vkVar, ejq ejqVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((vk) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        ejq textureObject = (ejq) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), ejq.class);
        ejq currentTexture = this.c.get((vk) location);
        a((vk) location, textureObject);
        if (currentTexture != null) {
            dex.a(currentTexture.b());
        }
    }

    @Insert(method = {"release(Lnet/minecraft/resources/ResourceLocation;)V"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;releaseTextureId(I)V", shift = At.Shift.BEFORE, ordinal = 0))
    public void labyMod$deleteTexture(vk location, InsertInfo callback) {
        this.c.remove(location);
    }
}
