package net.labymod.v1_21_4.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_21_4.client.player.inventory.InventoryTracker;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/player/MixinInventory.class */
@Mixin({cox.class})
public class MixinInventory implements Inventory {

    @Shadow
    @Final
    private List<ka<cwq>> l;

    @Shadow
    public int j;

    @Shadow
    @Final
    public ka<cwq> g;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.j;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.j = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            cwq itemStack = (cwq) this.g.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(cwu.a.n());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        cwq stack = MinecraftUtil.toMinecraft(itemStack);
        ggk gameMode = flk.Q().r;
        gkx player = flk.Q().t;
        if (gameMode == null || player == null || !player.gj().d || stack.f()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.g.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (ka<cwq> list : this.l) {
            for (cwq stack : list) {
                if (stack.a(awy.aX)) {
                    found += stack.M();
                }
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (ka<cwq> list : this.l) {
            for (cwq stack : list) {
                if (stack.a(awy.aX)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }

    @Redirect(method = {"<init>(Lnet/minecraft/world/entity/player/Player;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> ka<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cwq removeItem(List<cwq> list, int index, int amount) {
        ItemStack itemStackA = bsf.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
