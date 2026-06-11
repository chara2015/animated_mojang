package net.labymod.core.client.gui.screen.widget.widgets.hud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.hud.HudWidgetRendererAccessor;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HorizontalHudWidgetAlignment;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.hud.position.VerticalHudWidgetAlignment;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.ScaledRectangle;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.area.AreaRectangle;
import net.labymod.api.util.bounds.area.RectangleArea;
import net.labymod.api.util.bounds.area.RectangleAreaPosition;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.client.render.font.text.TextUtil;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/hud/HudWidgetRendererWidget.class */
@AutoWidget
public class HudWidgetRendererWidget extends AbstractWidget<HudWidgetWidget> implements HudWidgetRendererAccessor {
    private static final ModifyReason HUD_WIDGET_RENDERER = ModifyReason.of("hudWidgetRenderer");
    protected final AreaRectangle area = new AreaRectangle();
    protected final HudWidgetRegistry registry = Laby.references().hudWidgetRegistry();
    private boolean debugEnabled;

    public void addHudWidget(HudWidget<?> hudWidget) {
        addChildInitialized(new HudWidgetWidget(hudWidget, this));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        for (HudWidget<?> hudWidget : this.registry.values()) {
            addChild(new HudWidgetWidget(hudWidget, this));
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.attributes.bounds.WidgetStyleSheetUpdater
    public void onBoundsChanged(Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.area.set(bounds());
        updateHudWidgets();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        this.debugEnabled = this.labyAPI.minecraft().options().isDebugEnabled();
        super.render(context);
    }

    public void updateHudWidgets() {
        for (T widget : this.children) {
            HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget.getParent() == null && hudWidget.isEnabled() && canUpdateHudWidget(hudWidget)) {
                updateHudWidget(hudWidget);
            }
        }
    }

    public void updateHudWidget(HudWidget<?> hudWidget) {
        if (!hudWidget.isEnabled()) {
            throw new IllegalStateException("Cannot update hud widget that is not enabled");
        }
        if (hudWidget.getParent() != null) {
            throw new IllegalStateException("The hud widget has to be the first hud widget in the chain");
        }
        TextUtil.pushAndApplyAttributes();
        HudWidgetConfig config = hudWidget.getConfig();
        RectangleAreaPosition identifier = config.areaIdentifier();
        RectangleArea rectangle = this.area.getArea(identifier);
        FloatVector2 position = rectangle.relativeToAbsolute(config.getX(), config.getY());
        HudWidgetWidget widget = getWidget(hudWidget);
        HudWidgetAnchor anchor = HudWidgetAnchor.of(hudWidget);
        ScaledRectangle bounds = widget.scaledBounds();
        HudSize size = widget.size();
        HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone == null) {
            hudWidget.updateAnchor(anchor);
            bounds.setPosition((int) (position.getX() - anchor.getShiftX(size)), (int) (position.getY() - anchor.getShiftY(size)), HUD_WIDGET_RENDERER);
        } else {
            updateHudWidgetInDropzone(widget);
        }
        hudWidget.updateSize(widget, isEditor(), widget.size());
        widget.updateSizeOfWidget();
        updateOutOfBounds(hudWidget);
        updateHudWidgetChildrenOf(hudWidget);
        TextUtil.popRenderAttributes();
    }

