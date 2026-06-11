package net.labymod.v1_8_9.mixins.client.gui;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.v1_8_9.client.gfx.pipeline.renderer.FontRendererAccessor;
import net.labymod.v1_8_9.client.gfx.pipeline.renderer.text.VanillaFontRenderer;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinFontRenderer.class */
@Mixin({avn.class})
public class MixinFontRenderer implements FontRendererAccessor {

    @Shadow
    private boolean k;

    @Shadow
    @Final
    private int[] d;

    @Shadow
    @Final
    private byte[] e;

    @Shadow
    @Final
    private jy g;
    private boolean labyMod$translate;

    @Inject(method = {"onResourceManagerReload"}, at = {@At("TAIL")})
    private void labyMod$reload(bni lvt_1_1_, CallbackInfo ci) {
        if (this == ave.A().k) {
            ((VanillaFontRenderer) Laby.references().minecraftFontRenderer()).reload((ResourceLocation) this.g);
        }
    }

    @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.BEFORE)})
    private void labyMod$translateShadowPush(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> ci) {
        OverlappingTranslator translator = Laby.labyAPI().gfxRenderPipeline().overlappingTranslator();
        if (translator.isTranslated()) {
            this.labyMod$translate = true;
            bfl.E();
            translator.translate(this, VersionedStackProvider.DEFAULT_STACK);
        }
    }

    @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1, shift = At.Shift.AFTER)})
    private void labyMod$translateShadowPop(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> ci) {
        if (this.labyMod$translate) {
            bfl.F();
            this.labyMod$translate = false;
        }
    }

    @Overwrite
    public int a(char character) {
        if (character == 167) {
            return -1;
        }
        if (character == ' ') {
            return 4;
        }
        int charIndex = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".indexOf(character);
        if (character > 0 && charIndex != -1 && !this.k) {
            return this.d[charIndex];
        }
        if (this.e[character] != 0) {
            int widthIndex = this.e[character] & 255;
            int charWidthIndex = widthIndex >>> 4;
            int charWidth = widthIndex & 15;
            return (((charWidth + 1) - charWidthIndex) / 2) + 1;
        }
        return 0;
    }

    @Override // net.labymod.v1_8_9.client.gfx.pipeline.renderer.FontRendererAccessor
    public int[] getCharWidths() {
        return this.d;
    }

    @Override // net.labymod.v1_8_9.client.gfx.pipeline.renderer.FontRendererAccessor
    public byte[] getGlyphWidths() {
        return this.e;
    }
}
