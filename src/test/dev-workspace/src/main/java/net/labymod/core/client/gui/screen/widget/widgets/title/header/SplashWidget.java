package net.labymod.core.client.gui.screen.widget.widgets.title.header;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/SplashWidget.class */
@AutoWidget
public class SplashWidget extends AbstractWidget<Widget> implements SplashWidgetAccessor {
    private RenderableComponent splashText;

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor
    public void setSplashText(String splashText) {
        this.splashText = RenderableComponent.of(Component.text(splashText));
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.title.header.SplashWidgetAccessor
    public RenderableComponent getSplashText() {
        return this.splashText;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        Stack stack = context.stack();
        if (this.splashText != null) {
            stack.push();
            stack.translate(bounds().getCenterX(), bounds().getCenterY() + (this.splashText.getHeight() / 2.0f), 0.0f);
            stack.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
            float scale = ((1.8f - Math.abs(MathHelper.sin(((TimeUtil.getMillis() % 1000) / 1000.0f) * 6.2831855f) * 0.1f)) * 100.0f) / (this.splashText.getWidth() + 32.0f);
            stack.scale(scale, scale, scale);
            ScreenCanvas canvas = context.canvas();
            canvas.nextLayer();
            canvas.submitRenderableComponent(this.splashText, 0.0f, -8.0f, NamedTextColor.YELLOW.getValue() | (-16777216), 3);
            stack.pop();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 1.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 1.0f;
    }
}
