package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/CrossbowPull.class */
public class CrossbowPull implements RangeSelectItemModelProperty {
    public static final MapCodec<CrossbowPull> MAP_CODEC = MapCodec.unit(new CrossbowPull());

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        LivingEntity $$4 = $$2 == null ? null : $$2.asLivingEntity();
        if ($$4 == null || CrossbowItem.isCharged($$0)) {
            return 0.0f;
        }
        int $$5 = CrossbowItem.getChargeDuration($$0, $$4);
        return UseDuration.useDuration($$0, $$4) / $$5;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<CrossbowPull> type() {
        return MAP_CODEC;
    }
}
