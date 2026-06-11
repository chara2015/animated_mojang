package net.labymod.v1_12_2.mixins.client.renderer.skipcolors;

import java.nio.ByteOrder;
import java.nio.IntBuffer;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/skipcolors/MixinBufferBuilderSkipColors.class */
@Mixin({buk.class})
public abstract class MixinBufferBuilderSkipColors {

    @Shadow
    private IntBuffer c;

    @Shadow
    protected abstract int c(int i);

    @Inject(method = {"putColor"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$skipColors(int color, int index, CallbackInfo ci) {
        if (!GlColorAlphaModifier.isModifiedAlpha()) {
            return;
        }
        ci.cancel();
        int colorIndex = c(index);
        ColorFormat colorFormat = ColorFormat.ARGB32;
        int red = colorFormat.red(color);
        int green = colorFormat.green(color);
        int blue = colorFormat.blue(color);
        int alpha = colorFormat.alpha(color);
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.c.put(colorIndex, ColorFormat.ABGR32.pack(red, green, blue, alpha));
        } else {
            this.c.put(colorIndex, (red << 24) | (green << 16) | (blue << 8) | alpha);
        }
    }
}
