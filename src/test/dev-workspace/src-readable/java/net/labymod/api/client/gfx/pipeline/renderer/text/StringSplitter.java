package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.render.font.text.StringIterator;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/StringSplitter.class */
public class StringSplitter {
    private final FormattedTextLayout.Factory formattedTextLayoutFactory = Laby.references().formattedTextLayoutFactory();
    private final WidthProvider widthProvider;
    private final WidthCalculator widthCalculator;

    public StringSplitter(WidthProvider widthProvider) {
        this.widthProvider = widthProvider;
        this.widthCalculator = new WidthCalculator(widthProvider);
    }

    public float getTextWidth(@Nullable String text) {
        if (text == null) {
            return 0.0f;
        }
        StringIterator.iterateFormatted(text, Style.EMPTY, false, this.widthCalculator);
        return this.widthCalculator.getWidth();
    }

    public float getTextWidth(Component text) {
        FormattedTextLayout layout = this.formattedTextLayoutFactory.create(text);
        return getTextWidth(layout);
    }

    public float getTextWidth(FormattedTextLayout text) {
        text.format(this.widthCalculator);
        return this.widthCalculator.getWidth();
    }

    public String plainHeadByWidth(String text, float maxWidth, Style style) {
        return text.substring(0, plainIndexAtWidth(text, maxWidth, style));
    }

    public String plainTailByWidth(String text, float maxWidth, Style style) {
        WidthLimitedProcessor processor = new WidthLimitedProcessor(this.widthProvider, maxWidth, true);
        StringIterator.iterateBackwards(text, style, processor);
        return text.substring(processor.getPosition());
    }

    public int plainIndexAtWidth(String text, float maxWidth, Style style) {
        WidthLimitedProcessor processor = new WidthLimitedProcessor(this.widthProvider, maxWidth, false);
        StringIterator.iterateFormatted(text, style, false, processor);
        return processor.getPosition();
    }

    public WidthProvider getWidthProvider() {
        return this.widthProvider;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/StringSplitter$WidthCalculator.class */
    static class WidthCalculator implements GlyphProcessor {
        private final WidthProvider widthProvider;
        private float width;

        public WidthCalculator(WidthProvider widthProvider) {
            this.widthProvider = widthProvider;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public boolean accept(int position, Style style, int codepoint) {
            this.width += this.widthProvider.getWidth(codepoint, style);
            return true;
        }

        public float getWidth() {
            float width = this.width;
            this.width = 0.0f;
            return width;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/StringSplitter$WidthLimitedProcessor.class */
    static class WidthLimitedProcessor implements GlyphProcessor {
        private final WidthProvider widthProvider;
        private final boolean reversed;
        private float maxWidth;
        private float width;
        private int position;

        public WidthLimitedProcessor(WidthProvider widthProvider, float maxWidth, boolean reversed) {
            this.widthProvider = widthProvider;
            this.maxWidth = maxWidth;
            this.reversed = reversed;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public boolean accept(int position, Style style, int codepoint) {
            if (this.reversed) {
                return tailToHead(position, style, codepoint);
            }
            return headToTail(position, style, codepoint);
        }

        public int getPosition() {
            return this.position;
        }

        private boolean headToTail(int position, Style style, int codepoint) {
            this.maxWidth -= this.widthProvider.getWidth(codepoint, style);
            if (this.maxWidth >= 0.0f) {
                this.position = position + Character.charCount(codepoint);
                return true;
            }
            return false;
        }

        private boolean tailToHead(int position, Style style, int codepoint) {
            this.width += this.widthProvider.getWidth(codepoint, style);
            if (this.width > this.maxWidth) {
                return false;
            }
            this.position = position;
            return true;
        }
    }
}
