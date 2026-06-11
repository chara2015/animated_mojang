package net.labymod.v1_12_2.mixins.client.world.item;

import net.labymod.v1_12_2.client.util.ItemToolMaterialAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/world/item/MixinItemTool.class */
@Mixin({ahq.class})
public class MixinItemTool implements ItemToolMaterialAccessor {

    @Shadow
    protected a d;

    @Override // net.labymod.v1_12_2.client.util.ItemToolMaterialAccessor
    public a labyMod$getToolMaterial() {
        return this.d;
    }
}
