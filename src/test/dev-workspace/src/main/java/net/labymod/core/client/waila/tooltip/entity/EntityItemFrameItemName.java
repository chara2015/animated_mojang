package net.labymod.core.client.waila.tooltip.entity;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/EntityItemFrameItemName.class */
public class EntityItemFrameItemName extends ComponentEntityWailaTooltip {
    public EntityItemFrameItemName() {
        super("item-frame_item");
    }

    @Override // net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip
    @Nullable
    protected Component createComponent(Entity entity) {
        if (!(entity instanceof ItemFrame)) {
            return null;
        }
        ItemFrame itemFrame = (ItemFrame) entity;
        ItemStack item = itemFrame.getItem();
        if (item != null && !item.isAir()) {
            return item.getDisplayName();
        }
        return null;
    }
}
