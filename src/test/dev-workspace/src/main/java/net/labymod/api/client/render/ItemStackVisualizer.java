package net.labymod.api.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/ItemStackVisualizer.class */
@Referenceable
public interface ItemStackVisualizer {
    void submitItem(ScreenContext screenContext, ItemStack itemStack, int i, int i2, boolean z);

    default void submitItem(ScreenContext context, ItemStack itemStack, int x, int y) {
        submitItem(context, itemStack, x, y, true);
    }
}
