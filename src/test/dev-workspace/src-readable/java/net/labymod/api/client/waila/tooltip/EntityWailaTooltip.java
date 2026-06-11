package net.labymod.api.client.waila.tooltip;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.waila.Waila;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tooltip/EntityWailaTooltip.class */
public abstract class EntityWailaTooltip extends WailaTooltip {
    @Nullable
    protected abstract Widget createWidget(Entity entity);

    public EntityWailaTooltip(String id) {
        super("entity/" + id);
    }

    @Override // net.labymod.api.client.waila.tooltip.WailaTooltip
    @Nullable
    public final Widget createWidget() {
        Waila<?> waila = getWaila();
        if (waila == null) {
            return null;
        }
        Object object = waila.getValue();
        if (!(object instanceof Entity)) {
            return null;
        }
        Entity entity = (Entity) object;
        return createWidget(entity);
    }
}
