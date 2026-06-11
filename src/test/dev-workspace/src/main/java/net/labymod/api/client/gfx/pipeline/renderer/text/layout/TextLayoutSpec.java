package net.labymod.api.client.gfx.pipeline.renderer.text.layout;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.render.font.SizedFont;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/layout/TextLayoutSpec.class */
public final class TextLayoutSpec {
    private final FormattedTextLayout text;
    private final float x;
    private final float y;
    private final int argb;
    private final int backgroundArgb;
    private final float scale;
    private final int flags;

    private TextLayoutSpec(FormattedTextLayout text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.argb = argb;
        this.backgroundArgb = backgroundArgb;
        this.scale = scale;
        this.flags = flags;
    }

    public static Builder builder(String text, float x, float y, int argb) {
        return Builder.create(text, Style.EMPTY, x, y, argb);
    }

    public static Builder builder(String text, Style style, float x, float y, int argb) {
        return Builder.create(text, style, x, y, argb);
    }

    public static Builder builder(Component component, float x, float y, int argb) {
        return Builder.create(component, x, y, argb);
    }

    public static Builder builder(FormattedTextLayout text, float x, float y, int argb) {
        return Builder.create(text, x, y, argb);
    }

    public FormattedTextLayout text() {
        return this.text;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public int argb() {
        return this.argb;
    }

    public int backgroundArgb() {
        return this.backgroundArgb;
    }

    public float scale() {
        return this.scale;
    }

    public int flags() {
        return this.flags;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/layout/TextLayoutSpec$Builder.class */
    public static final class Builder {
        private static final FormattedTextLayout.Factory TEXT_LAYOUT_FACTORY = Laby.references().formattedTextLayoutFactory();
        private final FormattedTextLayout text;
        private final float x;
        private final float y;
        private final int argb;
        private int backgroundArgb = 0;
        private float scale = 1.0f;
        private int flags = 0;

        private Builder(FormattedTextLayout text, float x, float y, int argb) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.argb = argb;
        }

        static Builder create(String text, Style style, float x, float y, int argb) {
            return create(TEXT_LAYOUT_FACTORY.create(text, style), x, y, argb);
        }

        static Builder create(Component component, float x, float y, int argb) {
            return create(TEXT_LAYOUT_FACTORY.create(component), x, y, argb);
        }

        static Builder create(FormattedTextLayout text, float x, float y, int argb) {
            return new Builder(text, x, y, argb);
        }

        public Builder setBackgroundArgb(int backgroundArgb) {
            this.backgroundArgb = backgroundArgb;
            return this;
        }

        public Builder setScale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder setScale(SizedFont font) {
            this.scale = font.getSize();
            return this;
        }

        public Builder addFlag(int flag) {
            this.flags |= flag;
            return this;
        }

        public Builder removeFlag(int flag) {
            this.flags &= flag ^ (-1);
            return this;
        }

        public Builder setFlags(int flags) {
            this.flags = flags;
            return this;
        }

        public TextLayoutSpec build() {
            return new TextLayoutSpec(this.text, this.x, this.y, this.argb, this.backgroundArgb, this.scale, this.flags);
        }
    }
}
