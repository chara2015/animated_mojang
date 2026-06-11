package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.function.FloatSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/ProgressableProgressBarWidget.class */
@AutoWidget
public class ProgressableProgressBarWidget extends AbstractWidget<Widget> {
    private final FloatSupplier progressSupplier;
    private final LssProperty<Integer> progressForegroundColor = new LssProperty<>(-16711936);
    private final LssProperty<Integer> progressBackgroundColor = new LssProperty<>(-16777216);

    public ProgressableProgressBarWidget(FloatSupplier progressSupplier) {
        this.progressSupplier = progressSupplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        Bounds bounds = bounds();
        float progress = this.progressSupplier.get();
        if (progress < 0.0f) {
            progress = 0.0f;
        } else if (progress > 1.0f) {
            progress = 1.0f;
        }
        float x = bounds.getX();
        float y = bounds.getY();
        float width = bounds.getWidth();
        float height = bounds.getHeight();
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRect(x, y, width, height, this.progressBackgroundColor.get().intValue());
        renderState.submitRelativeRect(x, y, width * progress, height, this.progressForegroundColor.get().intValue());
    }

    public LssProperty<Integer> progressForegroundColor() {
        return this.progressForegroundColor;
    }

    public LssProperty<Integer> progressBackgroundColor() {
        return this.progressBackgroundColor;
    }
}
