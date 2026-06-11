package net.labymod.v26_1_2.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v26_1_2.client.player.inventory.InventoryTracker;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/player/MixinInventory.class */
@Mixin({Inventory.class})
public class MixinInventory implements net.labymod.api.client.entity.player.Inventory {

    @Shadow
    public int selected;

    @Shadow
    @Final
    public NonNullList<ItemStack> items;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.selected;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.selected = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public net.labymod.api.client.world.item.ItemStack itemStackAt(int index) {
        try {
            ItemStack itemStack = (ItemStack) this.items.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(Items.AIR.getDefaultInstance());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, net.labymod.api.client.world.item.ItemStack itemStack) {
        ItemStack stack = MinecraftUtil.toMinecraft(itemStack);
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        LocalPlayer player = Minecraft.getInstance().player;
        if (gameMode == null || player == null || !player.getAbilities().instabuild || stack.isEmpty()) {
            return;
        }
        gameMode.handleCreativeModeItemAdd(stack, 36 + index);
        this.items.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (ItemStack stack : this.items) {
            if (stack.is(ItemTags.ARROWS)) {
                found += stack.getCount();
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public net.labymod.api.client.world.item.ItemStack getNextArrows() {
        for (ItemStack stack : this.items) {
            if (stack.is(ItemTags.ARROWS)) {
                return MinecraftUtil.fromMinecraft(stack);
            }
        }
        return null;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> NonNullList<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack removeItem(List<ItemStack> list, int index, int amount) {
        net.labymod.api.client.world.item.ItemStack itemStackRemoveItem = ContainerHelper.removeItem(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackRemoveItem));
        return itemStackRemoveItem;
    }
}
