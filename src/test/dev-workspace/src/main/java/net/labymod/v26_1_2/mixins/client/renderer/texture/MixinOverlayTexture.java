package net.labymod.v26_1_2.mixins.client.renderer.texture;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture;
import net.labymod.v26_1_2.client.resources.texture.NativeGameImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/renderer/texture/MixinOverlayTexture.class */
@Mixin({OverlayTexture.class})
public class MixinOverlayTexture implements GameOverlayTexture {

    @Shadow
    @Final
    private DynamicTexture texture;
    private DynamicOverlayTexture labyMod$dynamicOverlayTexture;
    private NativeImage labyMod$overlayNativeImage;
    private GameImage labyMod$overlayImage;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$getImage(CallbackInfo ci) {
        this.labyMod$overlayNativeImage = this.texture.getPixels();
        this.labyMod$overlayImage = new NativeGameImage(this.labyMod$overlayNativeImage);
        this.labyMod$dynamicOverlayTexture = new DynamicOverlayTexture(this);
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public GameImage image() {
        return this.labyMod$overlayImage;
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public void upload() {
        NativeImage image = this.labyMod$overlayNativeImage;
        GlStateManager._activeTexture(GlConst.GL_TEXTURE1);
        GpuDevice device = RenderSystem.getDevice();
        CommandEncoder encoder = device.createCommandEncoder();
        encoder.writeToTexture(this.texture.getTexture(), image);
        GlStateManager._activeTexture(GlConst.GL_TEXTURE0);
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public DynamicOverlayTexture dynamicTexture() {
        return this.labyMod$dynamicOverlayTexture;
    }
}
