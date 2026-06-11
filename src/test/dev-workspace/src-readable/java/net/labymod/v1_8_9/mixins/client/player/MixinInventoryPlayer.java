package net.labymod.v1_8_9.mixins.client.player;

import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinInventoryPlayer.class */
@Mixin({wm.class})
public class MixinInventoryPlayer implements Inventory {

    @Shadow
    public zx[] a;

    @Shadow
    public int c;

    @Override // net.labymod.api.client.entity.player.Inventory
    public int getSelectedIndex() {
        return this.c;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public void setSelectedIndex(int index) {
        this.c = index;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack itemStackAt(int index) {
        try {
            zx itemStack = this.a[index];
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
        for (zx itemStack : this.a) {
            if (itemStack != null && itemStack.b() == zy.g) {
                found += itemStack.b;
            }
        }
        return found;
    }

    @Override // net.labymod.api.client.entity.player.Inventory
    public ItemStack getNextArrows() {
        for (zx itemStack : this.a) {
            if (itemStack != null && itemStack.b() == zy.g) {
                return MinecraftUtil.fromMinecraft(itemStack);
            }
        }
        return null;
    }
}
