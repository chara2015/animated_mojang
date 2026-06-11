package net.labymod.v1_21_11.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.v1_21_11.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        gir currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        dlt mcItemStack = MinecraftUtil.toMinecraft(itemStack);
        currentGuiGraphics.b(mcItemStack, x, y);
        if (decorate) {
            currentGuiGraphics.a(gfj.V().g, mcItemStack, x, y);
        }
    }
}
