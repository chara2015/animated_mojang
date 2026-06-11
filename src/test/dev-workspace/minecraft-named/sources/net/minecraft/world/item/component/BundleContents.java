package net.minecraft.world.item.component;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.apache.commons.lang3.math.Fraction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/BundleContents.class */
public final class BundleContents implements TooltipComponent {
    public static final BundleContents EMPTY = new BundleContents(List.of());
    public static final Codec<BundleContents> CODEC = ItemStack.CODEC.listOf().flatXmap(BundleContents::checkAndCreate, $$0 -> {
        return DataResult.success($$0.items);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, BundleContents> STREAM_CODEC = ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()).map(BundleContents::new, $$0 -> {
        return $$0.items;
    });
    private static final Fraction BUNDLE_IN_BUNDLE_WEIGHT = Fraction.getFraction(1, 16);
    private static final int NO_STACK_INDEX = -1;
    public static final int NO_SELECTED_ITEM_INDEX = -1;
    final List<ItemStack> items;
    final Fraction weight;
    final int selectedItem;

    BundleContents(List<ItemStack> $$0, Fraction $$1, int $$2) {
        this.items = $$0;
        this.weight = $$1;
        this.selectedItem = $$2;
    }

    private static DataResult<BundleContents> checkAndCreate(List<ItemStack> $$0) {
        try {
            Fraction $$1 = computeContentWeight($$0);
            return DataResult.success(new BundleContents($$0, $$1, -1));
        } catch (ArithmeticException e) {
            return DataResult.error(() -> {
                return "Excessive total bundle weight";
            });
        }
    }

    public BundleContents(List<ItemStack> $$0) {
        this($$0, computeContentWeight($$0), -1);
    }

    private static Fraction computeContentWeight(List<ItemStack> $$0) {
        Fraction $$1 = Fraction.ZERO;
        for (ItemStack $$2 : $$0) {
            $$1 = $$1.add(getWeight($$2).multiplyBy(Fraction.getFraction($$2.getCount(), 1)));
        }
        return $$1;
    }

    static Fraction getWeight(ItemStack $$0) {
        BundleContents $$1 = (BundleContents) $$0.get(DataComponents.BUNDLE_CONTENTS);
        if ($$1 != null) {
            return BUNDLE_IN_BUNDLE_WEIGHT.add($$1.weight());
        }
        List<BeehiveBlockEntity.Occupant> $$2 = ((Bees) $$0.getOrDefault(DataComponents.BEES, Bees.EMPTY)).bees();
        if (!$$2.isEmpty()) {
            return Fraction.ONE;
        }
        return Fraction.getFraction(1, $$0.getMaxStackSize());
    }

    public static boolean canItemBeInBundle(ItemStack $$0) {
        return !$$0.isEmpty() && $$0.getItem().canFitInsideContainerItems();
    }

    public int getNumberOfItemsToShow() {
        int $$0 = size();
        int $$1 = $$0 > 12 ? 11 : 12;
        int $$2 = $$0 % 4;
        int $$3 = $$2 == 0 ? 0 : 4 - $$2;
        return Math.min($$0, $$1 - $$3);
    }

    public ItemStack getItemUnsafe(int $$0) {
        return this.items.get($$0);
    }

    public Stream<ItemStack> itemCopyStream() {
        return this.items.stream().map((v0) -> {
            return v0.copy();
        });
    }

    public Iterable<ItemStack> items() {
        return this.items;
    }

    public Iterable<ItemStack> itemsCopy() {
        return Lists.transform(this.items, (v0) -> {
            return v0.copy();
        });
    }

    public int size() {
        return this.items.size();
    }

    public Fraction weight() {
        return this.weight;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public int getSelectedItem() {
        return this.selectedItem;
    }

    public boolean hasSelectedItem() {
        return this.selectedItem != -1;
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof BundleContents)) {
            return false;
        }
        BundleContents $$1 = (BundleContents) $$0;
        return this.weight.equals($$1.weight) && ItemStack.listMatches(this.items, $$1.items);
    }

    public int hashCode() {
        return ItemStack.hashStackList(this.items);
    }

    public String toString() {
        return "BundleContents" + String.valueOf(this.items);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/BundleContents$Mutable.class */
    public static class Mutable {
        private final List<ItemStack> items;
        private Fraction weight;
        private int selectedItem;

        public Mutable(BundleContents $$0) {
            this.items = new ArrayList($$0.items);
            this.weight = $$0.weight;
            this.selectedItem = $$0.selectedItem;
        }

        public Mutable clearItems() {
            this.items.clear();
            this.weight = Fraction.ZERO;
            this.selectedItem = -1;
            return this;
        }

        private int findStackIndex(ItemStack $$0) {
            if (!$$0.isStackable()) {
                return -1;
            }
            for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
                if (ItemStack.isSameItemSameComponents(this.items.get($$1), $$0)) {
                    return $$1;
                }
            }
            return -1;
        }

        private int getMaxAmountToAdd(ItemStack $$0) {
            Fraction $$1 = Fraction.ONE.subtract(this.weight);
            return Math.max($$1.divideBy(BundleContents.getWeight($$0)).intValue(), 0);
        }

        public int tryInsert(ItemStack $$0) {
            int $$1;
            if (!BundleContents.canItemBeInBundle($$0) || ($$1 = Math.min($$0.getCount(), getMaxAmountToAdd($$0))) == 0) {
                return 0;
            }
            this.weight = this.weight.add(BundleContents.getWeight($$0).multiplyBy(Fraction.getFraction($$1, 1)));
            int $$2 = findStackIndex($$0);
            if ($$2 != -1) {
                ItemStack $$3 = this.items.remove($$2);
                ItemStack $$4 = $$3.copyWithCount($$3.getCount() + $$1);
                $$0.shrink($$1);
                this.items.add(0, $$4);
            } else {
                this.items.add(0, $$0.split($$1));
            }
            return $$1;
        }

        public int tryTransfer(Slot $$0, Player $$1) {
            ItemStack $$2 = $$0.getItem();
            int $$3 = getMaxAmountToAdd($$2);
            if (BundleContents.canItemBeInBundle($$2)) {
                return tryInsert($$0.safeTake($$2.getCount(), $$3, $$1));
            }
            return 0;
        }

        public void toggleSelectedItem(int $$0) {
            this.selectedItem = (this.selectedItem == $$0 || indexIsOutsideAllowedBounds($$0)) ? -1 : $$0;
        }

        private boolean indexIsOutsideAllowedBounds(int $$0) {
            return $$0 < 0 || $$0 >= this.items.size();
        }

        public ItemStack removeOne() {
            if (this.items.isEmpty()) {
                return null;
            }
            int $$0 = indexIsOutsideAllowedBounds(this.selectedItem) ? 0 : this.selectedItem;
            ItemStack $$1 = this.items.remove($$0).copy();
            this.weight = this.weight.subtract(BundleContents.getWeight($$1).multiplyBy(Fraction.getFraction($$1.getCount(), 1)));
            toggleSelectedItem(-1);
            return $$1;
        }

        public Fraction weight() {
            return this.weight;
        }

        public BundleContents toImmutable() {
            return new BundleContents(List.copyOf(this.items), this.weight, this.selectedItem);
        }
    }
}
