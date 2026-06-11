package net.labymod.v1_21_11.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_21_11.client.player.inventory.InventoryTracker;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/player/MixinInventory.class */
@Mixin({ddl.class})
public class MixinInventory implements Inventory {

    @Shadow
    public int m;

    @Shadow
    @Final
    public jm<dlt> l;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.m;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.m = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            dlt itemStack = (dlt) this.l.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(dlx.a.m());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        dlt stack = MinecraftUtil.toMinecraft(itemStack);
        hio gameMode = gfj.V().q;
        hnh player = gfj.V().s;
        if (gameMode == null || player == null || !player.gL().d || stack.f()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.l.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (dlt stack : this.l) {
            if (stack.a(bdy.br)) {
                found += stack.N();
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (dlt stack : this.l) {
            if (stack.a(bdy.br)) {
                return MinecraftUtil.fromMinecraft(stack);
            }
        }
        return null;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> jm<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public dlt removeItem(List<dlt> list, int index, int amount) {
        ItemStack itemStackA = ccw.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
