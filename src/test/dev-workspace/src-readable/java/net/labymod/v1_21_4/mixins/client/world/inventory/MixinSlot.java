package net.labymod.v1_21_4.mixins.client.world.inventory;

import net.labymod.v1_21_4.client.world.inventory.SlotAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/world/inventory/MixinSlot.class */
@Mixin({cua.class})
public class MixinSlot implements SlotAccessor {

    @Mutable
    @Shadow
    @Final
    public int e;

    @Mutable
    @Shadow
    @Final
    public int f;

    @Override // net.labymod.v1_21_4.client.world.inventory.SlotAccessor
    public cua setPosition(int x, int y) {
        this.e = x;
        this.f = y;
        return (cua) this;
    }
}
