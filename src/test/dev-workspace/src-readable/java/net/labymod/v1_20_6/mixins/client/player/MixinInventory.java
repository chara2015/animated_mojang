package net.labymod.v1_20_6.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_20_6.client.player.inventory.InventoryTracker;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/player/MixinInventory.class */
@Mixin({cmy.class})
public class MixinInventory implements Inventory {

    @Shadow
    @Final
    private List<jr<cur>> n;

    @Shadow
    public int k;

    @Shadow
    @Final
    public jr<cur> h;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.k;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.k = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            cur itemStack = (cur) this.h.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(cuu.a.w());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        cur stack = MinecraftUtil.toMinecraft(itemStack);
        fyg gameMode = ffh.Q().q;
        gcs player = ffh.Q().s;
        if (gameMode == null || player == null || !player.gd().d || stack.e()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.h.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (jr<cur> list : this.n) {
            for (cur stack : list) {
                if (stack.a(awy.aU)) {
                    found += stack.I();
                }
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (jr<cur> list : this.n) {
            for (cur stack : list) {
                if (stack.a(awy.aU)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }

    @Redirect(method = {"<init>(Lnet/minecraft/world/entity/player/Player;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> jr<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cur removeItem(List<cur> list, int index, int amount) {
        ItemStack itemStackA = bqq.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
