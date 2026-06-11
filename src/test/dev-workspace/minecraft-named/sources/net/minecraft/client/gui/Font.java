package net.minecraft.client.gui;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.font.EmptyArea;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EffectGlyph;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringDecomposer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font.class */
public class Font {
    private static final float EFFECT_DEPTH = 0.01f;
    private static final float OVER_EFFECT_DEPTH = 0.01f;
    private static final float UNDER_EFFECT_DEPTH = -0.01f;
    public static final float SHADOW_DEPTH = 0.03f;
    final Provider provider;
    public final int lineHeight = 9;
    private final RandomSource random = RandomSource.create();
    private final StringSplitter splitter = new StringSplitter(($$0, $$1) -> {
        return getGlyphSource($$1.getFont()).getGlyph($$0).info().getAdvance($$1.isBold());
    });

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font$DisplayMode.class */
    public enum DisplayMode {
        NORMAL,
        SEE_THROUGH,
        POLYGON_OFFSET
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font$PreparedText.class */
    public interface PreparedText {
        void visit(GlyphVisitor glyphVisitor);

        ScreenRectangle bounds();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font$Provider.class */
    public interface Provider {
        GlyphSource glyphs(FontDescription fontDescription);

        EffectGlyph effect();
    }

    public Font(Provider $$0) {
        this.provider = $$0;
    }

    private GlyphSource getGlyphSource(FontDescription $$0) {
        return this.provider.glyphs($$0);
    }

    public String bidirectionalShaping(String $$0) {
        try {
            Bidi $$1 = new Bidi(new ArabicShaping(8).shape($$0), 127);
            $$1.setReorderingMode(0);
            return $$1.writeReordered(2);
        } catch (ArabicShapingException e) {
            return $$0;
        }
    }

    public void drawInBatch(String $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
        PreparedText $$10 = prepareText($$0, $$1, $$2, $$3, $$4, $$8);
        $$10.visit(GlyphVisitor.forMultiBufferSource($$6, $$5, $$7, $$9));
    }

    public void drawInBatch(Component $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
        PreparedText $$10 = prepareText($$0.getVisualOrderText(), $$1, $$2, $$3, $$4, false, $$8);
        $$10.visit(GlyphVisitor.forMultiBufferSource($$6, $$5, $$7, $$9));
    }

    public void drawInBatch(FormattedCharSequence $$0, float $$1, float $$2, int $$3, boolean $$4, Matrix4f $$5, MultiBufferSource $$6, DisplayMode $$7, int $$8, int $$9) {
        PreparedText $$10 = prepareText($$0, $$1, $$2, $$3, $$4, false, $$8);
        $$10.visit(GlyphVisitor.forMultiBufferSource($$6, $$5, $$7, $$9));
    }

