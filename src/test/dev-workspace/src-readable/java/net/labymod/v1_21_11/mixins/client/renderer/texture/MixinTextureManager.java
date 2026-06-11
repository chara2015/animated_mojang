package net.labymod.v1_21_11.mixins.client.renderer.texture;

import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.RenderDeviceOutOfMemoryException;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.ReloadableTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/texture/MixinTextureManager.class */
@Mixin({TextureManager.class})
public abstract class MixinTextureManager implements GameTextureManager {

    @Shadow
    @Mutable
    @Final
    private Map<Identifier, AbstractTexture> byPath;

    @Shadow
    public abstract void registerAndLoad(Identifier identifier, AbstractTexture abstractTexture);

    @Shadow
    public abstract void registerAndLoad(Identifier identifier, ReloadableTexture reloadableTexture);

    public boolean hasResource(ResourceLocation location) {
        return this.byPath.get((Identifier) location) != null;
    }

    public void registerAndRelease(ResourceLocation location, Object texture) {
        Object texture2 = MinecraftUtil.toMinecraft(texture);
        AbstractTexture textureObject = (AbstractTexture) CastUtil.requireInstanceOf(texture2, AbstractTexture.class);
        AbstractTexture currentTexture = this.byPath.get(location.getMinecraftLocation());
        if (texture2 instanceof ReloadableTexture) {
            ReloadableTexture reloadableTexture = (ReloadableTexture) texture2;
            registerAndLoad((Identifier) location.getMinecraftLocation(), reloadableTexture);
        } else {
            registerAndLoad((Identifier) location.getMinecraftLocation(), textureObject);
        }
        if (currentTexture != null) {
            try {
                currentTexture.close();
            } catch (RenderDeviceException | RenderDeviceOutOfMemoryException | IllegalStateException e) {
            }
        }
    }
}

