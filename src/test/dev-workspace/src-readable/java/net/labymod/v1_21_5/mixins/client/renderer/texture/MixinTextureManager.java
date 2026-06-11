package net.labymod.v1_21_5.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.RenderDeviceOutOfMemoryException;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({hks.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<alr, hkb> c;

    @Shadow
    public abstract void a(alr alrVar, hkb hkbVar);

    @Shadow
    public abstract void a(alr alrVar, hkh hkhVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((alr) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        Object texture2 = MinecraftUtil.toMinecraft(texture);
        hkb textureObject = (hkb) CastUtil.requireInstanceOf(texture2, hkb.class);
        hkb currentTexture = this.c.get(location.getMinecraftLocation());
        if (texture2 instanceof hkh) {
            hkh reloadableTexture = (hkh) texture2;
            a((alr) location.getMinecraftLocation(), reloadableTexture);
        } else {
            a((alr) location.getMinecraftLocation(), textureObject);
        }
        if (currentTexture != null) {
            try {
                currentTexture.close();
            } catch (RenderDeviceException | RenderDeviceOutOfMemoryException | IllegalStateException e) {
            }
        }
    }
}
