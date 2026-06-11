package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.function.FloatSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/ProgressBarWidget.class */
@AutoWidget
public class ProgressBarWidget extends SimpleWidget {
    private static final ModifyReason UPDATE_BAR = ModifyReason.of("updatebar");
    private DivWidget barWidget;
    private ComponentWidget textWidget;
    private FloatSupplier progressSupplier;
    private float progress;

    public ProgressBarWidget() {
        setProgress(0.0f);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.barWidget = new DivWidget();
        this.barWidget.addId("bar");
        addChild(this.barWidget);
        this.textWidget = ComponentWidget.empty();
        this.textWidget.addId("text");
        addChild(this.textWidget);
        updateBar();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext screenContext) {
        updateProgress();
        super.renderWidget(screenContext);
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float progress) {
        setProgress(() -> {
            return progress;
        });
        updateProgress();
    }

    public void setProgress(FloatSupplier progressSupplier) {
        this.progressSupplier = progressSupplier;
    }

    private void updateBar() {
        Bounds bounds = bounds();
        if (this.barWidget != null) {
            float progressWidth = bounds.getWidth() * this.progress;
            this.barWidget.bounds().setBounds(bounds.getLeft(), bounds.getTop(), bounds.getLeft() + progressWidth, bounds.getBottom(), UPDATE_BAR);
        }
        Component component = Component.text(((int) (this.progress * 100.0f)) + "%");
        RenderableComponent renderableComponent = RenderableComponent.of(component);
        if (this.textWidget != null) {
            this.textWidget.setComponent(component);
            this.textWidget.bounds().setPosition(bounds.getCenterX() - (renderableComponent.getWidth() / 2.0f), bounds.getTop(), UPDATE_BAR);
        }
    }

    private void updateProgress() {
        float progress = this.progressSupplier.get();
        if (this.progress != progress) {
            this.progress = progress;
            updateBar();
        }
    }
}
