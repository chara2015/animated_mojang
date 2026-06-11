package net.labymod.api.client.world.item;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/DeferredItemStack.class */
public interface DeferredItemStack extends ItemStack {
    ItemStack get();

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    default Item getAsItem() {
        return get().getAsItem();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default int getCurrentDamageValue() {
        return get().getCurrentDamageValue();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default int getUseDuration(LivingEntity entity) {
        return get().getUseDuration(entity);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isSword() {
        return get().isSword();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isItem() {
        return get().isItem();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isBlock() {
        return get().isBlock();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isFood() {
        return get().isFood();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isFishingTool() {
        return get().isFishingTool();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default int getSize() {
        return get().getSize();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default void setSize(int size) {
        get().setSize(size);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default Component getDisplayName() {
        return get().getDisplayName();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    default DataComponentContainer getDataComponentContainer() {
        return get().getDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    default DataComponentContainer getOrCreateDataComponentContainer() {
        return get().getOrCreateDataComponentContainer();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @Nullable
    default NBTTagCompound getNBTTag() {
        return get().getNBTTag();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    @NotNull
    default NBTTagCompound getOrCreateNBTTag() {
        return get().getOrCreateNBTTag();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean matches(ItemStack itemStack) {
        return get().matches(itemStack);
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default ItemStack copy() {
        return get().copy();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean isCorrectTool(BlockState state) {
        return get().isCorrectTool(state);
    }

    @Override // net.labymod.api.client.world.item.Item
    default ResourceLocation getIdentifier() {
        return get().getIdentifier();
    }

    @Override // net.labymod.api.client.world.item.Item
    default int getMaximumStackSize() {
        return get().getMaximumStackSize();
    }

    @Override // net.labymod.api.client.world.item.Item
    default int getMaximumDamage() {
        return get().getMaximumDamage();
    }

    @Override // net.labymod.api.client.world.item.Item
    default boolean isAir() {
        return get().isAir();
    }

    @Override // net.labymod.api.client.world.item.ItemStack
    default boolean areComponentsBound() {
        return get().areComponentsBound();
    }
}
