package net.labymod.v1_12_2.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        bib minecraft = bib.z();
        bhz.c();
        bzw itemRenderer = minecraft.ad();
        aip mcItemStack = (aip) CastUtil.cast(itemStack);
        itemRenderer.b(mcItemStack, x, y);
        if (decorate) {
            itemRenderer.a(minecraft.k, mcItemStack, x, y, (String) null);
        }
        bhz.a();
    }
}