    public void updateHudWidgetChildrenOf(HudWidget<?> parentHudWidget) {
        float bottom;
        HudWidget<?> hudWidget = parentHudWidget.getNextVisibleChild(isEditor());
        if (hudWidget == null) {
            return;
        }
        HudWidget<?> firstHudWidget = hudWidget.firstWidget();
        HudWidgetConfig firstWidgetConfig = firstHudWidget.getConfig();
        HorizontalHudWidgetAlignment alignment = firstWidgetConfig.horizontalAlignment().get();
        HudWidgetWidget parentWidget = getWidget(parentHudWidget);
        HudWidgetWidget widget = getWidget(hudWidget);
        ScaledRectangle parentBounds = parentWidget.scaledBounds();
        RectangleArea originRectangleArea = getArea(firstHudWidget);
        HudWidgetAnchor anchor = parentHudWidget.anchor();
        if (originRectangleArea != null) {
            anchor = HudWidgetAnchor.of(originRectangleArea.position(), alignment);
            hudWidget.updateAnchor(anchor);
        }
        float anchorX = parentBounds.getX() + anchor.getShiftX(parentWidget.size());
        if (parentHudWidget.isVisibleInGame() || isEditor()) {
            bottom = parentBounds.getBottom();
        } else {
            bottom = parentBounds.getTop();
        }
        float anchorY = bottom;
        ScaledRectangle bounds = widget.scaledBounds();
        HudSize size = widget.size();
        bounds.setPosition(anchorX - anchor.getShiftX(size), anchorY, HUD_WIDGET_RENDERER);
        widget.updateSizeOfWidget();
        updateHudWidgetChildrenOf(hudWidget);
    }

    public void updateHudWidgetsInDropzone() {
        HudWidget<?> draggingHudWidget = getDraggingHudWidget();
        for (T widget : this.children) {
            if (widget.hudWidget() != draggingHudWidget) {
                updateHudWidgetInDropzone(widget);
            }
        }
    }

