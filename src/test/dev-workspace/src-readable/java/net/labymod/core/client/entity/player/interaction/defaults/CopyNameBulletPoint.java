package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/defaults/CopyNameBulletPoint.class */
public class CopyNameBulletPoint extends AbstractBulletPoint {
    public CopyNameBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.copyUsername", new Component[0]));
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        String name = player.getName();
        Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(name);
        NotificationController notificationController = Laby.labyAPI().notificationController();
        notificationController.push(Notification.builder().title(Component.translatable("labymod.notification.copied", new Component[0])).text(Component.text(name)).duration(4000L).build());
    }
}
