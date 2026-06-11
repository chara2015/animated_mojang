package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectOutgoingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestAddResponseEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectOutgoingFriendRequestRemoveEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.TextFormat;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction.LabyConnectOutgoingRequestWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/requests/LabyConnectOutgoingRequestsActivity.class */
@Link("activity/labyconnect/laby-connect-outgoing-requests.lss")
@AutoActivity
public class LabyConnectOutgoingRequestsActivity extends Activity {
    private static final Map<String, Notification> NOTIFICATIONS = new HashMap();
    private String username;
    private VerticalListWidget<LabyConnectRequestWidget<?>> listRequests;
    private ComponentWidget pendingTitle;

    public LabyConnectOutgoingRequestsActivity(@NotNull String username) {
        this();
        this.username = username;
    }

    public LabyConnectOutgoingRequestsActivity() {
        NOTIFICATIONS.clear();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("container");
        DivWidget header = new DivWidget();
        header.addId("header");
        ComponentWidget title = ComponentWidget.i18n("labymod.activity.labyconnect.outgoingRequests.title");
        title.addId("title");
        header.addChild(title);
        FlexibleContentWidget inputContainer = new FlexibleContentWidget();
        inputContainer.addId("input-container");
        TextFieldWidget input = new TextFieldWidget();
        input.addId("input");
        input.maximalLength(19);
        input.placeholder(Component.translatable("labymod.ui.textfield.username", new Component[0]));
        input.setFocused(true);
        input.setText(this.username);
        input.setCursorAtEnd();
        input.setPressable(() -> {
            this.username = input.getText();
        });
        input.submitHandler(username -> {
            sendFriendRequest(username, input);
        });
        inputContainer.addFlexibleContent(input);
        ButtonWidget sendButton = ButtonWidget.i18n("labymod.ui.button.send");
        sendButton.addId("send-button");
        sendButton.setPressable(() -> {
            sendFriendRequest(input.getText(), input);
        });
        inputContainer.addContent(sendButton);
        header.addChild(inputContainer);
        container.addContent(header);
        DivWidget listContainer = new DivWidget();
        listContainer.addId("list-container");
        this.pendingTitle = ComponentWidget.i18n("labymod.activity.labyconnect.outgoingRequests.list.title");
        this.pendingTitle.addId("list-title");
        this.pendingTitle.setVisible(false);
        listContainer.addChild(this.pendingTitle);
        this.listRequests = new VerticalListWidget<>();
        this.listRequests.addId("list");
        initializeRequests();
        listContainer.addChild(this.listRequests);
        container.addFlexibleContent(listContainer);
        ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
        cancelButton.addId("cancel-button");
        cancelButton.setPressable(this::displayPreviousScreen);
        container.addContent(cancelButton);
        ((Document) this.document).addChild(container);
    }