    public void updateHudWidgetInDropzone(HudWidgetWidget widget) {
        HudWidget<?> hudWidget = widget.hudWidget();
        HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone == null || !hudWidget.isEnabled()) {
            return;
        }
        HudSize size = widget.size();
        ScaledRectangle bounds = widget.scaledBounds();
        hudWidget.updateAnchor(dropzone.getAnchor());
        float x = dropzone.getX(this, size);
        float y = dropzone.getY(this, size);
        bounds.setPosition((int) x, (int) y, HUD_WIDGET_RENDERER);
    }

    private void updateOutOfBounds(HudWidget<?> hudWidget) {
        HudWidgetDropzone dropzone = hudWidget.getAttachedDropzone();
        if (dropzone != null) {
            return;
        }
        HudWidgetWidget widget = getWidget(hudWidget);
        ScaledRectangle rectangle = widget.scaledBounds();
        if (rectangle.getRight() > this.area.getRight()) {
            rectangle.setX(this.area.getRight() - rectangle.getWidth(), HUD_WIDGET_RENDERER);
        }
        if (rectangle.getBottom() > this.area.getBottom()) {
            rectangle.setY(this.area.getBottom() - rectangle.getHeight(), HUD_WIDGET_RENDERER);
        }
        if (rectangle.getLeft() < this.area.getLeft()) {
            rectangle.setX(this.area.getLeft(), HUD_WIDGET_RENDERER);
        }
        if (rectangle.getTop() < this.area.getTop()) {
            rectangle.setY(this.area.getTop(), HUD_WIDGET_RENDERER);
        }
    }

    public void reinitializeHudWidget(HudWidget<?> hudWidget, String reason) {
        HudWidgetWidget widget = getWidget(hudWidget);
        widget.update(reason);
        updateHudWidget(hudWidget.firstWidget());
        hudWidget.onUpdate();
        if (hudWidget.getAttachedDropzone() != null) {
            updateHudWidgetsInDropzone();
        }
    }

    protected RectangleArea getArea(HudWidget<?> hudWidget) {
        HudWidgetWidget widget = getWidget(hudWidget);
        ScaledRectangle rectangle = widget.scaledBounds();
        float x = rectangle.getX() + hudWidget.anchor().getShiftX(widget.size());
        float y = rectangle.getY();
        return getArea(hudWidget, x, y);
    }

    protected RectangleArea getArea(HudWidget<?> hudWidget, float x, float y) {
        VerticalHudWidgetAlignment verticalAlignment = hudWidget.getConfig().verticalOrientation().get();
        switch (verticalAlignment) {
            case TOP:
                y = this.area.getTop();
                break;
            case CENTER:
                y = this.area.getCenterY();
                break;
            case BOTTOM:
                y = this.area.getBottom();
                break;
        }
        HorizontalHudWidgetAlignment horizontalAlignment = hudWidget.getConfig().horizontalOrientation().get();
        switch (horizontalAlignment) {
            case LEFT:
                x = this.area.getLeft();
                break;
            case CENTER:
                x = this.area.getCenterX();
                break;
            case RIGHT:
                x = this.area.getRight();
                break;
        }
        return this.area.getArea(x, y);
    }

    public boolean canUpdateHudWidget(HudWidget<?> hudWidget) {
        return true;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    @NotNull
    public HudWidgetWidget getWidget(@NotNull HudWidget<?> hudWidget) {
        for (T child : this.children) {
            if (child.hudWidget().equals(hudWidget)) {
                return child;
            }
        }
        throw new IllegalStateException("No widget found for " + hudWidget.getId());
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    public void onVisibilityChanged(HudWidget<?> hudWidget) {
        HudWidget<?> firstWidget = hudWidget.firstWidget();
        if (firstWidget.isEnabled() && canUpdateHudWidget(firstWidget)) {
            updateHudWidget(firstWidget);
        }
        if (firstWidget.getConfig().getDropzoneId() != null) {
            updateHudWidgetsInDropzonesOf(firstWidget);
        }
    }

    private void updateHudWidgetsInDropzonesOf(HudWidget<?> hudWidget) {
        for (HudWidgetDropzone dropzone : hudWidget.getDropzones()) {
            HudWidget<?> hudWidgetInDropzone = getHudWidgetInDropzone(dropzone);
            if (hudWidgetInDropzone != null && hudWidgetInDropzone != hudWidget) {
                updateHudWidget(hudWidgetInDropzone);
            }
        }
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    public HudWidget<?> getHudWidgetInDropzone(String dropzoneId) {
        String id;
        for (T widget : this.children) {
            HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget.isEnabled() && canUpdateHudWidget(hudWidget) && (id = hudWidget.getConfig().getDropzoneId()) != null && id.equals(dropzoneId)) {
                return hudWidget;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    public Collection<HudWidget<?>> getHudWidgetsInArea(Rectangle area) {
        List<HudWidget<?>> hudWidgets = new ArrayList<>();
        for (T widget : this.children) {
            HudWidget<?> hudWidget = widget.hudWidget();
            if (hudWidget.isEnabled()) {
                ScaledRectangle bounds = widget.scaledBounds();
                if (area.isOverlapping(bounds.copy().shrink(1.0f))) {
                    hudWidgets.add(hudWidget);
                }
            }
        }
        return hudWidgets;
    }

    public void onSizeChanged(HudWidget<?> hudWidget) {
        HudWidget<?> firstWidget = hudWidget.firstWidget();
        if (firstWidget.isEnabled()) {
            if (canUpdateHudWidget(firstWidget)) {
                updateHudWidget(firstWidget);
                if (hudWidget.anchor().isRight()) {
                    getWidget(hudWidget).skipInterpolation();
                }
            } else {
                HudWidgetWidget widget = getWidget(hudWidget);
                widget.updateSizeOfWidget();
            }
        }
        updateHudWidgetsInDropzone();
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    public Rectangle getArea() {
        return this.area;
    }

    public boolean isEditor() {
        return false;
    }

    @Override // net.labymod.api.client.gui.hud.HudWidgetRendererAccessor
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.Element
    public boolean isVisible() {
        boolean editor = isEditor();
        if (!editor && !this.labyAPI.minecraft().isIngame()) {
            return false;
        }
        if (editor) {
            return true;
        }
        for (HudWidgetWidget child : getChildren()) {
            HudWidget<?> hudWidget = child.hudWidget();
            if (hudWidget.isEnabled()) {
                return true;
            }
        }
        return false;
    }
}
