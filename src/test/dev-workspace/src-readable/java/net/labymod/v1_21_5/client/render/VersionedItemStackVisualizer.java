package net.labymod.v1_21_5.client.render;

import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_5.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/render/VersionedItemStackVisualizer.class */
@Singleton
@Implements(ItemStackVisualizer.class)
public class VersionedItemStackVisualizer implements ItemStackVisualizer {
    @Override // net.labymod.api.client.render.ItemStackVisualizer
    public void submitItem(ScreenContext context, ItemStack itemStack, int x, int y, boolean decorate) {
        ftk currentGuiGraphics = MinecraftUtil.getCurrentGuiGraphics();
        currentGuiGraphics.b((dak) CastUtil.cast(itemStack), x, y);
        if (decorate) {
            currentGuiGraphics.a(fqq.Q().h, (dak) CastUtil.cast(itemStack), x, y);
        }
    }
}
