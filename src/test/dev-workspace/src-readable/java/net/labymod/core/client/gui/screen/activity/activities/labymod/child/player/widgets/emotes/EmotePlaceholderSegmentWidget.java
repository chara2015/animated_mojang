package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/widgets/emotes/EmotePlaceholderSegmentWidget.class */
@AutoWidget
public class EmotePlaceholderSegmentWidget extends WheelWidget.Segment {
    private final Icon icon;
    private final Component component;

    public EmotePlaceholderSegmentWidget(Icon icon, Component component) {
        this.icon = icon;
        this.component = component;
        if (this.icon == null && this.component == null) {
            throw new IllegalArgumentException("Either icon or component must be set");
        }
        if (this.icon == null) {
            addId("component-placeholder");
        } else if (this.component == null) {
            addId("icon-placeholder");
        } else {
            addId("icon-component-placeholder");
        }
    }

    public EmotePlaceholderSegmentWidget(Icon icon) {
        this(icon, null);
    }

    public EmotePlaceholderSegmentWidget(Component component) {
        this(null, component);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.children.clear();
        IconWidget iconWidget = null;
        if (this.icon != null) {
            iconWidget = new IconWidget(this.icon);
            iconWidget.addId("placeholder-icon");
        }
        ComponentWidget componentWidget = null;
        if (this.component != null) {
            componentWidget = ComponentWidget.component(this.component);
            componentWidget.addId("placeholder-component");
        }
        if (iconWidget == null) {
            addChild(componentWidget);
            return;
        }
        if (componentWidget == null) {
            addChild(iconWidget);
            return;
        }
        VerticalListWidget<Widget> container = new VerticalListWidget<>();
        container.addId("placeholder-container");
        container.addChild(iconWidget);
        container.addChild(componentWidget);
        addChild(container);
    }
}
