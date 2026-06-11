package net.labymod.api.client.gui.screen.widget.util;

import net.labymod.api.Laby;
import net.labymod.api.client.render.RenderPipeline;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/util/WidgetMeta.class */
public class WidgetMeta {
    private final AlphaStateModifier alphaStateModifier = new AlphaStateModifier();

    public void setAlpha(float alpha) {
        this.alphaStateModifier.setAlpha(alpha);
    }

    public void multiplyAlpha(float alpha) {
        this.alphaStateModifier.multiplyAlpha(alpha);
    }

    public void revertAlphaState() {
        this.alphaStateModifier.revert();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/util/WidgetMeta$AlphaStateModifier.class */
    static class AlphaStateModifier {
        private static final RenderPipeline RENDER_PIPELINE = Laby.references().renderPipeline();
        private float lastState;

        AlphaStateModifier() {
        }

        public void setAlpha(float alpha) {
            this.lastState = RENDER_PIPELINE.getAlpha();
            RENDER_PIPELINE.setAlpha(alpha);
        }

        public void multiplyAlpha(float alpha) {
            this.lastState = RENDER_PIPELINE.getAlpha();
            RENDER_PIPELINE.multiplyAlpha(alpha);
        }

        public void revert() {
            RENDER_PIPELINE.setAlpha(this.lastState);
        }
    }
}
