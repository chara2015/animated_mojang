package net.labymod.v1_21_11.mixins.client.renderer;

import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinOverlayTexture.class */
@Mixin({OverlayTexture.class})
public class MixinOverlayTexture implements TextureViewProvider {

    @Shadow
    @Final
    private DynamicTexture texture;

    public DeviceTextureView getDeviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown((DeviceTexture) CastUtil.requireInstanceOf(this.texture.getTexture(), DeviceTexture.class));
    }
}

