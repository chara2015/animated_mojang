package net.labymod.v1_12_2.mixins.client.renderer.texture;

import java.awt.image.BufferedImage;
import javax.annotation.Nullable;
import net.labymod.v1_12_2.client.resources.texture.HttpTextureAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/texture/MixinHttpTexture.class */
@Mixin({cdh.class})
public class MixinHttpTexture implements HttpTextureAccessor {

    @Shadow
    @Nullable
    private BufferedImage l;

    @Override // net.labymod.v1_12_2.client.resources.texture.HttpTextureAccessor
    public BufferedImage getImage() {
        return this.l;
    }
}
