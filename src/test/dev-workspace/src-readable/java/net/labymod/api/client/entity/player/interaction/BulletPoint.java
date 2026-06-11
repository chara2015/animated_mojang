package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.icon.Icon;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/interaction/BulletPoint.class */
public interface BulletPoint {
    Component getTitle();

    Icon getIcon();

    void execute(Player player);

    default boolean isVisible(Player playerInfo) {
        return true;
    }
}
