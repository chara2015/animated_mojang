package net.labymod.v26_1_1.client.renderer;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/renderer/ItemStackRenderStateAccessor.class */
public interface ItemStackRenderStateAccessor {
    boolean labyMod$isLeftHand();

    ItemDisplayContext labyMod$getItemDisplayContext();

    void labyMod$setLivingEntity(LivingEntity livingEntity);

    LivingEntity labyMod$getLivingEntity();

    void labyMod$setItemStack(ItemStack itemStack);

    ItemStack labyMod$getItemStack();
}
