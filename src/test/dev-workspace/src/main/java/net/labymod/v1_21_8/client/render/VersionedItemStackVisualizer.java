package net.labymod.v1_21_8.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_8.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        fxb currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.b((dcv) CastUtil.cast(itemStack), x, y);
        if (decorate) {
            currentGuiGraphics.a(fue.R().h, (dcv) CastUtil.cast(itemStack), x, y);
        }
    }
}
