package net.labymod.v1_20_4.mixins.client.world.inventory;

import net.labymod.v1_20_4.client.world.inventory.SlotAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/world/inventory/MixinSlot.class */
@Mixin({cjw.class})
public class MixinSlot implements SlotAccessor {

    @Mutable
    @Shadow
    @Final
    public int f;

    @Mutable
    @Shadow
    @Final
    public int g;

    @Override // net.labymod.v1_20_4.client.world.inventory.SlotAccessor
    public cjw setPosition(int x, int y) {
        this.f = x;
        this.g = y;
        return (cjw) this;
    }
}
