package net.labymod.v1_20_1.mixins.client.renderer;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/MixinOverlayTexture.class */
@Mixin({fum.class})
public class MixinOverlayTexture implements TextureViewProvider {

    @Shadow
    @Final
    private fui f;

    @Inject(method = {"setupOverlayColor"}, at = {@At("TAIL")})
    private void labyMod$setShaderTexture(CallbackInfo ci) {
        ShaderTextures.setShaderTexture(1, getDeviceTextureView());
    }

    @Inject(method = {"teardownOverlayColor"}, at = {@At("TAIL")})
    private void labyMod$clearShaderTexture(CallbackInfo ci) {
        ShaderTextures.clearShaderTexture(1);
    }

    @Override // net.labymod.api.laby3d.util.TextureViewProvider
    public DeviceTextureView getDeviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(this.f.a());
    }
}
