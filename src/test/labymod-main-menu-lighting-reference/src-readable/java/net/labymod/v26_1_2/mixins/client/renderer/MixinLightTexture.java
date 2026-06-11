package net.labymod.v26_1_2.mixins.client.renderer;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.minecraft.client.renderer.Lightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/MixinLightTexture.class */
@Mixin({Lightmap.class})
public abstract class MixinLightTexture implements TextureViewProvider {
    @Shadow
    public abstract GpuTextureView getTextureView();

    @Override // net.labymod.api.laby3d.util.TextureViewProvider
    public DeviceTextureView getDeviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(getTextureView(), DeviceTextureView.class);
    }
}
