package net.labymod.v1_21_5.mixins.client.renderer;

import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/renderer/MixinLightTexture.class */
@Mixin({grk.class})
public abstract class MixinLightTexture implements TextureViewProvider {
    @Shadow
    public abstract GpuTexture a();

    @Inject(method = {"turnOnLightLayer"}, at = {@At("TAIL")})
    private void labyMod$turnOnLightLayer(CallbackInfo ci) {
        ShaderTextures.setShaderTexture(2, getDeviceTextureView());
    }

    @Inject(method = {"turnOffLightLayer"}, at = {@At("TAIL")})
    private void labyMod$turnOffLightLayer(CallbackInfo ci) {
        ShaderTextures.clearShaderTexture(2);
    }

    @Override // net.labymod.api.laby3d.util.TextureViewProvider
    public DeviceTextureView getDeviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown((DeviceTexture) CastUtil.requireInstanceOf(a(), DeviceTexture.class));
    }
}
