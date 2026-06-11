package net.labymod.v1_12_2.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Locale;
import java.util.function.Supplier;
import javax.inject.Singleton;
import net.labymod.api.Namespaces;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.v1_12_2.client.gfx.pipeline.renderer.FontRendererAccessor;
import net.labymod.v1_12_2.client.gfx.pipeline.renderer.OptiFineFontAccessor;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage.class */
@Singleton
@Implements(VanillaGlyphStorage.class)
public class VersionedVanillaGlyphStorage extends VanillaGlyphStorage {
    private static final int UNICODE_PAGES = 256;
    private static final String GLYPH_LOOKUP = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000";
    private static final String UNICODE_PAGE_PATH = "textures/font/unicode_page_%02x.png";
    private static final float DEFAULT_GLYPH_HEIGHT = 7.99f;
    private static final float DEFAULT_BOLD_OFFSET = 1.0f;
    private static final float DEFAULT_BOLD_OFFSET_UNICODE = 0.5f;
    private static final float DEFAULT_UNICODE_GLYPH_HEIGHT = 15.98f;
    private final ResourceLocation[] unicodePages;
    private final Supplier<bip> fontRenderer;

    public VersionedVanillaGlyphStorage() {
        super(x$0 -> {
            return new BakedGlyph[x$0];
        }, x$02 -> {
            return new BakedGlyph[x$02];
        });
        this.unicodePages = new ResourceLocation[256];
        this.fontRenderer = () -> {
            return bib.z().k;
        };
    }

    public void reload(ResourceLocation defaultFontLocation) {
        clear();
        FontRendererAccessor accessor = (bip) this.fontRenderer.get();
        OptiFineFontAccessor ofAccessor = null;
        float boldOffset = 1.0f;
        if (accessor instanceof OptiFineFontAccessor) {
            OptiFineFontAccessor _ofAccessor = (OptiFineFontAccessor) accessor;
            ofAccessor = _ofAccessor;
            boldOffset = _ofAccessor.optiFine$getBoldOffset();
        }
        FontContext fontContext = new FontContext(accessor, ofAccessor, boldOffset);
        for (int codepoint = 0; codepoint < 65536; codepoint++) {
            int glyphIndex = GLYPH_LOOKUP.indexOf(codepoint);
            if (glyphIndex != -1) {
                prepareDefaultGlyph(defaultFontLocation, fontContext, codepoint, glyphIndex);
            } else {
                byte b = accessor.getGlyphWidths()[codepoint];
                if (b != 0) {
                    ResourceLocation unicodePage = getUnicodePage(codepoint);
                    prepareUnicodeGlyph(unicodePage, codepoint, b);
                }
            }
        }
        float width = fontContext.getSpaceWidth();
        setGlyph(32, new VanillaBakedGlyph(true, defaultFontLocation, Rectangle.absolute(0.0f, 0.0f, width, DEFAULT_GLYPH_HEIGHT), 0.0f, 0.0f, 0.0f, 0.0f, new VanillaGlyphDescription(width, boldOffset)));
        setEffectGlyph(new VanillaBakedGlyph(false, defaultFontLocation, Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f), 0.0f, 0.0f, 0.0f, 0.0f, GlyphDescription.simple(0.0f)));
    }

    private ResourceLocation getUnicodePage(int codepoint) {
        int page = codepoint / 256;
        ResourceLocation unicodePage = this.unicodePages[page];
        if (unicodePage == null) {
            unicodePage = ResourceLocation.create(Namespaces.MINECRAFT, String.format(Locale.ROOT, UNICODE_PAGE_PATH, Integer.valueOf(page)));
            this.unicodePages[page] = unicodePage;
        }
        return unicodePage;
    }

    private void prepareDefaultGlyph(ResourceLocation defaultFontLocation, FontContext fontContext, int index, int glyphIndex) {
        int textureX = (glyphIndex % 16) * 8;
        int textureY = (glyphIndex / 16) * 8;
        float width = fontContext.getCharWidth(glyphIndex);
        float minU = textureX / 128.0f;
        float minV = textureY / 128.0f;
        float maxU = ((textureX + DEFAULT_GLYPH_HEIGHT) - DEFAULT_BOLD_OFFSET) / 128.0f;
        float maxV = (textureY + DEFAULT_GLYPH_HEIGHT) / 128.0f;
        MutableRectangle bounds = Rectangle.absolute(0.0f, 0.0f, 6.99f, DEFAULT_GLYPH_HEIGHT);
        setGlyph(index, new VanillaBakedGlyph(defaultFontLocation, bounds, minU, minV, maxU, maxV, new VanillaGlyphDescription(width, fontContext.boldOffset())));
    }

    private void prepareUnicodeGlyph(ResourceLocation unicodePage, int codepoint, int glyphWidth) {
        float leftBearing = glyphWidth >>> 4;
        float rightBearing = (glyphWidth & 15) + 1;
        float textureX = ((codepoint % 16) * 16) + leftBearing;
        float textureY = ((codepoint & 255) / 16) * 16;
        float charWidth = rightBearing - leftBearing;
        float effectiveCharWidth = charWidth - 0.02f;
        float minU = textureX / 256.0f;
        float minV = textureY / 256.0f;
        float maxU = (textureX + effectiveCharWidth) / 256.0f;
        float maxV = (textureY + DEFAULT_UNICODE_GLYPH_HEIGHT) / 256.0f;
        MutableRectangle bounds = Rectangle.absolute(0.0f, 0.0f, effectiveCharWidth / 2.0f, DEFAULT_GLYPH_HEIGHT);
        setGlyph(codepoint, new VanillaBakedGlyph(unicodePage, bounds, minU, minV, maxU, maxV, new VanillaGlyphDescription((charWidth / 2.0f) + DEFAULT_BOLD_OFFSET, 0.5f)));
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage
    protected BakedGlyph createGlyph(int codepoint) {
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext.class */
    static final class FontContext extends Record {
        private final FontRendererAccessor accessor;

        @Nullable
        private final OptiFineFontAccessor ofAccessor;
        private final float boldOffset;

        FontContext(FontRendererAccessor accessor, @Nullable OptiFineFontAccessor ofAccessor, float boldOffset) {
            this.accessor = accessor;
            this.ofAccessor = ofAccessor;
            this.boldOffset = boldOffset;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FontContext.class), FontContext.class, "accessor;ofAccessor;boldOffset", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->accessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/FontRendererAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->ofAccessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/OptiFineFontAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->boldOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FontContext.class), FontContext.class, "accessor;ofAccessor;boldOffset", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->accessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/FontRendererAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->ofAccessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/OptiFineFontAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->boldOffset:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FontContext.class, Object.class), FontContext.class, "accessor;ofAccessor;boldOffset", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->accessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/FontRendererAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->ofAccessor:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/OptiFineFontAccessor;", "FIELD:Lnet/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$FontContext;->boldOffset:F").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public FontRendererAccessor accessor() {
            return this.accessor;
        }

        @Nullable
        public OptiFineFontAccessor ofAccessor() {
            return this.ofAccessor;
        }

        public float boldOffset() {
            return this.boldOffset;
        }

        float getCharWidth(int glyphIndex) {
            if (this.ofAccessor != null) {
                return this.ofAccessor.optiFine$getCharWidths()[glyphIndex];
            }
            return this.accessor.getCharWidths()[glyphIndex];
        }

        float getSpaceWidth() {
            if (this.ofAccessor != null) {
                return this.ofAccessor.optiFine$getCharWidths()[32];
            }
            return 4.0f;
        }
    }
}
