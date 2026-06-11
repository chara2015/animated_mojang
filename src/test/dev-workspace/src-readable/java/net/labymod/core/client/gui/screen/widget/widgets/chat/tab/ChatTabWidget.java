package net.labymod.core.client.gui.screen.widget.widgets.chat.tab;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.core.client.gui.screen.widget.widgets.chat.ChatWindowWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/chat/tab/ChatTabWidget.class */
@AutoWidget
public class ChatTabWidget extends HorizontalListWidget {
    private final ChatWindowWidget windowWidget;
    private final ChatTab tab;
    private int unread;
    private ComponentWidget unreadWidget;

    public ChatTabWidget(ChatWindowWidget windowWidget, ChatTab tab) {
        this.windowWidget = windowWidget;
        this.tab = tab;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean active = this.windowWidget.window().getActiveTab() == this.tab;
        HorizontalListWidget tabWidget = new HorizontalListWidget();
        tabWidget.addId("tab");
        if (active) {
            tabWidget.addId("active");
        }
        this.windowWidget.applyDefaultContextMenu(tabWidget.createContextMenu(), this.tab);
        String tabName = this.tab.getName();
        if (tabName.length() > 15) {
            tabName = tabName.substring(0, 15) + "...";
        }
        ComponentWidget nameWidget = ComponentWidget.text(tabName);
        nameWidget.addId("name");
        tabWidget.addEntry(nameWidget);
        int unread = this.tab.getUnread();
        this.unread = unread;
        this.unreadWidget = ComponentWidget.text(String.valueOf(unread));
        this.unreadWidget.addId("unread");
        this.unreadWidget.setVisible(unread != 0);
        tabWidget.addEntry(this.unreadWidget);
        addEntry(tabWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.LEFT) {
            return false;
        }
        ChatTab activeTab = this.windowWidget.window().getActiveTab();
        if (activeTab == this.tab) {
            return false;
        }
        this.windowWidget.displayTab(this.tab);
        return true;
    }

    public void updateUnread(int unread) {
        if (this.unread == unread) {
            return;
        }
        this.unread = unread;
        if (this.unreadWidget != null) {
            this.unreadWidget.setText(String.valueOf(unread));
            this.unreadWidget.setVisible(unread != 0);
        }
    }

    public ChatTab tab() {
        return this.tab;
    }
}
