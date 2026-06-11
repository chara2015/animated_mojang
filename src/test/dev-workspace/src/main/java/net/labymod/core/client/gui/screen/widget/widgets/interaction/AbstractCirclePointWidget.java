package net.labymod.core.client.gui.screen.widget.widgets.interaction;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.AbstractPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/interaction/AbstractCirclePointWidget.class */
public class AbstractCirclePointWidget<T extends AbstractPointWidget> extends AbstractWidget<T> {
    protected final InteractionAnimationController animationController;
    private boolean hasLastMousePosition;
    private int lastMouseX;
    private int lastMouseY;

    public AbstractCirclePointWidget(InteractionAnimationController animationController) {
        this.animationController = animationController;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        MutableMouse mouse = context.mouse();
        this.lastMouseX = mouse.getX();
        this.lastMouseY = mouse.getY();
        this.hasLastMousePosition = true;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        AbstractPointWidget nearest = getNearestPoint();
        for (T child : this.children) {
            child.updateHovered(child == nearest);
        }
    }

    public T getNearestPoint() {
        if (!this.hasLastMousePosition) {
            return null;
        }
        Bounds bounds = bounds();
        float centerX = bounds.getCenterX();
        float centerY = bounds.getCenterY();
        AbstractPointWidget abstractPointWidget = null;
        float f = Float.MAX_VALUE;
        float minRadius = getMinRadius();
        for (T t : this.children) {
            Bounds bounds2 = t.bounds();
            float centerX2 = bounds2.getCenterX();
            float centerY2 = bounds2.getCenterY();
            float fSquare = MathHelper.square(this.lastMouseX - centerX2) + MathHelper.square(this.lastMouseY - centerY2);
            float fSquare2 = MathHelper.square(centerX2 - centerX) + MathHelper.square(centerY2 - centerY);
            if (fSquare < f && fSquare2 > MathHelper.square(minRadius) && fSquare < MathHelper.square(t.getRadius())) {
                abstractPointWidget = t;
                f = fSquare;
            }
        }
        return (T) abstractPointWidget;
    }

    protected float getRadius() {
        return bounds().getHeight() / 2.0f;
    }

    protected float getMinRadius() {
        return getRadius() / 1.5f;
    }
}
