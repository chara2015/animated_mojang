package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests;

import java.util.List;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.login.LabyConnectIncomingFriendRequestAddBulkEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestRemoveEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction.LabyConnectIncomingRequestWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/requests/LabyConnectIncomingRequestsActivity.class */
@Link("activity/labyconnect/laby-connect-incoming-requests.lss")
@AutoActivity
public class LabyConnectIncomingRequestsActivity extends Activity {
    private VerticalListWidget<LabyConnectIncomingRequestWidget> listRequests;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        List<IncomingFriendRequest> incomingRequests;
        super.initialize(parent);
        LabyConnect labyConnect = this.labyAPI.labyConnect();
        if (!labyConnect.isAuthenticated()) {
            return;
        }
        LabyConnectSession session = labyConnect.getSession();
        if (session == null) {
            incomingRequests = List.of();
        } else {
            incomingRequests = session.getIncomingRequests();
        }
        List<IncomingFriendRequest> friendRequests = incomingRequests;
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("container");
        DivWidget header = new DivWidget();
        header.addId("header");
        ComponentWidget title = ComponentWidget.i18n("labymod.activity.labyconnect.incomingRequests.title");
        title.addId("title");
        header.addChild(title);
        container.addContent(header);
        DivWidget listContainer = new DivWidget();
        listContainer.addId("list-container");
        this.listRequests = new VerticalListWidget<>();
        this.listRequests.addId("list");
        for (IncomingFriendRequest friendRequest : friendRequests) {
            this.listRequests.addChild(new LabyConnectIncomingRequestWidget(this, friendRequest));
        }
        ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) this.listRequests);
        scrollWidget.addId("scroll");
        listContainer.addChild(scrollWidget);
        container.addFlexibleContent(listContainer);
        ((Document) this.document).addChild(container);
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAdd(LabyConnectIncomingFriendRequestAddEvent event) {
        addFriendRequest(event.request());
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestAddBulk(LabyConnectIncomingFriendRequestAddBulkEvent event) {
        for (IncomingFriendRequest friendRequest : event.getRequests()) {
            addFriendRequest(friendRequest);
        }
    }

    @Subscribe
    public void onLabyConnectIncomingFriendRequestRemove(LabyConnectIncomingFriendRequestRemoveEvent event) {
        this.listRequests.removeChildIf(LabyConnectIncomingRequestWidget.class, request -> {
            return request.getRequest().getUniqueId().equals(event.request().getUniqueId());
        });
    }

    private void addFriendRequest(IncomingFriendRequest request) {
        List<Widget> widget = this.listRequests.findChildrenIf(child -> {
            return child.getRequest().equals(request);
        });
        if (widget != null && !widget.isEmpty()) {
            return;
        }
        this.listRequests.addChildAsync(new LabyConnectIncomingRequestWidget(this, request));
    }
}
