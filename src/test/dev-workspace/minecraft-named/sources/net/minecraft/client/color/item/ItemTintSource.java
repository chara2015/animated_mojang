package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/ItemTintSource.class */
public interface ItemTintSource {
    int calculate(ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity);

    MapCodec<? extends ItemTintSource> type();
}
