package net.labymod.v1_21_3.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_3.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        fns currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.b((cxp) CastUtil.cast(itemStack), x, y);
        if (decorate) {
            currentGuiGraphics.a(fmg.Q().h, (cxp) CastUtil.cast(itemStack), x, y);
        }
    }
}
