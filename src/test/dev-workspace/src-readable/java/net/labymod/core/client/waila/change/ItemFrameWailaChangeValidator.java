package net.labymod.core.client.waila.change;

import java.util.Objects;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.decoration.ItemFrame;
import net.labymod.api.client.waila.Waila;
import net.labymod.api.client.waila.WailaEntity;
import net.labymod.api.client.waila.change.WailaChangeValidator;
import net.labymod.api.client.world.item.Item;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/change/ItemFrameWailaChangeValidator.class */
public class ItemFrameWailaChangeValidator implements WailaChangeValidator {
    private ItemFrame currentItemFrame;
    private Item currentFramedIem;

    @Override // net.labymod.api.client.waila.change.WailaChangeValidator
    public boolean checkForChange(Waila<?> waila) {
        if (!(waila instanceof WailaEntity)) {
            reset();
            return false;
        }
        WailaEntity wailaEntity = (WailaEntity) waila;
        Entity entity = wailaEntity.getValue();
        if (!(entity instanceof ItemFrame)) {
            reset();
            return false;
        }
        ItemFrame itemFrame = (ItemFrame) entity;
        if (needsUpdate(itemFrame)) {
            this.currentItemFrame = itemFrame;
            this.currentFramedIem = itemFrame.getItem();
            return true;
        }
        return false;
    }

    private boolean needsUpdate(ItemFrame itemFrame) {
        return (Objects.equals(this.currentItemFrame, itemFrame) && Objects.equals(this.currentFramedIem, itemFrame.getItem())) ? false : true;
    }

    private void reset() {
        this.currentItemFrame = null;
        this.currentFramedIem = null;
    }
}
