package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/selector/MarkerWidget.class */
@AutoWidget
public class MarkerWidget extends AbstractWidget<Widget> {
    private static final ModifyReason COLOR_POSITION = ModifyReason.of("colorPosition");
    private final SelectorWidget selectorWidget;
    private float x;
    private float y;

    protected MarkerWidget(SelectorWidget selectorWidget) {
        this.selectorWidget = selectorWidget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        super.render(context);
        Scissor scissor = context.canvas().scissor();
        scissor.push(this.selectorWidget.bounds());
        renderMarker(context);
        scissor.pop();
    }

    private void renderMarker(ScreenContext context) {
        float width = bounds().getWidth() / 2.0f;
        context.canvas().submitCircle(RenderStates.INVERTED_COLOR_CIRCLE, bounds().getLeft() + width, bounds().getTop() + width, width - 1.0f, width, -1);
    }

    public void setX(float x) {
        this.x = x;
        float width = bounds().getWidth();
        bounds().setLeft((this.selectorWidget.bounds().getLeft() + x) - (width / 2.0f), COLOR_POSITION);
        bounds().setWidth(width, COLOR_POSITION);
    }

    public void setY(float y) {
        this.y = y;
        float height = bounds().getHeight();
        bounds().setTop((this.selectorWidget.bounds().getBottom() - y) - (height / 2.0f), COLOR_POSITION);
        bounds().setHeight(height, COLOR_POSITION);
    }

    public void reapply() {
        if (this.x == 0.0f && this.y == 0.0f) {
            this.selectorWidget.updateMarkerPosition();
        }
        setX(this.x);
        setY(this.y);
    }
}
