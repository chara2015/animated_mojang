package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/layout/SplitContentWidget.class */
@AutoWidget
public class SplitContentWidget<Left extends Widget, Right extends Widget> extends AbstractWidget<Widget> {
    private static final ModifyReason ENTRY_POSITION = ModifyReason.of("entryPosition");
    private final LssProperty<Float> splitGapWidth = new LssProperty<>(Float.valueOf(5.0f));
    private final LssProperty<Float> splitButtonWidth = new LssProperty<>(Float.valueOf(5.0f));
    private final LssProperty<Float> initialPercentage = new LssProperty<>(Float.valueOf(0.3f));
    private final LssProperty<Float> minPercentage = new LssProperty<>(Float.valueOf(0.2f));
    private final LssProperty<Float> maxPercentage = new LssProperty<>(Float.valueOf(0.8f));
    private boolean modified;
    private float percentage;
    private boolean dragging;
    private float percentageBeforeDrag;
    private Left left;
    private Right right;

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        addChild(this.left);
        addChild(this.right);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (!this.modified) {
            this.percentage = this.initialPercentage.get().floatValue();
            this.percentage = clampPercentage(this.percentage);
        }
        updateSplit();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        updateSplit();
    }

    @Override // net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void applyMediaRules(boolean updateState) {
        super.applyMediaRules(updateState);
        this.percentage = clampPercentage(this.percentage);
    }

    private void updateSplit() {
        Bounds parentBounds = bounds();
        float leftWidth = parentBounds.getWidth() * this.percentage;
        if (this.left != null) {
            ReasonableMutableRectangle leftBounds = this.left.bounds().rectangle(BoundsType.OUTER);
            leftBounds.setBounds(parentBounds.getLeft(), parentBounds.getTop(), (parentBounds.getLeft() + leftWidth) - this.splitGapWidth.get().floatValue(), parentBounds.getBottom(), ENTRY_POSITION);
            this.left.updateBounds();
        }
        if (this.right != null) {
            ReasonableMutableRectangle rightBounds = this.right.bounds().rectangle(BoundsType.OUTER);
            rightBounds.setBounds(parentBounds.getLeft() + leftWidth + this.splitGapWidth.get().floatValue(), parentBounds.getTop(), parentBounds.getRight(), parentBounds.getBottom(), ENTRY_POSITION);
            this.right.updateBounds();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget, net.labymod.api.client.gui.element.Element
    public void renderOverlay(ScreenContext context) {
        super.renderOverlay(context);
        Bounds bounds = bounds();
        float splitX = bounds.getLeft() + (bounds.getWidth() * this.percentage);
        if (!this.dragging && !isHoveringSplitButton(context.mouse(), splitX)) {
            return;
        }
        ScreenCanvas canvas = context.canvas();
        if (this.dragging) {
            canvas.submitAbsoluteRect(splitX - 0.5f, bounds.getTop(), splitX + 0.5f, bounds.getBottom(), -2130706433);
        }
        canvas.submitText("||", splitX - 1.5f, context.mouse().getY() - 3.0f, -1, 1);
    }

    private boolean isHoveringSplitButton(MutableMouse mouse, float splitX) {
        if (this.labyAPI.minecraft().isMouseDown(MouseButton.LEFT)) {
            return false;
        }
        float buttonWidth = this.splitButtonWidth.get().floatValue();
        return ((float) mouse.getX()) >= splitX - buttonWidth && ((float) mouse.getX()) <= splitX + buttonWidth;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            Bounds bounds = bounds();
            float leftWidth = bounds.getWidth() * this.percentage;
            if (mouse.getX() >= (bounds.getLeft() + leftWidth) - this.splitButtonWidth.get().floatValue() && mouse.getX() <= bounds.getLeft() + leftWidth + this.splitButtonWidth.get().floatValue()) {
                this.dragging = true;
                this.percentageBeforeDrag = this.percentage;
            }
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if (this.dragging) {
            Bounds bounds = bounds();
            float percentage = (1.0f / bounds.getWidth()) * (mouse.getX() - bounds.getLeft());
            this.percentage = clampPercentage(percentage);
            this.modified = true;
            updateSplit();
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            this.dragging = false;
        }
        return super.mouseReleased(mouse, mouseButton);
    }

    public void setLeft(Left left) {
        this.left = left;
    }

    public void setRight(Right right) {
        this.right = right;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        updateSplit();
    }

    public float getPercentage() {
        return this.percentage;
    }

    public LssProperty<Float> splitButtonWidth() {
        return this.splitButtonWidth;
    }

    public LssProperty<Float> splitGapWidth() {
        return this.splitGapWidth;
    }

    public LssProperty<Float> initialPercentage() {
        return this.initialPercentage;
    }

    public LssProperty<Float> minPercentage() {
        return this.minPercentage;
    }

    public LssProperty<Float> maxPercentage() {
        return this.maxPercentage;
    }

    private float clampPercentage(float value) {
        return MathHelper.clamp(value, this.minPercentage.get().floatValue(), this.maxPercentage.get().floatValue());
    }
}