    public void drawInBatch8xOutline(FormattedCharSequence $$0, float $$1, float $$2, int $$3, int $$4, Matrix4f $$5, MultiBufferSource $$6, int $$7) {
        PreparedTextBuilder $$8 = new PreparedTextBuilder(this, 0.0f, 0.0f, $$4, false, false);
        for (int $$9 = -1; $$9 <= 1; $$9++) {
            for (int $$10 = -1; $$10 <= 1; $$10++) {
                if ($$9 != 0 || $$10 != 0) {
                    float[] $$11 = {$$1};
                    int $$12 = $$9;
                    int $$13 = $$10;
                    $$0.accept(($$62, $$72, $$82) -> {
                        boolean $$92 = $$72.isBold();
                        BakedGlyph $$102 = getGlyph($$82, $$72);
                        $$8.x = $$11[0] + ($$12 * $$102.info().getShadowOffset());
                        $$8.y = $$2 + ($$13 * $$102.info().getShadowOffset());
                        $$11[0] = $$11[0] + $$102.info().getAdvance($$92);
                        return $$8.accept($$62, $$72.withColor($$4), $$102);
                    });
                }
            }
        }
        GlyphVisitor $$14 = GlyphVisitor.forMultiBufferSource($$6, $$5, DisplayMode.NORMAL, $$7);
        for (TextRenderable.Styled $$15 : $$8.glyphs) {
            $$14.acceptGlyph($$15);
        }
        PreparedTextBuilder $$16 = new PreparedTextBuilder(this, $$1, $$2, $$3, false, true);
        $$0.accept($$16);
        $$16.visit(GlyphVisitor.forMultiBufferSource($$6, $$5, DisplayMode.POLYGON_OFFSET, $$7));
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font$PreparedTextBuilder.class */
    class PreparedTextBuilder implements FormattedCharSink, PreparedText {
        private final boolean drawShadow;
        private final int color;
        private final int backgroundColor;
        private final boolean includeEmpty;
        float x;
        float y;
        private float left;
        private float top;
        private float right;
        private float bottom;
        private float backgroundLeft;
        private float backgroundTop;
        private float backgroundRight;
        private float backgroundBottom;
        final List<TextRenderable.Styled> glyphs;
        private List<TextRenderable> effects;
        private List<EmptyArea> emptyAreas;

        public PreparedTextBuilder(Font font, float $$0, float $$1, int $$2, boolean $$3, boolean $$4) {
            this($$0, $$1, $$2, 0, $$3, $$4);
        }

        public PreparedTextBuilder(float $$0, float $$1, int $$2, int $$3, boolean $$4, boolean $$5) {
            this.left = Float.MAX_VALUE;
            this.top = Float.MAX_VALUE;
            this.right = -3.4028235E38f;
            this.bottom = -3.4028235E38f;
            this.backgroundLeft = Float.MAX_VALUE;
            this.backgroundTop = Float.MAX_VALUE;
            this.backgroundRight = -3.4028235E38f;
            this.backgroundBottom = -3.4028235E38f;
            this.glyphs = new ArrayList();
            this.x = $$0;
            this.y = $$1;
            this.drawShadow = $$4;
            this.color = $$2;
            this.backgroundColor = $$3;
            this.includeEmpty = $$5;
            markBackground($$0, $$1, 0.0f);
        }

        private void markSize(float $$0, float $$1, float $$2, float $$3) {
            this.left = Math.min(this.left, $$0);
            this.top = Math.min(this.top, $$1);
            this.right = Math.max(this.right, $$2);
            this.bottom = Math.max(this.bottom, $$3);
        }

        private void markBackground(float $$0, float $$1, float $$2) {
            if (ARGB.alpha(this.backgroundColor) == 0) {
                return;
            }
            this.backgroundLeft = Math.min(this.backgroundLeft, $$0 - 1.0f);
            this.backgroundTop = Math.min(this.backgroundTop, $$1 - 1.0f);
            this.backgroundRight = Math.max(this.backgroundRight, $$0 + $$2);
            this.backgroundBottom = Math.max(this.backgroundBottom, $$1 + 9.0f);
            markSize(this.backgroundLeft, this.backgroundTop, this.backgroundRight, this.backgroundBottom);
        }

        private void addGlyph(TextRenderable.Styled $$0) {
            this.glyphs.add($$0);
            markSize($$0.left(), $$0.top(), $$0.right(), $$0.bottom());
        }

        private void addEffect(TextRenderable $$0) {
            if (this.effects == null) {
                this.effects = new ArrayList();
            }
            this.effects.add($$0);
            markSize($$0.left(), $$0.top(), $$0.right(), $$0.bottom());
        }

        private void addEmptyGlyph(EmptyArea $$0) {
            if (this.emptyAreas == null) {
                this.emptyAreas = new ArrayList();
            }
            this.emptyAreas.add($$0);
        }

        @Override // net.minecraft.util.FormattedCharSink
        public boolean accept(int $$0, Style $$1, int $$2) {
            BakedGlyph $$3 = Font.this.getGlyph($$2, $$1);
            return accept($$0, $$1, $$3);
        }

        public boolean accept(int $$0, Style $$1, BakedGlyph $$2) {
            GlyphInfo $$3 = $$2.info();
            boolean $$4 = $$1.isBold();
            TextColor $$5 = $$1.getColor();
            int $$6 = getTextColor($$5);
            int $$7 = getShadowColor($$1, $$6);
            float $$8 = $$3.getAdvance($$4);
            float $$9 = $$0 == 0 ? this.x - 1.0f : this.x;
            float $$10 = $$3.getShadowOffset();
            float $$11 = $$4 ? $$3.getBoldOffset() : 0.0f;
            TextRenderable.Styled $$12 = $$2.createGlyph(this.x, this.y, $$6, $$7, $$1, $$11, $$10);
            if ($$12 != null) {
                addGlyph($$12);
            } else if (this.includeEmpty) {
                addEmptyGlyph(new EmptyArea(this.x, this.y, $$8, 7.0f, 9.0f, $$1));
            }
            markBackground(this.x, this.y, $$8);
            if ($$1.isStrikethrough()) {
                addEffect(Font.this.provider.effect().createEffect($$9, (this.y + 4.5f) - 1.0f, this.x + $$8, this.y + 4.5f, 0.01f, $$6, $$7, $$10));
            }
            if ($$1.isUnderlined()) {
                addEffect(Font.this.provider.effect().createEffect($$9, (this.y + 9.0f) - 1.0f, this.x + $$8, this.y + 9.0f, 0.01f, $$6, $$7, $$10));
            }
            this.x += $$8;
            return true;
        }

        @Override // net.minecraft.client.gui.Font.PreparedText
        public void visit(GlyphVisitor $$0) {
            if (ARGB.alpha(this.backgroundColor) != 0) {
                $$0.acceptEffect(Font.this.provider.effect().createEffect(this.backgroundLeft, this.backgroundTop, this.backgroundRight, this.backgroundBottom, Font.UNDER_EFFECT_DEPTH, this.backgroundColor, 0, 0.0f));
            }
            for (TextRenderable.Styled $$1 : this.glyphs) {
                $$0.acceptGlyph($$1);
            }
            if (this.effects != null) {
                for (TextRenderable $$2 : this.effects) {
                    $$0.acceptEffect($$2);
                }
            }
            if (this.emptyAreas != null) {
                for (EmptyArea $$3 : this.emptyAreas) {
                    $$0.acceptEmptyArea($$3);
                }
            }
        }

        private int getTextColor(TextColor $$0) {
            if ($$0 != null) {
                int $$1 = ARGB.alpha(this.color);
                int $$2 = $$0.getValue();
                return ARGB.color($$1, $$2);
            }
            return this.color;
        }

        private int getShadowColor(Style $$0, int $$1) {
            Integer $$2 = $$0.getShadowColor();
            if ($$2 != null) {
                float $$3 = ARGB.alphaFloat($$1);
                float $$4 = ARGB.alphaFloat($$2.intValue());
                if ($$3 != 1.0f) {
                    return ARGB.color(ARGB.as8BitChannel($$3 * $$4), $$2.intValue());
                }
                return $$2.intValue();
            }
            if (this.drawShadow) {
                return ARGB.scaleRGB($$1, 0.25f);
            }
            return 0;
        }

        @Override // net.minecraft.client.gui.Font.PreparedText
        public ScreenRectangle bounds() {
            if (this.left >= this.right || this.top >= this.bottom) {
                return null;
            }
            int $$0 = Mth.floor(this.left);
            int $$1 = Mth.floor(this.top);
            int $$2 = Mth.ceil(this.right);
            int $$3 = Mth.ceil(this.bottom);
            return new ScreenRectangle($$0, $$1, $$2 - $$0, $$3 - $$1);
        }
    }

    BakedGlyph getGlyph(int $$0, Style $$1) {
        GlyphSource $$2 = getGlyphSource($$1.getFont());
        BakedGlyph $$3 = $$2.getGlyph($$0);
        if ($$1.isObfuscated() && $$0 != 32) {
            int $$4 = Mth.ceil($$3.info().getAdvance(false));
            $$3 = $$2.getRandomGlyph(this.random, $$4);
        }
        return $$3;
    }

    public PreparedText prepareText(String $$0, float $$1, float $$2, int $$3, boolean $$4, int $$5) {
        if (isBidirectional()) {
            $$0 = bidirectionalShaping($$0);
        }
        PreparedTextBuilder $$6 = new PreparedTextBuilder($$1, $$2, $$3, $$5, $$4, false);
        StringDecomposer.iterateFormatted($$0, Style.EMPTY, $$6);
        return $$6;
    }

    public PreparedText prepareText(FormattedCharSequence $$0, float $$1, float $$2, int $$3, boolean $$4, boolean $$5, int $$6) {
        PreparedTextBuilder $$7 = new PreparedTextBuilder($$1, $$2, $$3, $$6, $$4, $$5);
        $$0.accept($$7);
        return $$7;
    }

    public int width(String $$0) {
        return Mth.ceil(this.splitter.stringWidth($$0));
    }

    public int width(FormattedText $$0) {
        return Mth.ceil(this.splitter.stringWidth($$0));
    }

    public int width(FormattedCharSequence $$0) {
        return Mth.ceil(this.splitter.stringWidth($$0));
    }

    public String plainSubstrByWidth(String $$0, int $$1, boolean $$2) {
        return $$2 ? this.splitter.plainTailByWidth($$0, $$1, Style.EMPTY) : this.splitter.plainHeadByWidth($$0, $$1, Style.EMPTY);
    }

    public String plainSubstrByWidth(String $$0, int $$1) {
        return this.splitter.plainHeadByWidth($$0, $$1, Style.EMPTY);
    }

    public FormattedText substrByWidth(FormattedText $$0, int $$1) {
        return this.splitter.headByWidth($$0, $$1, Style.EMPTY);
    }

    public int wordWrapHeight(FormattedText $$0, int $$1) {
        return 9 * this.splitter.splitLines($$0, $$1, Style.EMPTY).size();
    }

    public List<FormattedCharSequence> split(FormattedText $$0, int $$1) {
        return Language.getInstance().getVisualOrder(this.splitter.splitLines($$0, $$1, Style.EMPTY));
    }

    public List<FormattedText> splitIgnoringLanguage(FormattedText $$0, int $$1) {
        return this.splitter.splitLines($$0, $$1, Style.EMPTY);
    }

    public boolean isBidirectional() {
        return Language.getInstance().isDefaultRightToLeft();
    }

    public StringSplitter getSplitter() {
        return this.splitter;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/Font$GlyphVisitor.class */
    public interface GlyphVisitor {
        static GlyphVisitor forMultiBufferSource(final MultiBufferSource $$0, final Matrix4f $$1, final DisplayMode $$2, final int $$3) {
            return new GlyphVisitor() { // from class: net.minecraft.client.gui.Font.GlyphVisitor.1
                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptGlyph(TextRenderable.Styled $$02) {
                    render($$02);
                }

                @Override // net.minecraft.client.gui.Font.GlyphVisitor
                public void acceptEffect(TextRenderable $$02) {
                    render($$02);
                }

                private void render(TextRenderable $$02) {
                    VertexConsumer $$12 = $$0.getBuffer($$02.renderType($$2));
                    $$02.render($$1, $$12, $$3, false);
                }
            };
        }

        default void acceptGlyph(TextRenderable.Styled $$0) {
        }

        default void acceptEffect(TextRenderable $$0) {
        }

        default void acceptEmptyArea(EmptyArea $$0) {
        }
    }
}
