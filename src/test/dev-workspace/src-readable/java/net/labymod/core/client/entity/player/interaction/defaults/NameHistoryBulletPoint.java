package net.labymod.core.client.entity.player.interaction.defaults;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab.NameHistoryActivity;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/defaults/NameHistoryBulletPoint.class */
public class NameHistoryBulletPoint extends AbstractBulletPoint {
    public NameHistoryBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.nameHistory", new Component[0]));
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        String name = player.getName();
        NameHistoryActivity activity = LabyMod.references().nameHistoryActivity();
        activity.scheduleQuery(name);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
}
