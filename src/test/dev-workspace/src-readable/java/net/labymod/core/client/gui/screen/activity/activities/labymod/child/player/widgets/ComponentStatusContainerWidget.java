package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/ComponentStatusContainerWidget.class */
@AutoWidget
public class ComponentStatusContainerWidget<T extends Widget> extends StatusContainerWidget<T, ComponentWidget> {
    private final ComponentWidget statusWidget;

    public ComponentStatusContainerWidget() {
        super(null);
        this.statusWidget = ComponentWidget.empty();
        this.statusWidgetSupplier = () -> {
            return this.statusWidget;
        };
    }

    public ComponentStatusContainerWidget<T> displayStatus(Component status) {
        this.statusWidget.setComponent(status);
        return (ComponentStatusContainerWidget) displayStatus();
    }
}
