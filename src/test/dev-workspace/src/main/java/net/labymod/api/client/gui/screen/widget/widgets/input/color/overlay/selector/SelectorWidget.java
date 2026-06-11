package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector;

import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/color/overlay/selector/SelectorWidget.class */
@AutoWidget
public abstract class SelectorWidget extends AbstractWidget<Widget> {
    private final ColorData colorData;
    private final LssProperty<Orientation> orientation = new LssProperty<>(Orientation.NONE);
    private boolean dragging = false;
    private final MarkerWidget markerWidget = new MarkerWidget(this);

    public abstract void renderSelector(Orientation orientation, ScreenContext screenContext);

    public abstract void update(float f, float f2);

    public abstract void updateMarkerPosition();

    protected SelectorWidget(ColorData colorData) {
        this.colorData = colorData;
        colorData.addUpdateListener(this, () -> {
            if (!this.dragging) {
                updateMarkerPosition();
            }
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.markerWidget);
        updateMarkerPosition();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        Orientation orientation = this.orientation.get();
        if (orientation == Orientation.NONE) {
            return;
        }
        if (this.dragging) {
            Bounds bounds = bounds();
            update(Math.min(Math.max(0.0f, (float) (context.mouse().getXDouble() - ((double) bounds.getLeft()))), bounds.getWidth()), Math.min(Math.max(0.0f, (float) (-(context.mouse().getYDouble() - ((double) bounds.getBottom())))), bounds.getHeight()));
        }
        renderSelector(orientation, context);
        if (this.markerWidget != null) {
            this.markerWidget.render(context);
        }
        if (this.dragging && !this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT)) {
            this.dragging = false;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton != MouseButton.LEFT) {
            return false;
        }
        this.dragging = true;
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return super.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.markerWidget != null) {
            this.markerWidget.reapply();
        }
    }

    public void updateMarkerPosition(float posX, float posY) {
        if (posX >= 0.0f && posX <= bounds().getWidth(BoundsType.INNER)) {
            markerWidget().setX(posX);
        }
        if (posY >= 0.0f && posY <= bounds().getHeight(BoundsType.INNER)) {
            markerWidget().setY(posY);
        }
        markerWidget().bounds().checkForChanges();
    }

    public ColorData colorData() {
        return this.colorData;
    }

    public MarkerWidget markerWidget() {
        return this.markerWidget;
    }

    public LssProperty<Orientation> orientation() {
        return this.orientation;
    }
}
