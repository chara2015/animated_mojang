package net.labymod.core.client.waila.tooltip.entity.labymod;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.group.Group;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/labymod/LabyModGroupWailaTooltip.class */
public class LabyModGroupWailaTooltip extends ComponentEntityWailaTooltip {
    private final WailaHudWidget widget;

    public LabyModGroupWailaTooltip(WailaHudWidget widget) {
        super("labymod/group");
        this.widget = widget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip
    @Nullable
    protected Component createComponent(Entity entity) {
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).labyModGroup().get().booleanValue() || !(entity instanceof Player)) {
            return null;
        }
        Player player = (Player) entity;
        GameUser user = player.gameUser();
        Group group = user.visibleGroup();
        if (group.isDefault()) {
            return null;
        }
        return Component.text(group.getDisplayName(), TextColor.color(group.getColor().getRGB()));
    }
}
