package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.configuration.settings.type.AbstractSettingRegistry;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.LabyConnectStateUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectUpdateSettingEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageDeleteEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageReadEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendPinUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectFriendAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectOfflineActivity;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests.LabyConnectOutgoingRequestsActivity;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectFriendWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectRequestsButtonWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/LabyConnectFriendsActivity.class */
@Link("activity/labyconnect/laby-connect-friends.lss")
@AutoActivity
public class LabyConnectFriendsActivity extends Activity {
    private static final ListSession<LabyConnectEntryWidget> SESSION = new ListSession<>();
    private static final int MAX_OFFLINE_FRIENDS = 100;

    @Nullable
    private ScreenRendererWidget contentScreenRendererWidget;
    private ParentScreen contentDisplay;
    private ScrollWidget friendsScroll;
    private VerticalListWidget<LabyConnectEntryWidget> listFriends;
    private ComponentWidget friendCounter;
    private String filterQuery = "";

    public void setContentDisplay(ParentScreen contentDisplay) {
        if (contentDisplay instanceof ScreenRendererWidget) {
            this.contentScreenRendererWidget = (ScreenRendererWidget) contentDisplay;
        }
        this.contentDisplay = contentDisplay;
        LabyConnectEntryWidget selected = SESSION.getSelectedEntry();
        if (selected != null) {
            selected.updateContentDisplay(contentDisplay);
            selected.select();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        FlexibleContentWidget containers = new FlexibleContentWidget();
        containers.addId("containers");
        DivWidget header = new DivWidget();
        header.addId("header-container");
        this.friendCounter = ComponentWidget.empty();
        this.friendCounter.addId("title");
        updateFriendCounter();
        header.addChild(this.friendCounter);
        ButtonWidget buttonAdd = ButtonWidget.icon(Textures.SpriteCommon.SMALL_ADD_WITH_SHADOW);
        buttonAdd.addId("button-add");
        buttonAdd.setPressable(this::openOutgoingRequests);
        header.addChild(buttonAdd);
        containers.addContent(header);
        DivWidget search = new DivWidget();
        search.addId("search-container");
        TextFieldWidget textField = new TextFieldWidget();
        textField.addId("search-field");
        textField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        textField.setText(this.filterQuery);
        textField.updateListener(this::applyFriendsFilter);
        search.addChild(textField);
        containers.addContent(search);
        this.listFriends = new VerticalListWidget<>(SESSION);
        this.listFriends.addId("friends-container");
        this.listFriends.setSelectCallback((v0) -> {
            v0.select();
        });
        this.listFriends.setComparator((f1, f2) -> {
            long timestamp;
            long timestamp2;
            if (!(f1 instanceof LabyConnectFriendWidget) || !(f2 instanceof LabyConnectFriendWidget)) {
                if (f1 instanceof LabyConnectRequestsButtonWidget) {
                    return -1;
                }
                if (f2 instanceof LabyConnectRequestsButtonWidget) {
                    return 1;
                }
                return 0;
            }
            Friend friend1 = ((LabyConnectFriendWidget) f1).friend();
            Friend friend2 = ((LabyConnectFriendWidget) f2).friend();
            int cp = Boolean.compare(friend2.isPinned(), friend1.isPinned());
            if (cp != 0) {
                return cp;
            }
            int cs = Boolean.compare(friend2.isOnline(), friend1.isOnline());
            if (cs != 0) {
                return cs;
            }
            if (friend1.chat().getLastMessage() != null || friend2.chat().getLastMessage() != null) {
                if (friend1.chat().getLastMessage() != null) {
                    timestamp = friend1.chat().getLastMessage().getTimestamp();
                } else {
                    timestamp = 0;
                }
                long t1 = timestamp;
                if (friend2.chat().getLastMessage() != null) {
                    timestamp2 = friend2.chat().getLastMessage().getTimestamp();
                } else {
                    timestamp2 = 0;
                }
                long t2 = timestamp2;
                int cm = Long.compare(t2, t1);
                if (cm != 0) {
                    return cm;
                }
            }
            return Long.compare(friend2.getLastOnline(), friend1.getLastOnline());
        });
        initializeFriendList(false);
        this.friendsScroll = new ScrollWidget((VerticalListWidget<?>) this.listFriends);
        containers.addFlexibleContent(this.friendsScroll);
        DivWidget footer = new DivWidget();
        footer.addId("footer-container");
        User self = session.self();
        footer.addChild(new DivWidget().addId("split"));
        footer.addChild(new IconWidget(Icon.head(self.getUniqueId())).addId("icon"));
        footer.addChild(ComponentWidget.text(self.getName()).addId("name"));
        UserStatus userStatus = self.userStatus();
        TranslatableComponent statusComponent = Component.translatable(userStatus.getRemoteTranslationKey(true), userStatus.textColor());
        footer.addChild(ComponentWidget.component(statusComponent).addId("status"));
        ButtonWidget settingsButton = ButtonWidget.icon(Textures.SpriteCommon.SETTINGS);
        settingsButton.setPressable(this::openSettings);
        LabyConnectOfflineActivity.fillContextMenu(footer.createContextMenu());
        footer.addChild(settingsButton);
        containers.addContent(footer);
        ((Document) this.document).addChild(containers);
    }

    private void updateFriendCounter() {
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        Component component = Component.translatable("labymod.activity.labyconnect.friends.title", new Component[0]).append(Component.text(" (").append(Component.text(Integer.valueOf(session.getOnlineFriendCount())).color(NamedTextColor.GREEN)).append(Component.text("/")).append(Component.text(Integer.valueOf(session.getFriends().size())).color(NamedTextColor.GRAY)).append(Component.text(")")));
        this.friendCounter.setComponent(component);
    }

    private void applyFriendsFilter(String query) {
        this.filterQuery = query;
        initializeFriendList(true);
    }

    private void initializeFriendList(boolean initialized) {
        if (this.listFriends == null) {
            return;
        }
        this.listFriends.getChildren().clear();
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null) {
            return;
        }
        if (!hasFilter()) {
            LabyConnectRequestsButtonWidget requests = new LabyConnectRequestsButtonWidget(this.contentDisplay, session.getIncomingRequests());
            if (initialized) {
                this.listFriends.addChildInitialized(requests);
            } else {
                this.listFriends.addChild(requests);
            }
        }
        List<LabyConnectEntryWidget> children = new ArrayList<>();
        int addedOfflineFriends = 0;
        for (Friend friend : session.getFriends()) {
            boolean offline = !friend.isOnline();
            if (!offline || addedOfflineFriends < MAX_OFFLINE_FRIENDS || friend.isPinned()) {
                if (isUserInFilter(friend)) {
                    children.add(new LabyConnectFriendWidget(this.contentDisplay, friend));
                    if (offline) {
                        addedOfflineFriends++;
                    }
                }
            }
        }
        if (initialized) {
            this.listFriends.addChildrenInitialized(children, true);
        } else {
            this.listFriends.addChildren(children, true);
        }
    }