    private void initializeRequests() {
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        for (OutgoingFriendRequest request : session.getOutgoingRequests()) {
            this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, request));
            this.pendingTitle.setVisible(true);
        }
    }

    private void sendFriendRequest(String username, TextFieldWidget input) {
        String trim = username.trim();
        if (trim.isEmpty()) {
            return;
        }
        String ownUsername = this.labyAPI.minecraft().sessionAccessor().getSession().getUsername();
        if (trim.equalsIgnoreCase(ownUsername)) {
            displayNotification(Icon.head(ownUsername), Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, trim, Component.translatable("labymod.activity.labyconnect.outgoingRequests.result.self", new Component[0]).color(NamedTextColor.RED));
            input.setText("");
            return;
        }
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session != null) {
            Notification notification = displayNotification(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, null, trim, Component.translatable("labymod.activity.labyconnect.outgoingRequests.sending", new Component[0]).color(NamedTextColor.GREEN));
            NOTIFICATIONS.put(trim.toLowerCase(Locale.ROOT), notification);
            session.sendFriendRequest(trim);
            input.setText("");
        }
    }

    @Subscribe
    public void onFriendRequestAdd(LabyConnectOutgoingFriendRequestAddEvent event) {
        this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, event.request()));
    }

    @Subscribe
    public void onFriendRequestRemove(LabyConnectOutgoingFriendRequestRemoveEvent event) {
        this.listRequests.removeChildIf(widget -> {
            return (widget instanceof LabyConnectOutgoingRequestWidget) && ((LabyConnectOutgoingRequestWidget) widget).getRequest().equals(event.request());
        });
    }

    @Subscribe
    public void onFriendRequestAddBulk(LabyConnectOutgoingFriendRequestAddBulkEvent event) {
        for (OutgoingFriendRequest request : event.getRequests()) {
            this.listRequests.addChildAsync(new LabyConnectOutgoingRequestWidget(this, request));
        }
    }

    @Subscribe
    public void onFriendRequestResponse(LabyConnectOutgoingFriendRequestAddResponseEvent event) {
        Component component;
        Component component2;
        String query = event.getQuery();
        Notification notification = NOTIFICATIONS.remove(query.toLowerCase(Locale.ROOT));
        if (notification != null) {
            Laby.references().notificationController().pop(notification);
        }
        if (event.wasSent()) {
            component2 = Component.translatable("labymod.activity.labyconnect.outgoingRequests.result." + "success", new Component[0]).color(NamedTextColor.GREEN);
        } else {
            String reason = event.getReason();
            if (reason.contains(" ")) {
                LegacyResponse response = LegacyResponse.of(reason, query);
                if (response == null) {
                    component = Component.text(reason);
                } else {
                    component = Component.translatable("labymod.activity.labyconnect.outgoingRequests.result." + String.valueOf(response), new Component[0]).color(NamedTextColor.RED);
                    if (response.other) {
                        component = ((TranslatableComponent) component).args(Component.text(query));
                    }
                }
            } else {
                String translationKey = TextFormat.SNAKE_CASE.toCamelCase(reason, true);
                component = Component.translatable("labymod.activity.labyconnect.outgoingRequests.result." + translationKey, new Component[0]);
            }
            component2 = component.color(NamedTextColor.RED);
        }
        displayNotification(event.wasSent() ? Icon.head(query) : Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP, event.wasSent() ? Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP : null, query, component2);
    }

    private Notification displayNotification(Icon icon, Icon secondaryIcon, String username, Component text) {
        Notification notification = Notification.builder().icon(icon).secondaryIcon(secondaryIcon).title(Component.text(username)).text(text).build();
        Laby.references().notificationController().push(notification);
        return notification;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/requests/LabyConnectOutgoingRequestsActivity$LegacyResponse.class */
    private enum LegacyResponse {
        SERVER_ERROR("Our server is having some problems. Please try again in a few minutes.", false),
        ALREADY_REQUESTED("You already sent a request to that user", true),
        REQUEST_LIMIT_REACHED("Too many outgoing friend requests.", false),
        FRIEND_LIMIT_REACHED_OTHER("{} already has a lot of friends (Limit of 256)!", true),
        FRIEND_LIMIT_REACHED_SELF("You already have a lot of friends (Limit of 256)!", false),
        ALREADY_FRIENDS("You are already friends with {}", true),
        NOT_FOUND("User {} was not found", true);

        private static final LegacyResponse[] VALUES = values();
        private final String response;
        private final boolean other;

        LegacyResponse(String response, boolean other) {
            this.response = response.toLowerCase(Locale.ROOT);
            this.other = other;
        }

        @Override // java.lang.Enum
        public String toString() {
            return TextFormat.SNAKE_CASE.toLowerCamelCase(name());
        }

        public static LegacyResponse of(String response, String user) {
            String response2 = response.toLowerCase(Locale.ROOT);
            for (LegacyResponse value : VALUES) {
                String legacyResponse = value.response.replace("{}", user.toLowerCase(Locale.ROOT));
                if (legacyResponse.equals(response2)) {
                    return value;
                }
            }
            return null;
        }
    }
}
