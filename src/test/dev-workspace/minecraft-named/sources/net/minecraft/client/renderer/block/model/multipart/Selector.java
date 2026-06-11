package net.minecraft.client.renderer.block.model.multipart;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/Selector.class */
public final class Selector extends Record {
    private final Optional<Condition> condition;
    private final BlockStateModel.Unbaked variant;
    public static final Codec<Selector> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Condition.CODEC.optionalFieldOf("when").forGetter((v0) -> {
            return v0.condition();
        }), BlockStateModel.Unbaked.CODEC.fieldOf("apply").forGetter((v0) -> {
            return v0.variant();
        })).apply($$0, Selector::new);
    });

    public Selector(Optional<Condition> $$0, BlockStateModel.Unbaked $$1) {
        this.condition = $$0;
        this.variant = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Selector.class), Selector.class, "condition;variant", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->variant:Lnet/minecraft/client/renderer/block/model/BlockStateModel$Unbaked;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Selector.class), Selector.class, "condition;variant", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->variant:Lnet/minecraft/client/renderer/block/model/BlockStateModel$Unbaked;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Selector.class, Object.class), Selector.class, "condition;variant", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->condition:Ljava/util/Optional;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/Selector;->variant:Lnet/minecraft/client/renderer/block/model/BlockStateModel$Unbaked;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Optional<Condition> condition() {
        return this.condition;
    }

    public BlockStateModel.Unbaked variant() {
        return this.variant;
    }

    public <O, S extends StateHolder<O, S>> Predicate<S> instantiate(StateDefinition<O, S> $$0) {
        return (Predicate) this.condition.map($$1 -> {
            return $$1.instantiate($$0);
        }).orElse($$02 -> {
            return true;
        });
    }
}
