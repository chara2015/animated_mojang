package net.labymod.v1_21_11.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.RenderDeviceOutOfMemoryException;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({ilr.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<amo, ikz> c;

    @Shadow
    public abstract void a(amo amoVar, ikz ikzVar);

    @Shadow
    public abstract void a(amo amoVar, ilh ilhVar);

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public boolean hasResource(ResourceLocation location) {
        return this.c.get((amo) location) != null;
    }

    @Override // net.labymod.core.client.resources.texture.GameTextureManager
    public void registerAndRelease(ResourceLocation location, Object texture) {
        Object texture2 = MinecraftUtil.toMinecraft(texture);
        ikz textureObject = (ikz) CastUtil.requireInstanceOf(texture2, ikz.class);
        ikz currentTexture = this.c.get(location.getMinecraftLocation());
        if (texture2 instanceof ilh) {
            ilh reloadableTexture = (ilh) texture2;
            a((amo) location.getMinecraftLocation(), reloadableTexture);
        } else {
            a((amo) location.getMinecraftLocation(), textureObject);
        }
        if (currentTexture != null) {
            try {
                currentTexture.close();
            } catch (RenderDeviceException | RenderDeviceOutOfMemoryException | IllegalStateException e) {
            }
        }
    }
}
