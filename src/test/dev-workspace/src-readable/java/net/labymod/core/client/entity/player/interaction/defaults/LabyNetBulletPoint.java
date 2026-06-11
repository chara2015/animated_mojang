package net.labymod.core.client.entity.player.interaction.defaults;

import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.api.models.OperatingSystem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/defaults/LabyNetBulletPoint.class */
public class LabyNetBulletPoint extends AbstractBulletPoint {
    public LabyNetBulletPoint() {
        super(Component.translatable("labymod.activity.interactionMenu.entry.labynetProfile", new Component[0]));
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        OperatingSystem.getPlatform().openUrl(String.format(Locale.ROOT, Constants.Urls.LABYNET_PROFILE_NAME, player.getName()));
    }
}