    private boolean hasFilter() {
        return (this.filterQuery == null || this.filterQuery.isEmpty()) ? false : true;
    }

    private boolean isUserInFilter(User user) {
        if (!hasFilter()) {
            return true;
        }
        return user.getName().toLowerCase(Locale.ROOT).contains(this.filterQuery.toLowerCase(Locale.ROOT));
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
    }

    @Subscribe
    public void onLabyConnectStateUpdate(LabyConnectStateUpdateEvent event) {
        initializeFriendList(true);
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(LabyConnectIncomingFriendRequestAddEvent event) {
        this.listFriends.reInitializeChildrenIf(widget -> {
            return widget instanceof LabyConnectRequestsButtonWidget;
        });
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulkEvent(LabyConnectIncomingFriendRequestAddBulkEvent event) {
        this.listFriends.reInitializeChildrenIf(widget -> {
            return widget instanceof LabyConnectRequestsButtonWidget;
        });
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestRemove(LabyConnectIncomingFriendRequestRemoveEvent event) {
        this.listFriends.reInitializeChildrenIf(widget -> {
            return widget instanceof LabyConnectRequestsButtonWidget;
        });
    }

    @Subscribe
    public void onLabyConnectFriendAdd(LabyConnectFriendAddEvent event) {
        addFriend(event.friend());
        updateFriendCounter();
    }

    @Subscribe
    public void onLabyConnectFriendAddBulk(LabyConnectFriendAddBulkEvent event) {
        for (Friend friend : event.getFriends()) {
            addFriend(friend);
        }
        updateFriendCounter();
    }

    @Subscribe
    public void onLabyConnectFriendRemove(LabyConnectFriendRemoveEvent event) {
        UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.removeChildIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().getUniqueId().equals(uniqueId);
        });
        updateFriendCounter();
    }

    @Subscribe
    public void onLabyConnectChatMessage(LabyConnectChatMessageEvent event) {
        UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().chat().getId().equals(chatId);
        });
    }

    @Subscribe
    public void onLabyConnectChatMessage(LabyConnectChatMessageDeleteEvent event) {
        UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().chat().getId().equals(chatId);
        });
    }

    @Subscribe
    public void onLabyConnectChatMessageRead(LabyConnectChatMessageReadEvent event) {
        UUID chatId = event.chat().getId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().chat().getId().equals(chatId);
        });
    }

    @Subscribe
    public void onUserUpdateData(UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            UUID uniqueId = event.gameUser().getUniqueId();
            this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
                return widget.friend().getUniqueId().equals(uniqueId);
            });
        });
    }

    @Subscribe
    public void onLabyConnectFriendStatus(LabyConnectFriendStatusEvent event) {
        UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().getUniqueId().equals(uniqueId);
        });
        updateFriendCounter();
    }

    @Subscribe
    public void onLabyConnectUpdateSetting(LabyConnectUpdateSettingEvent event) {
        reload();
    }

    @Subscribe
    public void onLabyConnectFriendServer(LabyConnectFriendServerEvent event) {
        UUID uniqueId = event.friend().getUniqueId();
        this.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
            return widget.friend().getUniqueId().equals(uniqueId);
        });
    }

    @Subscribe
    public void onUserPinEvent(final LabyConnectFriendPinUpdateEvent event) {
        this.labyAPI.minecraft().executeOnRenderThread(new Runnable(this) { // from class: net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.LabyConnectFriendsActivity.1
            final /* synthetic */ LabyConnectFriendsActivity this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                UUID uniqueId = event.friend().getUniqueId();
                this.this$0.listFriends.reInitializeChildrenIf(LabyConnectFriendWidget.class, widget -> {
                    return widget.friend().getUniqueId().equals(uniqueId);
                });
            }
        });
    }

    public ListSession<LabyConnectEntryWidget> session() {
        return SESSION;
    }

    private void addFriend(@NotNull Friend friend) {
        if (!isUserInFilter(friend)) {
            return;
        }
        this.listFriends.addChildAsync(new LabyConnectFriendWidget(this.contentDisplay, friend));
    }

    public void openChat(UUID uuid) {
        Friend friend;
        LabyConnectSession session = this.labyAPI.labyConnect().getSession();
        if (session == null || (friend = session.getFriend(uuid)) == null) {
            return;
        }
        LabyConnectFriendWidget widget = new LabyConnectFriendWidget(this.contentDisplay, friend);
        if (this.contentDisplay != null) {
            widget.select();
        }
        SESSION.setSelectedEntry(widget);
    }

    public void openOutgoingRequests() {
        openOutgoingRequests("");
    }

    public void openOutgoingRequests(String username) {
        displayScreen(new LabyConnectOutgoingRequestsActivity(username));
    }

    public void openSettings() {
        ConfigProvider<LabyConnectConfigAccessor> configProvider = this.labyAPI.labyConnect().configProvider();
        AbstractSettingRegistry labyconnect = ((LabyConnectConfigAccessor) configProvider.get()).asRegistry("labyconnect");
        labyconnect.initialize();
        Activity activity = labyconnect.createActivityLazy();
        activity.addInitializeRunnable(() -> {
            activity.addStyle("activity/labyconnect/laby-connect-settings.lss");
        });
        activity.addCloseRunnable(() -> {
            configProvider.save();
        });
        this.contentDisplay.displayScreen(activity);
        SESSION.setSelectedEntry(null);
    }
}
