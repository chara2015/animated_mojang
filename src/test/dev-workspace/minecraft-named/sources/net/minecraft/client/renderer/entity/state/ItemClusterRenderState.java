package net.minecraft.client.renderer.entity.state;

import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/ItemClusterRenderState.class */
public class ItemClusterRenderState extends EntityRenderState {
    public final ItemStackRenderState item = new ItemStackRenderState();
    public int count;
    public int seed;

    public void extractItemGroupRenderState(Entity $$0, ItemStack $$1, ItemModelResolver $$2) {
        $$2.updateForNonLiving(this.item, $$1, ItemDisplayContext.GROUND, $$0);
        this.count = getRenderedAmount($$1.getCount());
        this.seed = getSeedForItemStack($$1);
    }

    public static int getSeedForItemStack(ItemStack $$0) {
        if ($$0.isEmpty()) {
            return 187;
        }
        return Item.getId($$0.getItem()) + $$0.getDamageValue();
    }

    public static int getRenderedAmount(int $$0) {
        if ($$0 <= 1) {
            return 1;
        }
        if ($$0 <= 16) {
            return 2;
        }
        if ($$0 <= 32) {
            return 3;
        }
        if ($$0 <= 48) {
            return 4;
        }
        return 5;
    }
}
