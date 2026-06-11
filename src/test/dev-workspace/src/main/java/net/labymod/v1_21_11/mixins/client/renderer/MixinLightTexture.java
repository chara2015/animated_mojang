package net.labymod.v1_21_11.mixins.client.renderer;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinLightTexture.class */
@Mixin({hoj.class})
public abstract class MixinLightTexture implements TextureViewProvider {
    @Shadow
    public abstract GpuTextureView a();

    @Override // net.labymod.api.laby3d.util.TextureViewProvider
    public DeviceTextureView getDeviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(a(), DeviceTextureView.class);
    }
}
