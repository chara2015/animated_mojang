package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/Cooldown.class */
public final class Cooldown extends Record implements RangeSelectItemModelProperty {
    public static final MapCodec<Cooldown> MAP_CODEC = MapCodec.unit(new Cooldown());

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Cooldown.class), Cooldown.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Cooldown.class), Cooldown.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Cooldown.class, Object.class), Cooldown.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        if ($$2 != null) {
            LivingEntity livingEntityAsLivingEntity = $$2.asLivingEntity();
            if (livingEntityAsLivingEntity instanceof Player) {
                Player $$4 = (Player) livingEntityAsLivingEntity;
                return $$4.getCooldowns().getCooldownPercent($$0, 0.0f);
            }
        }
        return 0.0f;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<Cooldown> type() {
        return MAP_CODEC;
    }
}
