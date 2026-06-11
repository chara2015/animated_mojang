package net.labymod.v1_21_8.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.RenderDeviceOutOfMemoryException;
import net.labymod.v1_21_8.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({hru.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<ame, hrc> c;

    @Shadow
    public abstract void a(ame ameVar, hrc hrcVar);

    @Shadow
    public abstract void a(ame ameVar, hrj hrjVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((ame) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        Object texture2 = MinecraftUtil.toMinecraft(texture);
        hrc textureObject = (hrc) CastUtil.requireInstanceOf(texture2, hrc.class);
        hrc currentTexture = this.c.get(location.getMinecraftLocation());
        if (texture2 instanceof hrj) {
            hrj reloadableTexture = (hrj) texture2;
            a((ame) location.getMinecraftLocation(), reloadableTexture);
        } else {
            a((ame) location.getMinecraftLocation(), textureObject);
        }
        if (currentTexture != null) {
            try {
                currentTexture.close();
            } catch (RenderDeviceException | RenderDeviceOutOfMemoryException | IllegalStateException e) {
            }
        }
    }
}
