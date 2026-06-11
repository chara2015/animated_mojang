package net.labymod.core.client.gui.navigation.elements;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageReadEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/navigation/elements/LabyConnectNavigationElement.class */
public class LabyConnectNavigationElement extends ScreenNavigationElement {
    public static final LabyConnectActivity ACTIVITY = new LabyConnectActivity();

    public LabyConnectNavigationElement() {
        super(ACTIVITY);
    }

    @Override // net.labymod.api.client.gui.navigation.NavigationElement
    public String getWidgetId() {
        return "labyconnect";
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Component getDisplayName() {
        Component component = Component.translatable("labymod.ui.navigation.labyconnect", new Component[0]);
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return component;
        }
        int unreadCount = session.getUnreadCount();
        if (unreadCount == 0) {
            return component;
        }
        component.append(Component.text(" "));
        component.append(Component.text("(", NamedTextColor.GRAY));
        component.append(Component.text(Integer.valueOf(unreadCount), NamedTextColor.RED));
        component.append(Component.text(")", NamedTextColor.GRAY));
        return component;
    }

    @Override // net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement
    public Icon getIcon() {
        return Textures.SpriteCommon.CHAT_BUBBLE;
    }

    @Subscribe
    public void onLabyConnectChatMessage(LabyConnectChatMessageEvent event) {
        updateWidget();
    }

    @Subscribe
    public void onLabyConnectChatMessageRead(LabyConnectChatMessageReadEvent event) {
        updateWidget();
    }
}
