package net.labymod.api.client.world.item;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.component.data.BuiltinDataComponents;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/ItemStack.class */
public interface ItemStack extends Item {
    @NotNull
    Item getAsItem();

    int getCurrentDamageValue();

    int getUseDuration(LivingEntity livingEntity);

    boolean isSword();

    boolean isItem();

    boolean isBlock();

    boolean isFood();

    boolean isFishingTool();

    int getSize();

    void setSize(int i);

    Component getDisplayName();

    @NotNull
    DataComponentContainer getDataComponentContainer();

    @NotNull
    DataComponentContainer getOrCreateDataComponentContainer();

    @Deprecated(forRemoval = true, since = "4.1.23")
    @Nullable
    NBTTagCompound getNBTTag();

    @Deprecated(forRemoval = true, since = "4.1.23")
    @NotNull
    NBTTagCompound getOrCreateNBTTag();

    boolean matches(ItemStack itemStack);

    ItemStack copy();

    boolean isCorrectTool(BlockState blockState);

    @Deprecated(forRemoval = true, since = "4.2.14")
    default int getUseDuration() {
        return getUseDuration(null);
    }

    default int foodNutrition() {
        NBTTag<?> tag;
        try {
            if (!isFood()) {
                return 0;
            }
            NBTTagCompound food = (NBTTagCompound) getDataComponentContainer().get(BuiltinDataComponents.FOOD);
            if (food == null || (tag = food.get("nutrition")) == null) {
                return 0;
            }
            return ((Integer) tag.value()).intValue();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    default float foodSaturationValue() {
        NBTTag<?> tag;
        try {
            if (!isFood()) {
                return 0.0f;
            }
            NBTTagCompound food = (NBTTagCompound) getDataComponentContainer().get(BuiltinDataComponents.FOOD);
            if (food == null || (tag = food.get("saturation")) == null) {
                return 0.0f;
            }
            return ((Float) tag.value()).floatValue();
        } catch (NullPointerException e) {
            return 0.0f;
        }
    }

    default boolean isShield() {
        return getIdentifier().equals(VanillaItems.SHIELD.identifier());
    }

    default boolean isBow() {
        return getIdentifier().equals(VanillaItems.BOW.identifier());
    }

    default boolean hasDataComponentContainer() {
        return getDataComponentContainer() != DataComponentContainer.EMPTY;
    }

    default void applyPatchToDataComponent(@NotNull DataComponentPatch patch) {
        DataComponentContainer destination = getOrCreateDataComponentContainer();
        destination.apply(patch);
    }

    @Deprecated(forRemoval = true, since = "4.1.23")
    default boolean hasNBTTag() {
        return getNBTTag() != null;
    }

    @Deprecated(forRemoval = true, since = "4.1.23")
    default void setNBTTag(NBTTagCompound source) {
        NBTTagCompound destination = getOrCreateNBTTag();
        destination.clear();
        for (String key : source.keySet()) {
            NBTTag<?> value = source.get(key);
            if (value != null) {
                destination.set(key, value);
            }
        }
    }

    default int getLegacyItemData() {
        return -1;
    }

    default void setLegacyItemData(int legacyData) {
    }

    default ItemData toItemData() {
        return ItemData.from(this);
    }

    default boolean isItem(VanillaItem item) {
        return getIdentifier().equals(item.identifier());
    }

    static boolean matches(ItemStack itemStack1, ItemStack itemStack2) {
        return itemStack1 == itemStack2 || (itemStack1 != null && itemStack1.matches(itemStack2));
    }

    static boolean isSameItem(ItemStack itemStack1, ItemStack itemStack2) {
        return Objects.equals(itemStack1, itemStack2) || itemStack1.getAsItem() == itemStack2.getAsItem();
    }

    static boolean isSameItemSameTags(ItemStack itemStack1, ItemStack itemStack2) {
        if (Objects.equals(itemStack1, itemStack2)) {
            return true;
        }
        DataComponentContainer dataComponentContainer1 = itemStack1.getDataComponentContainer();
        DataComponentContainer dataComponentContainer22 = itemStack2.getDataComponentContainer();
        return isSameItem(itemStack1, itemStack2) && Objects.equals(dataComponentContainer1, dataComponentContainer22);
    }

    @ApiStatus.Experimental
    default boolean areComponentsBound() {
        return true;
    }
}
