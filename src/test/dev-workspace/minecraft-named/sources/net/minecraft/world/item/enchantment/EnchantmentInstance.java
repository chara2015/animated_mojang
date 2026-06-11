package net.minecraft.world.item.enchantment;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.Holder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/EnchantmentInstance.class */
public final class EnchantmentInstance extends Record {
    private final Holder<Enchantment> enchantment;
    private final int level;

    public EnchantmentInstance(Holder<Enchantment> $$0, int $$1) {
        this.enchantment = $$0;
        this.level = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EnchantmentInstance.class), EnchantmentInstance.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->level:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EnchantmentInstance.class), EnchantmentInstance.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->level:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EnchantmentInstance.class, Object.class), EnchantmentInstance.class, "enchantment;level", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->enchantment:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/world/item/enchantment/EnchantmentInstance;->level:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Holder<Enchantment> enchantment() {
        return this.enchantment;
    }

    public int level() {
        return this.level;
    }

    public int weight() {
        return enchantment().value().getWeight();
    }
}
