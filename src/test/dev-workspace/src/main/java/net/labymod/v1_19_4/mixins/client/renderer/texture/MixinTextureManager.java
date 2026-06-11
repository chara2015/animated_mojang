package net.labymod.v1_19_4.mixins.client.renderer.texture;

import com.mojang.blaze3d.platform.TextureUtil;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({ftd.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<add, fsn> c;

    @Shadow
    public abstract void a(add addVar, fsn fsnVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((add) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        fsn textureObject = (fsn) CastUtil.requireInstanceOf(MinecraftUtil.toMinecraft(texture), fsn.class);
        fsn currentTexture = this.c.get((add) location);
        a((add) location, textureObject);
        if (currentTexture != null) {
            TextureUtil.releaseTextureId(currentTexture.b());
        }
    }

    @Insert(method = {"release(Lnet/minecraft/resources/ResourceLocation;)V"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;releaseTextureId(I)V", shift = At.Shift.BEFORE, ordinal = 0))
    public void labyMod$deleteTexture(add location, InsertInfo callback) {
        this.c.remove(location);
    }
}
