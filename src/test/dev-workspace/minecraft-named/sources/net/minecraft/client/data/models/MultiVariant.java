package net.minecraft.client.data.models;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.block.model.SingleVariant;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.resources.model.WeightedVariants;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/MultiVariant.class */
public final class MultiVariant extends Record {
    private final WeightedList<Variant> variants;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MultiVariant.class), MultiVariant.class, "variants", "FIELD:Lnet/minecraft/client/data/models/MultiVariant;->variants:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MultiVariant.class), MultiVariant.class, "variants", "FIELD:Lnet/minecraft/client/data/models/MultiVariant;->variants:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MultiVariant.class, Object.class), MultiVariant.class, "variants", "FIELD:Lnet/minecraft/client/data/models/MultiVariant;->variants:Lnet/minecraft/util/random/WeightedList;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public WeightedList<Variant> variants() {
        return this.variants;
    }

    public MultiVariant(WeightedList<Variant> $$0) {
        if (!$$0.isEmpty()) {
            this.variants = $$0;
            return;
        }
        throw new IllegalArgumentException("Variant list must contain at least one element");
    }

    public MultiVariant with(VariantMutator $$0) {
        return new MultiVariant(this.variants.map($$0));
    }

    public BlockStateModel.Unbaked toUnbaked() {
        List<Weighted<Variant>> $$0 = this.variants.unwrap();
        if ($$0.size() == 1) {
            return new SingleVariant.Unbaked((Variant) ((Weighted) $$0.getFirst()).value());
        }
        return new WeightedVariants.Unbaked(this.variants.map(SingleVariant.Unbaked::new));
    }
}
