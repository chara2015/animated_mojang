package net.labymod.v1_19_4.mixins.client.player;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.v1_19_4.client.player.inventory.InventoryTracker;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/player/MixinInventory.class */
@Mixin({byl.class})
public class MixinInventory implements Inventory {

    @Shadow
    @Final
    private List<hm<cfv>> o;

    @Shadow
    public int l;

    @Shadow
    @Final
    public hm<cfv> i;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.l;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.l = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            cfv itemStack = (cfv) this.i.get(index);
            return MinecraftUtil.fromMinecraft(itemStack);
        } catch (IndexOutOfBoundsException e) {
            return MinecraftUtil.fromMinecraft(cfy.a.ad_());
        }
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setCreativeModeItemStack(int index, ItemStack itemStack) {
        cfv stack = MinecraftUtil.toMinecraft(itemStack);
        fdn gameMode = emh.N().r;
        fhk player = emh.N().t;
        if (gameMode == null || player == null || !player.fK().d || stack.b()) {
            return;
        }
        gameMode.a(stack, 36 + index);
        this.i.set(index, stack);
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public int countAllArrows() {
        int found = 0;
        for (hm<cfv> list : this.o) {
            for (cfv stack : list) {
                if (stack.a(ano.as)) {
                    found += stack.K();
                }
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (hm<cfv> list : this.o) {
            for (cfv stack : list) {
                if (stack.a(ano.as)) {
                    return MinecraftUtil.fromMinecraft(stack);
                }
            }
        }
        return null;
    }

    @Redirect(method = {"<init>(Lnet/minecraft/world/entity/player/Player;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    public <E> hm<E> withSize(int size, E object) {
        return InventoryTracker.withSize(size, object, this);
    }

    @Redirect(method = {"removeItem(II)Lnet/minecraft/world/item/ItemStack;"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/ContainerHelper;removeItem(Ljava/util/List;II)Lnet/minecraft/world/item/ItemStack;"))
    public cfv removeItem(List<cfv> list, int index, int amount) {
        ItemStack itemStackA = bds.a(list, index, amount);
        Laby.fireEvent(new InventorySetSlotEvent(this, index, itemStackA));
        return itemStackA;
    }
}
