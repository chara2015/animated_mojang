package net.labymod.core.client.waila.tooltip.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.item.ArmorStand;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity.HealthStatusWidget;
import net.labymod.api.client.waila.tooltip.EntityWailaTooltip;
import net.labymod.api.util.HealthStatus;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/EntityHealthWailaTooltip.class */
public class EntityHealthWailaTooltip extends EntityWailaTooltip {
    private final WailaHudWidget widget;

    public EntityHealthWailaTooltip(WailaHudWidget widget) {
        super("health");
        this.widget = widget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.waila.tooltip.EntityWailaTooltip
    @Nullable
    protected Widget createWidget(Entity entity) {
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).entityHealth().get().booleanValue() || !(entity instanceof LivingEntity)) {
            return null;
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        if (entity instanceof ArmorStand) {
            return null;
        }
        return new HealthStatusWidget(HealthStatus.of(livingEntity));
    }
}
