package net.labymod.v1_16_5.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_16_5.client.player.inventory.InventoryTracker;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/player/MixinInventory.class */
@Mixin({bfv.class})
public class MixinInventory implements Inventory {

    @Shadow
    @Final
    private List<gj<bmb>> f;

    @Shadow
    public int d;

    @Shadow
    @Final
    public gj<bmb> a;

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
            bmb itemStack = (bmb) this.a.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(bmd.a.r());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        bmb stack = MinecraftUtil.toMinecraft(itemStack);
        dww gameMode = djz.C().q;
        dzm player = djz.C().s;
        if (gameMode == null || player == null || !player.bC.d || stack.a()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.a.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (gj<bmb> list : this.f) {
            for (bmb stack : list) {
                if (stack.b().a(aeg.Y)) {
                    found += stack.E();
                }
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (gj<bmb> list : this.f) {
            for (bmb stack : list) {
                if (stack.b().a(aeg.Y)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }

    @Redirect(method = {"<init>(Lnet/minecraft/world/entity/player/Player;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> gj<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public bmb removeItem(List<bmb> list, int index, int amount) {
        ItemStack itemStackA = aoo.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
