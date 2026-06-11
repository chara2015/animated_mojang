package net.labymod.v1_8_9.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        ave minecraft = ave.A();
        avc.c();
        bjh itemRenderer = minecraft.ag();
        zx mcItemStack = (zx) CastUtil.cast(itemStack);
        itemRenderer.b(mcItemStack, x, y);
        if (decorate) {
            itemRenderer.a(minecraft.k, mcItemStack, x, y, (String) null);
        }
        avc.a();
    }
}
