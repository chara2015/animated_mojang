package net.labymod.v1_21_11.mixins.client.renderer;

import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.minecraft.client.renderer.LightTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/MixinLightTexture.class */
@Mixin({LightTexture.class})
public abstract class MixinLightTexture implements TextureViewProvider {
    @Shadow
    public abstract GpuTextureView a();

    public DeviceTextureView getDeviceTextureView() {
        return (DeviceTextureView) CastUtil.requireInstanceOf(a(), DeviceTextureView.class);
    }
}
