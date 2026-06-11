package net.labymod.v26_1.client.player.inventory;

import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.minecraft.core.NonNullList;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/player/inventory/InventoryTracker.class */
public class InventoryTracker<E> extends NonNullList<E> {
    private final Inventory inventory;

    protected InventoryTracker(List<E> list, @Nullable E object, Inventory inventory) {
        super(list, object);
        this.inventory = inventory;
    }

    @NotNull
    public E set(int i, @NotNull E e) {
        E e2 = (E) super.set(i, e);
        Laby.fireEvent(new InventorySetSlotEvent(this.inventory, i, (ItemStack) e));
        return e2;
    }

    public static <E> InventoryTracker<E> withSize(int size, E object, Inventory inventory) {
        Validate.notNull(object);
        Object[] array = new Object[size];
        Arrays.fill(array, object);
        return new InventoryTracker<>(Arrays.asList(array), object, inventory);
    }
}
