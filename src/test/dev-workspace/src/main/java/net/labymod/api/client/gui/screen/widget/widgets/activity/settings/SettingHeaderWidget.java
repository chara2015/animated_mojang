package net.labymod.api.client.gui.screen.widget.widgets.activity.settings;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/activity/settings/SettingHeaderWidget.class */
public class SettingHeaderWidget extends AbstractWidget<Widget> {
    private final Component displayName;
    private final Pressable pressable;

    public SettingHeaderWidget(Component displayName) {
        this(displayName, Pressable.NOOP);
    }

    public SettingHeaderWidget(Component displayName, Pressable pressable) {
        this.displayName = displayName;
        this.pressable = pressable;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ComponentWidget headerComponent = ComponentWidget.component(this.displayName);
        headerComponent.addId("title-header");
        headerComponent.setPressable(this.pressable);
        addChild(headerComponent);
    }
}
