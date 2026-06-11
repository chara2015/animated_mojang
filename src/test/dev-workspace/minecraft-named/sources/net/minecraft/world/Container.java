package net.minecraft.world;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SlotProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/Container.class */
public interface Container extends Clearable, SlotProvider, Iterable<ItemStack> {
    public static final float DEFAULT_DISTANCE_BUFFER = 4.0f;

    int getContainerSize();

    boolean isEmpty();

    ItemStack getItem(int i);

    ItemStack removeItem(int i, int i2);

    ItemStack removeItemNoUpdate(int i);

    void setItem(int i, ItemStack itemStack);

    void setChanged();

    boolean stillValid(Player player);

    default int getMaxStackSize() {
        return 99;
    }

    default int getMaxStackSize(ItemStack $$0) {
        return Math.min(getMaxStackSize(), $$0.getMaxStackSize());
    }

    default void startOpen(ContainerUser $$0) {
    }

    default void stopOpen(ContainerUser $$0) {
    }

    default List<ContainerUser> getEntitiesWithContainerOpen() {
        return List.of();
    }

    default boolean canPlaceItem(int $$0, ItemStack $$1) {
        return true;
    }

    default boolean canTakeItem(Container $$0, int $$1, ItemStack $$2) {
        return true;
    }

    default int countItem(Item $$0) {
        int $$1 = 0;
        for (ItemStack $$2 : this) {
            if ($$2.getItem().equals($$0)) {
                $$1 += $$2.getCount();
            }
        }
        return $$1;
    }

    default boolean hasAnyOf(Set<Item> $$0) {
        return hasAnyMatching($$1 -> {
            return !$$1.isEmpty() && $$0.contains($$1.getItem());
        });
    }

    default boolean hasAnyMatching(Predicate<ItemStack> $$0) {
        for (ItemStack $$1 : this) {
            if ($$0.test($$1)) {
                return true;
            }
        }
        return false;
    }

    static boolean stillValidBlockEntity(BlockEntity $$0, Player $$1) {
        return stillValidBlockEntity($$0, $$1, 4.0f);
    }

    static boolean stillValidBlockEntity(BlockEntity $$0, Player $$1, float $$2) {
        Level $$3 = $$0.getLevel();
        BlockPos $$4 = $$0.getBlockPos();
        if ($$3 == null || $$3.getBlockEntity($$4) != $$0) {
            return false;
        }
        return $$1.isWithinBlockInteractionRange($$4, $$2);
    }

    @Override // net.minecraft.world.entity.SlotProvider
    default SlotAccess getSlot(final int $$0) {
        if ($$0 < 0 || $$0 >= getContainerSize()) {
            return null;
        }
        return new SlotAccess() { // from class: net.minecraft.world.Container.1
            @Override // net.minecraft.world.entity.SlotAccess
            public ItemStack get() {
                return Container.this.getItem($$0);
            }

            @Override // net.minecraft.world.entity.SlotAccess
            public boolean set(ItemStack $$02) {
                Container.this.setItem($$0, $$02);
                return true;
            }
        };
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/Container$ContainerIterator.class */
    public static class ContainerIterator implements Iterator<ItemStack> {
        private final Container container;
        private int index;
        private final int size;

        public ContainerIterator(Container $$0) {
            this.container = $$0;
            this.size = $$0.getContainerSize();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.index < this.size;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public ItemStack next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Container container = this.container;
            int i = this.index;
            this.index = i + 1;
            return container.getItem(i);
        }
    }

    @Override // java.lang.Iterable
    default Iterator<ItemStack> iterator() {
        return new ContainerIterator(this);
    }
}
