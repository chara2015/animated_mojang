package net.labymod.v1_8_9.mixins.client.renderer;

import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.laby3d.util.TextureViewProvider;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/MixinLightTexture.class */
@Mixin({bfk.class})
public abstract class MixinLightTexture implements TextureViewProvider {

    @Shadow
    @Final
    private jy I;

    @Shadow
    @Final
    private blz G;

    @Inject(method = {"enableLightmap"}, at = {@At("TAIL")})
    private void labyMod$turnOnLightLayer(CallbackInfo ci) {
        ShaderTextures.setShaderTexture(1, this.I);
    }

    @Inject(method = {"disableLightmap"}, at = {@At("TAIL")})
    private void labyMod$turnOffLightLayer(CallbackInfo ci) {
        ShaderTextures.clearShaderTexture(1);
    }

    @Override // net.labymod.api.laby3d.util.TextureViewProvider
    public DeviceTextureView getDeviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(this.G.b());
    }
}
