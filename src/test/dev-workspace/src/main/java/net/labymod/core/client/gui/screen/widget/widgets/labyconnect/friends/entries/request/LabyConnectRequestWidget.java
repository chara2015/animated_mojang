package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/friends/entries/request/LabyConnectRequestWidget.class */
@AutoWidget
public abstract class LabyConnectRequestWidget<T extends User> extends LabyConnectEntryWidget {
    protected final T request;

    protected abstract void initializeButtons(DivWidget divWidget);

    public LabyConnectRequestWidget(ParentScreen contentDisplay, T request) {
        super(contentDisplay);
        this.request = request;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget container = new DivWidget();
        IconWidget widgetHead = new IconWidget(Icon.head(this.request.getUniqueId()));
        widgetHead.addId("icon");
        container.addChild(widgetHead);
        ComponentWidget widgetName = ComponentWidget.text(this.request.getName());
        widgetName.addId("text");
        container.addChild(widgetName);
        initializeButtons(container);
        addChild(container);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget
    public void select() {
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public int getSortingValue() {
        return 0;
    }

    public T getRequest() {
        return this.request;
    }
}
