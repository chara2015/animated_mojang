package net.labymod.core.client.waila.tooltip.entity;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.waila.tooltip.EntityWailaTooltip;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/ComponentEntityWailaTooltip.class */
public abstract class ComponentEntityWailaTooltip extends EntityWailaTooltip {
    @Nullable
    protected abstract Component createComponent(Entity entity);

    protected ComponentEntityWailaTooltip(String id) {
        super(id);
    }

    @Override // net.labymod.api.client.waila.tooltip.EntityWailaTooltip
    @Nullable
    protected Widget createWidget(Entity entity) {
        Component component = createComponent(entity);
        if (component == null) {
            return null;
        }
        return ComponentWidget.component(component);
    }
}
