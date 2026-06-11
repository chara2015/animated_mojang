package net.labymod.core.client.entity.player.interaction.defaults;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.core.client.gui.navigation.elements.LabyConnectNavigationElement;
import net.labymod.core.client.gui.screen.activity.activities.labyconnect.LabyConnectActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/defaults/AddFriendBulletPoint.class */
public class AddFriendBulletPoint extends AbstractBulletPoint {
    public AddFriendBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.addFriend", new Component[0]));
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            return;
        }
        String username = player.getName();
        LabyConnectActivity activity = LabyConnectNavigationElement.ACTIVITY;
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
        Task.builder(() -> {
            Laby.labyAPI().minecraft().executeNextTick(() -> {
                activity.desktopActivity().friendsActivity().openOutgoingRequests(username);
            });
        }).delay(100L, TimeUnit.MILLISECONDS).build().execute();
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public boolean isVisible(Player player) {
        LabyConnectSession session;
        UUID target = player.getUniqueId();
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        return labyConnect.state() == LabyConnectState.PLAY && (session = labyConnect.getSession()) != null && session.getFriend(target) == null;
    }
}
