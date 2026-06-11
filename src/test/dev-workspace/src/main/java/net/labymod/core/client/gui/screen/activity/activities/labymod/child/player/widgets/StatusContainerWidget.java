package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets;

import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/StatusContainerWidget.class */
@AutoWidget
public class StatusContainerWidget<T extends Widget, S extends Widget> extends AbstractWidget<Widget> {
    protected static final String STATUS_WIDGET_ID = "status-content";
    protected Supplier<S> statusWidgetSupplier;
    protected boolean status;
    protected S statusWidget;
    protected T contentWidget;

    public StatusContainerWidget(Supplier<S> statusWidgetSupplier) {
        this.statusWidgetSupplier = statusWidgetSupplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.children.clear();
        if (this.status) {
            this.statusWidget = this.statusWidgetSupplier.get();
            if (this.statusWidget == null) {
                return;
            }
            this.statusWidget.addId(STATUS_WIDGET_ID);
            addChild(this.statusWidget);
            return;
        }
        if (this.contentWidget != null) {
            Widget firstChildIf = findFirstChildIf(widget -> {
                return widget == this.contentWidget;
            });
            if (firstChildIf != null) {
                firstChildIf.setVisible(true);
            } else {
                addChild(this.contentWidget);
            }
        }
    }

    public StatusContainerWidget<T, S> displayStatus() {
        this.status = true;
        if (!this.initialized) {
            return this;
        }
        Widget statusWidget = findFirstChildIf(widget -> {
            return widget == this.statusWidget;
        });
        if (statusWidget == null) {
            this.statusWidget = this.statusWidgetSupplier.get();
            this.statusWidget.addId(STATUS_WIDGET_ID);
            this.statusWidget.setVisible(true);
            addChildInitialized(this.statusWidget);
        } else {
            statusWidget.setVisible(true);
        }
        if (this.contentWidget != null) {
            this.contentWidget.setVisible(false);
        }
        return this;
    }

    public StatusContainerWidget<T, S> displayContent() {
        this.status = false;
        if (!this.initialized) {
            return this;
        }
        if (this.statusWidget != null) {
            this.statusWidget.setVisible(false);
        }
        Widget contentWidget = findFirstChildIf(widget -> {
            return widget == this.contentWidget;
        });
        if (contentWidget == null) {
            this.contentWidget.setVisible(true);
            addChildInitialized(this.contentWidget);
        } else {
            contentWidget.setVisible(true);
        }
        return this;
    }

    public void updateContent(T content) {
        this.contentWidget = content;
    }

    public S getStatusWidget() {
        return this.statusWidget;
    }

    public T getContentWidget() {
        return this.contentWidget;
    }
}
