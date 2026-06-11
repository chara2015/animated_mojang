package net.labymod.api.event.client.entity.player.inventory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent.class */
public final class InventorySetSlotEvent extends Record implements Event {
    private final Inventory inventory;
    private final int index;
    private final ItemStack itemStack;

    public InventorySetSlotEvent(Inventory inventory, int index, ItemStack itemStack) {
        this.inventory = inventory;
        this.index = index;
        this.itemStack = itemStack;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, InventorySetSlotEvent.class), InventorySetSlotEvent.class, "inventory;index;itemStack", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->inventory:Lnet/labymod/api/client/entity/player/Inventory;", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->index:I", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, InventorySetSlotEvent.class), InventorySetSlotEvent.class, "inventory;index;itemStack", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->inventory:Lnet/labymod/api/client/entity/player/Inventory;", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->index:I", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, InventorySetSlotEvent.class, Object.class), InventorySetSlotEvent.class, "inventory;index;itemStack", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->inventory:Lnet/labymod/api/client/entity/player/Inventory;", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->index:I", "FIELD:Lnet/labymod/api/event/client/entity/player/inventory/InventorySetSlotEvent;->itemStack:Lnet/labymod/api/client/world/item/ItemStack;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Inventory inventory() {
        return this.inventory;
    }

    public int index() {
        return this.index;
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }
}
