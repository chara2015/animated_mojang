package net.labymod.core.client.gui.screen.widget.widgets.account;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.client.gui.screen.widget.widgets.account.entry.AccountEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/account/AccountPopupEntryWidget.class */
@AutoWidget
@Link("activity/account/account-popup.lss")
public class AccountPopupEntryWidget extends AbstractWidget<Widget> {
    private final AccountEntry entry;

    public AccountPopupEntryWidget(AccountEntry entry) {
        this.entry = entry;
        addId("account-entry");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        HorizontalListWidget list = new HorizontalListWidget();
        list.addId("container");
        IconWidget avatar = new IconWidget(this.entry.getIcon());
        avatar.addId("icon");
        list.addEntry(avatar);
        ComponentWidget usernameWidget = ComponentWidget.component(this.entry.getComponent());
        usernameWidget.addId("component");
        list.addEntry(usernameWidget);
        addChild(list);
    }
}
