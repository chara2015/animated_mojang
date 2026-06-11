package net.labymod.core.client.waila.tooltip.entity;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/EntityNameWailaTooltip.class */
public class EntityNameWailaTooltip extends ComponentEntityWailaTooltip {
    public EntityNameWailaTooltip() {
        super("name");
    }

    @Override // net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip
    @Nullable
    protected Component createComponent(Entity entity) {
        return entity.nameComponent();
    }
}
