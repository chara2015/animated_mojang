package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/CustomServerEntryWidget.class */
@AutoWidget
public final class CustomServerEntryWidget extends ServerEntryWidget {
    private final Widget widget;

    public CustomServerEntryWidget(Widget widget) {
        this.widget = widget;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget wrapper = new FlexibleContentWidget();
        wrapper.addId("wrapper");
        wrapper.addContent(this.widget);
        addChild(wrapper);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public int getListIndex() {
        return 0;
    }
}
