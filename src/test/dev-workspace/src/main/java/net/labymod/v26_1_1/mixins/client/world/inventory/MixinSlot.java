package net.labymod.v26_1_1.mixins.client.world.inventory;

import net.labymod.v26_1_1.client.world.inventory.SlotAccessor;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/world/inventory/MixinSlot.class */
@Mixin({Slot.class})
public class MixinSlot implements SlotAccessor {

    @Mutable
    @Shadow
    @Final
    public int x;

    @Mutable
    @Shadow
    @Final
    public int y;

    @Override // net.labymod.v26_1_1.client.world.inventory.SlotAccessor
    public Slot setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return (Slot) this;
    }
}
