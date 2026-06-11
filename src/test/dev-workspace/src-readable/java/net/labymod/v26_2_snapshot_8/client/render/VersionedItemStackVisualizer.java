package net.labymod.v26_2_snapshot_8.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        GuiGraphicsExtractor currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        net.minecraft.world.item.ItemStack mcItemStack = MinecraftUtil.toMinecraft(itemStack);
        currentGuiGraphics.fakeItem(mcItemStack, x, y);
        if (decorate) {
            currentGuiGraphics.itemDecorations(Minecraft.getInstance().font, mcItemStack, x, y);
        }
    }
}
