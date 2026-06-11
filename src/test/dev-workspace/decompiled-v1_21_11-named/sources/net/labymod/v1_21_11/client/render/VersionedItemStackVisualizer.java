package net.labymod.v1_21_11.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        GuiGraphics currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        net.minecraft.world.item.ItemStack mcItemStack = MinecraftUtil.toMinecraft(itemStack);
        currentGuiGraphics.renderFakeItem(mcItemStack, x, y);
        if (decorate) {
            currentGuiGraphics.renderItemDecorations(Minecraft.getInstance().font, mcItemStack, x, y);
        }
    }
}
