package net.labymod.v1_12_2.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_12_2.client.player.inventory.InventoryTracker;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/player/MixinInventoryPlayer.class */
@Mixin({aec.class})
public class MixinInventoryPlayer implements Inventory {

    @Shadow
    public int d;

    @Shadow
    @Final
    public fi<aip> a;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.d;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.d = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            aip itemStack = (aip) this.a.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(MinecraftUtil.AIR);
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        throw new UnsupportedOperationException("Not implemented in this version");
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (aip itemStack : this.a) {
            if (itemStack != null && itemStack.c() == air.h) {
                found += itemStack.E();
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (aip itemStack : this.a) {
            if (itemStack != null && itemStack.c() == air.h) {
                return MinecraftUtil.fromMinecraft(itemStack);
            }
        }
        return null;
    }

    @Redirect(method = {"<init>(Lnet/minecraft/entity/player/EntityPlayer;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/util/NonNullList;"))
    public <E> fi<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"decrStackSize(II)Lnet/minecraft/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/ItemStackHelper;getAndSplit(Ljava/util/List;II)Lnet/minecraft/item/ItemStack;"))
    public aip removeItem(List<aip> list, int index, int amount) {
        ItemStack itemStackA = tw.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
