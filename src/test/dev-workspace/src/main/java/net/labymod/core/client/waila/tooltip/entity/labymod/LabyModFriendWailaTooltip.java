package net.labymod.core.client.waila.tooltip.entity.labymod;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/labymod/LabyModFriendWailaTooltip.class */
public class LabyModFriendWailaTooltip extends ComponentEntityWailaTooltip {
    private final WailaHudWidget widget;

    public LabyModFriendWailaTooltip(WailaHudWidget widget) {
        super("labymod/friend");
        this.widget = widget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip
    @Nullable
    protected Component createComponent(Entity entity) {
        LabyConnectSession session;
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).labyModFriend().get().booleanValue() || !(entity instanceof Player)) {
            return null;
        }
        Player player = (Player) entity;
        LabyConnect connect = Laby.labyAPI().labyConnect();
        if (connect == null || (session = connect.getSession()) == null) {
            return null;
        }
        Friend friend = session.getFriend(player.getUniqueId());
        if (friend == null) {
            return null;
        }
        return Component.text("Friend", NamedTextColor.GOLD);
    }
}
