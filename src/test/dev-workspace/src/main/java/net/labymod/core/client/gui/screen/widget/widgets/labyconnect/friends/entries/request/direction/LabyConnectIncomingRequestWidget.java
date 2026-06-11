package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.direction;

import java.util.Objects;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/friends/entries/request/direction/LabyConnectIncomingRequestWidget.class */
@AutoWidget
public class LabyConnectIncomingRequestWidget extends LabyConnectRequestWidget<IncomingFriendRequest> {
    public LabyConnectIncomingRequestWidget(ParentScreen contentDisplay, IncomingFriendRequest request) {
        super(contentDisplay, request);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.request.LabyConnectRequestWidget
    protected void initializeButtons(DivWidget container) {
        ButtonWidget widgetAccept = ButtonWidget.icon(Textures.SpriteCommon.SMALL_CHECKED);
        widgetAccept.addId("action", "accept");
        IncomingFriendRequest incomingFriendRequest = (IncomingFriendRequest) this.request;
        Objects.requireNonNull(incomingFriendRequest);
        widgetAccept.setPressable(incomingFriendRequest::accept);
        container.addChild(widgetAccept);
        ButtonWidget widgetDecline = ButtonWidget.icon(Textures.SpriteCommon.SMALL_X_WITH_SHADOW);
        widgetDecline.addId("action", "decline");
        IncomingFriendRequest incomingFriendRequest2 = (IncomingFriendRequest) this.request;
        Objects.requireNonNull(incomingFriendRequest2);
        widgetDecline.setPressable(incomingFriendRequest2::decline);
        container.addChild(widgetDecline);
    }
}
