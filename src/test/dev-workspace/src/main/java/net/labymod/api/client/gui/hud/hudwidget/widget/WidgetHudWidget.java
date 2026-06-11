package net.labymod.api.client.gui.hud.hudwidget.widget;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/widget/WidgetHudWidget.class */
@Links({@Link("style.lss"), @Link("widget-hud-widget.lss")})
public abstract class WidgetHudWidget<T extends HudWidgetConfig> extends HudWidget<T> {
    private final LinkMetaLoader linkMetaLoader;

    protected WidgetHudWidget(String id, Class<T> config) {
        super(id, config);
        this.linkMetaLoader = Laby.references().linkMetaLoader();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initialize(HudWidgetWidget widget) {
        widget.handleStyleSheet = true;
        widget.setSize(SizeType.ACTUAL, WidgetSide.WIDTH, WidgetSize.fitContent());
        widget.setSize(SizeType.ACTUAL, WidgetSide.HEIGHT, WidgetSize.fitContent());
        widget.setStencil(true);
        super.initialize(widget);
        widget.setInitializedAnchor(this.anchor);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void postInitialize(HudWidgetWidget widget) {
        LinkMetaLoader linkMetaLoader = this.linkMetaLoader;
        Class<?> cls = getClass();
        Objects.requireNonNull(widget);
        linkMetaLoader.loadMeta(cls, widget::applyStyleSheet);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onBoundsChanged(HudWidgetWidget widget, Rectangle previousRect, Rectangle newRect) {
        super.onBoundsChanged(widget, previousRect, newRect);
        updateSize(widget, widget.accessor().isEditor(), widget.size());
        widget.setScale(widget.size().getScale());
        if (this.anchor != widget.getInitializedAnchor()) {
            widget.reInitialize();
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void updateSize(HudWidgetWidget widget, boolean isEditorContext, HudSize size) {
        Bounds bounds = widget.bounds();
        size.set(bounds.getWidth(), bounds.getHeight());
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void render(ScreenContext context, boolean isEditorContext, HudSize size) {
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean canPreInitialize() {
        return false;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean handlesScaling() {
        return true;
    }
}
