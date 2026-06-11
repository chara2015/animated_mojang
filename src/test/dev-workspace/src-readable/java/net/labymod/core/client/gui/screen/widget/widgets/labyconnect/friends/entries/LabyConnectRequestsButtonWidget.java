package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries;

import java.util.List;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections.requests.LabyConnectIncomingRequestsActivity;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/friends/entries/LabyConnectRequestsButtonWidget.class */
@AutoWidget
public class LabyConnectRequestsButtonWidget extends LabyConnectEntryWidget {
    private final ComponentWidget widgetName;
    private final List<IncomingFriendRequest> requests;

    public LabyConnectRequestsButtonWidget(ParentScreen contentDisplay, List<IncomingFriendRequest> requests) {
        super(contentDisplay);
        this.requests = requests;
        this.widgetName = ComponentWidget.empty();
        this.widgetName.addId("text");
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public boolean onPress() {
        super.onPress();
        return false;
    }

    private void updateWidgetName(int amount) {
        Component component = Component.translatable("labymod.activity.labyconnect.friends.requests", new Component[0]).color(NamedTextColor.GRAY).arguments(Component.text(Integer.valueOf(amount)).color(NamedTextColor.YELLOW));
        this.widgetName.setComponent(component);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        updateWidgetName(this.requests.size());
        IconWidget widgetHead = new IconWidget(Textures.SpriteCommon.MULTIPLAYER);
        widgetHead.addId("icon");
        addChild(widgetHead);
        addChild(this.widgetName);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.LabyConnectEntryWidget
    public void select() {
        displayContentActivity(new LabyConnectIncomingRequestsActivity());
    }

    public void setAmount(int size) {
        updateWidgetName(size);
    }

    @Override // net.labymod.api.client.gui.screen.widget.Widget
    public int getSortingValue() {
        return Integer.MIN_VALUE;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        return obj instanceof LabyConnectRequestsButtonWidget;
    }
}
