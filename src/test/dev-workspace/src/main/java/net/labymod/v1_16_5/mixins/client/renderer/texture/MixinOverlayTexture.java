package net.labymod.v1_16_5.mixins.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.gfx.texture.overlay.DynamicOverlayTexture;
import net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture;
import net.labymod.v1_16_5.client.resources.texture.NativeGameImage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/texture/MixinOverlayTexture.class */
@Mixin({ejw.class})
public class MixinOverlayTexture implements GameOverlayTexture {

    @Shadow
    @Final
    private ejs b;
    private DynamicOverlayTexture labyMod$dynamicOverlayTexture;
    private det labyMod$overlayNativeImage;
    private GameImage labyMod$overlayImage;

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$getImage(CallbackInfo ci) {
        this.labyMod$overlayNativeImage = this.b.e();
        this.labyMod$overlayImage = new NativeGameImage(this.labyMod$overlayNativeImage);
        this.labyMod$dynamicOverlayTexture = new DynamicOverlayTexture(this);
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public GameImage image() {
        return this.labyMod$overlayImage;
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public void upload() {
        det image = this.labyMod$overlayNativeImage;
        RenderSystem.activeTexture(GlConst.GL_TEXTURE1);
        this.b.d();
        RenderSystem.matrixMode(GlConst.GL_TEXTURE);
        RenderSystem.loadIdentity();
        RenderSystem.scalef(0.6666667f, 0.6666667f, 0.6666667f);
        RenderSystem.matrixMode(GlConst.GL_MODELVIEW);
        this.b.d();
        image.a(0, 0, 0, 0, 0, image.a(), image.b(), false, true, false, false);
        RenderSystem.activeTexture(GlConst.GL_TEXTURE0);
    }

    @Override // net.labymod.core.client.gfx.texture.overlay.GameOverlayTexture
    public DynamicOverlayTexture dynamicTexture() {
        return this.labyMod$dynamicOverlayTexture;
    }
}
