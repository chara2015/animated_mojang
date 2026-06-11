package net.labymod.api.client.render;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/ItemStackRenderer.class */
@Deprecated(forRemoval = true, since = "4.4.0")
public interface ItemStackRenderer {
    public static final ItemStackRenderer NOP = new NOPItemStackRenderer();

    void renderItemStack(Stack stack, ItemStack itemStack, int i, int i2, boolean z, float f);

    default void renderItemStack(Stack stack, ItemStack itemStack, int x, int y) {
        renderItemStack(stack, itemStack, x, y, true);
    }

    default void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate) {
        float alpha = Laby.labyAPI().renderPipeline().getAlpha();
        if (alpha == 0.0f) {
            return;
        }
        renderItemStack(stack, itemStack, x, y, decorate, alpha);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/ItemStackRenderer$NOPItemStackRenderer.class */
    public static class NOPItemStackRenderer implements ItemStackRenderer {
        @Override // net.labymod.api.client.render.ItemStackRenderer
        public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        }
    }
}
