package net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/tabs/FriendInviteActivity.class */
@AutoActivity
public class FriendInviteActivity extends AbstractPlayerActivity {
    private final Consumer<Friend> inviteCallback;
    private final Function<Friend, Boolean> isInvitedFunction;
    private final Function<Friend, Boolean> canInviteFunction;
    private String searchQuery = "";

    public FriendInviteActivity(Consumer<Friend> inviteCallback, Function<Friend, Boolean> isInvitedFunction, Function<Friend, Boolean> canInviteFunction) {
        this.inviteCallback = inviteCallback;
        this.isInvitedFunction = isInvitedFunction;
        this.canInviteFunction = canInviteFunction;
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.AbstractPlayerActivity
    protected void buildHeader(HorizontalListWidget header) {
        TextFieldWidget searchInput = new TextFieldWidget();
        searchInput.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        searchInput.updateListener(query -> {
            this.searchQuery = query.toLowerCase();
            fillList(this.list, true);
        });
        header.addEntry(searchInput);
        this.searchQuery = "";
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.AbstractPlayerActivity
    protected void fillList(VerticalListWidget<Widget> list, boolean initialized) {
        if (this.list == null) {
            return;
        }
        List<Widget> widgets = new ArrayList<>();
        list.getChildren().clear();
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session != null) {
            List<Friend> friends = new ArrayList<>();
            List<FriendWidget> friendWidgets = new ArrayList<>();
            boolean isEmpty = true;
            for (Friend friend : session.getFriends()) {
                if (this.searchQuery.isBlank() || friend.getName().toLowerCase().contains(this.searchQuery)) {
                    friends.add(friend);
                    if (friend.isOnline()) {
                        isEmpty = false;
                        friendWidgets.add(new FriendWidget(this, friend));
                    }
                }
            }
            friendWidgets.sort(Comparator.comparingLong((v0) -> {
                return v0.getLastOnline();
            }));
            widgets.addAll(friendWidgets);
            if (isEmpty || friends.isEmpty()) {
                ComponentWidget emptyText = ComponentWidget.i18n("labymod.activity.worldsharing.menu.no_friends_found");
                emptyText.addId("empty-text");
                widgets.add(emptyText);
            }
        } else {
            ComponentWidget offlineText = ComponentWidget.i18n("labymod.labyconnect.notConnected");
            offlineText.addId("empty-text");
            if (initialized) {
                list.addChildInitialized(offlineText);
            } else {
                list.addChild(offlineText);
            }
        }
        if (initialized) {
            list.addChildrenInitialized(widgets, true);
        } else {
            list.addChildren(widgets, true);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/tabs/FriendInviteActivity$FriendWidget.class */
    private class FriendWidget extends HorizontalListWidget {
        public final Friend friend;

        private FriendWidget(FriendInviteActivity friendInviteActivity, Friend friend) {
            this.friend = friend;
            addId("entry");
            String username = friend.getName();
            boolean alreadyInvited = friendInviteActivity.isInvitedFunction != null && friendInviteActivity.isInvitedFunction.apply(friend).booleanValue();
            boolean canInvite = (alreadyInvited || friendInviteActivity.canInviteFunction == null || !friendInviteActivity.canInviteFunction.apply(friend).booleanValue()) ? false : true;
            addEntry(new IconWidget(friend.chat().icon()));
            ComponentWidget nameWidget = ComponentWidget.component(Component.text(username, friend.gameUser().displayColor()));
            addEntry(nameWidget);
            ButtonWidget inviteButton = ButtonWidget.i18n("labymod.activity.worldsharing." + (alreadyInvited ? "menu.invited" : "menu.invite"));
            inviteButton.addId("invite");
            inviteButton.setEnabled(canInvite);
            inviteButton.setActionListener(() -> {
                if (!alreadyInvited) {
                    inviteButton.updateComponent(Component.translatable("labymod.activity.worldsharing.menu.invited", new Component[0]));
                    inviteButton.setEnabled(false);
                    friendInviteActivity.inviteCallback.accept(friend);
                }
            });
            addEntry(inviteButton);
        }

        public long getLastOnline() {
            return this.friend.getLastOnline();
        }
    }
}
