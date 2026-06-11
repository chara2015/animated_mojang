package net.labymod.api.client.gui.hud.hudwidget.background;

import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.RoundedData;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/background/HudWidgetBackgroundRenderer.class */
public interface HudWidgetBackgroundRenderer {
    BackgroundConfig config();

    default void renderEntireBackground(ScreenContext context, HudSize size) {
        float width = size.getActualWidth();
        float height = size.getActualHeight();
        renderEntireBackground(context, width, height);
    }

    default void renderEntireBackground(ScreenContext context, float width, float height) {
        BackgroundConfig backgroundConfig = config();
        float margin = backgroundConfig.margin().get().floatValue();
        renderBackgroundSegment(context, margin, margin, width - (margin * 2.0f), height - (margin * 2.0f));
    }

    default void renderBackgroundSegment(ScreenContext context, float x, float y, float width, float height) {
        renderBackgroundSegment(context, x, y, width, height, 1.0f);
    }

    default void renderBackgroundSegment(ScreenContext context, float x, float y, float width, float height, float opacity) {
        BackgroundConfig backgroundConfig = config();
        if (!backgroundConfig.enabled().get().booleanValue()) {
            return;
        }
        int roundness = backgroundConfig.roundness().get().intValue();
        int backgroundColor = backgroundConfig.color().get().get();
        if (opacity != 1.0f) {
            int rgb = backgroundColor & 16777215;
            float alpha = (((backgroundColor >>> 24) & 255) / 255.0f) * opacity;
            backgroundColor = (((int) (alpha * 255.0f)) << 24) | rgb;
        }
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRoundedRect(x, y, width, height, backgroundColor, RoundedData.builder().setRadius(roundness).setLowerEdgeSoftness(-0.125f).setUpperEdgeSoftness(0.5f).build());
    }
}
