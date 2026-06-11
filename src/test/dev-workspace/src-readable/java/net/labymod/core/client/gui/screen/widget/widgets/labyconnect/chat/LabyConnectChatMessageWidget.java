package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.api.user.GameUser;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/chat/LabyConnectChatMessageWidget.class */
@AutoWidget
public class LabyConnectChatMessageWidget extends FlexibleContentWidget {
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private final GameUser sender;
    private final ChatMessage message;
    private boolean headerVisible;

    public LabyConnectChatMessageWidget(ChatMessage message) {
        this(message.sender().gameUser(), message);
    }

    public LabyConnectChatMessageWidget(GameUser sender, ChatMessage message) {
        this.headerVisible = true;
        this.sender = sender;
        this.message = message;
        addId(sender.getUniqueId().equals(this.labyAPI.getUniqueId()) ? "self" : "other");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        User sender = this.message.sender();
        VerticalListWidget<Widget> flex = new VerticalListWidget<>();
        flex.addId("flex");
        if (this.headerVisible) {
            HorizontalListWidget header = new HorizontalListWidget();
            header.addId("message-header");
            TextColor color = this.sender.displayColor();
            header.addEntry(ComponentWidget.component(Component.text(sender.getName()).color(color)).addId("sender"));
            ComponentWidget timestamp = ComponentWidget.text(TIME_FORMAT.format(Long.valueOf(this.message.getTimestamp())));
            timestamp.addId("timestamp");
            header.addEntry(timestamp);
            flex.addChild(header);
        }
        Widget messageContentWidget = this.message.createWidget();
        messageContentWidget.addId("message-content");
        if (!this.headerVisible) {
            messageContentWidget.addId("headless");
        }
        flex.addChild(messageContentWidget);
        addContent(flex);
        if (this.headerVisible) {
            IconWidget avatar = new IconWidget(Icon.head(sender.getUniqueId()));
            avatar.addId("avatar");
            addContent(avatar);
        }
    }

    public void setHeaderVisible(boolean headerVisible) {
        this.headerVisible = headerVisible;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        this.message.markAsRead();
    }

    public ChatMessage message() {
        return this.message;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LabyConnectChatMessageWidget that = (LabyConnectChatMessageWidget) o;
        return Objects.equals(this.message, that.message);
    }

    public int hashCode() {
        if (this.message != null) {
            return this.message.hashCode();
        }
        return 0;
    }
}
