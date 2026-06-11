package net.labymod.v1_12_2.client.player.inventory;

import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/player/inventory/InventoryTracker.class */
public class InventoryTracker<E> extends fi<E> {
    private final Inventory inventory;

    protected InventoryTracker(List<E> list, @Nullable E object, Inventory inventory) {
        super(list, object);
        this.inventory = inventory;
    }

    @NotNull
    public E set(int i, @NotNull E e) {
        Laby.fireEvent(new InventorySetSlotEvent(this.inventory, i, (ItemStack) e));
        return (E) super.set(i, e);
    }

    public static <E> InventoryTracker<E> withSize(int size, E object, Inventory inventory) {
        Validate.notNull(object);
        Object[] array = new Object[size];
        Arrays.fill(array, object);
        return new InventoryTracker<>(Arrays.asList(array), object, inventory);
    }
}
