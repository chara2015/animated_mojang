package net.labymod.api.client.gfx.pipeline.renderer.text.layout;

import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderingOptions;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/layout/TextLayoutEngine.class */
public class TextLayoutEngine {
    private final WidthProvider widthProvider;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/layout/TextLayoutEngine$WidthProvider.class */
    @FunctionalInterface
    public interface WidthProvider {
        float getWidth(FormattedTextLayout formattedTextLayout);
    }

    public TextLayoutEngine(WidthProvider widthProvider) {
        this.widthProvider = widthProvider;
    }

    public TextLayoutData layout(TextLayoutSpec spec) {
        int fontFlags = 0;
        int textFlags = spec.flags();
        if (TextRenderingOptions.hasShadow(textFlags)) {
            fontFlags = 0 | 1;
        }
        float x = spec.x() / spec.scale();
        float y = spec.y() / spec.scale();
        if (TextRenderingOptions.isCentered(textFlags)) {
            float width = this.widthProvider.getWidth(spec.text());
            x -= width / 2.0f;
        }
        boolean floatingPointValues = TextRenderingOptions.isFloatingPointValues(textFlags);
        return new TextLayoutData(spec.text(), MathHelper.applyFloatingPointPosition(floatingPointValues, x), MathHelper.applyFloatingPointPosition(floatingPointValues, y), spec.argb(), spec.backgroundArgb(), spec.scale(), fontFlags);
    }
}
