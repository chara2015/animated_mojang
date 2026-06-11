package net.labymod.core.client.gui.screen.activity.activities.worldsharing;

import java.util.Objects;
import java.util.function.Function;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.DefaultComponentTab;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.FriendInviteActivity;
import net.labymod.core.client.gui.screen.activity.activities.worldsharing.tabs.PlayerActivity;
import net.labymod.core.client.worldsharing.network.NetEventHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/worldsharing/LocalInviteActivity.class */
@AutoActivity
@Link("activity/worldsharing/dashboard.lss")
public class LocalInviteActivity extends BoxedTabbedActivity {
    private static final String I18N_PREFIX = "labymod.activity.worldsharing.";

    public LocalInviteActivity(NetEventHandler netEventHandler) {
        Function<Friend, Boolean> alreadyInvitedFunction = friend -> {
            return Boolean.valueOf(netEventHandler.isLocalPlayer(friend.getUniqueId()));
        };
        Function<Friend, Boolean> canInviteFunction = friend2 -> {
            return Boolean.valueOf(netEventHandler.isOnLocalServer());
        };
        Objects.requireNonNull(netEventHandler);
        FriendInviteActivity localFriendInviteActivity = new FriendInviteActivity(netEventHandler::inviteToLocalServer, alreadyInvitedFunction, canInviteFunction);
        register("invite", new DefaultComponentTab(Component.translatable("labymod.activity.worldsharing.menu.invite", new Component[0]), localFriendInviteActivity));
        register("players", new DefaultComponentTab(Component.translatable("labymod.activity.worldsharing.menu.players", new Component[0]), new PlayerActivity(netEventHandler, false)));
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.worldsharing.BoxedTabbedActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget container = (FlexibleContentWidget) new FlexibleContentWidget().addId("container");
        HorizontalListWidget header = (HorizontalListWidget) new HorizontalListWidget().addId("header");
        ComponentWidget title = ComponentWidget.i18n("labymod.activity.worldsharing.menu.invite_friends");
        title.addId("title");
        header.addEntry(title);
        container.addContent(header);
        container.addContent(this.tabMenu);
        container.addContent(this.prevScreenRenderer);
        container.addContent(this.screenRenderer);
        FlexibleContentWidget bottomBar = (FlexibleContentWidget) new FlexibleContentWidget().addId("bottom-bar");
        ButtonWidget closeButton = ButtonWidget.i18n("labymod.ui.button.close");
        closeButton.addId("close-button");
        closeButton.setActionListener(this::displayPreviousScreen);
        bottomBar.addContent(closeButton);
        container.addContent(bottomBar);
        ((Document) this.document).addChild(container);
    }
}
