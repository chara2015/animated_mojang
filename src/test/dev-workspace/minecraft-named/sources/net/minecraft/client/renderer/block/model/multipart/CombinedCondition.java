package net.minecraft.client.renderer.block.model.multipart;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/CombinedCondition.class */
public final class CombinedCondition extends Record implements Condition {
    private final Operation operation;
    private final List<Condition> terms;

    public CombinedCondition(Operation $$0, List<Condition> $$1) {
        this.operation = $$0;
        this.terms = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CombinedCondition.class), CombinedCondition.class, "operation;terms", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->operation:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition$Operation;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->terms:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CombinedCondition.class), CombinedCondition.class, "operation;terms", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->operation:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition$Operation;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->terms:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CombinedCondition.class, Object.class), CombinedCondition.class, "operation;terms", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->operation:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition$Operation;", "FIELD:Lnet/minecraft/client/renderer/block/model/multipart/CombinedCondition;->terms:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Operation operation() {
        return this.operation;
    }

    public List<Condition> terms() {
        return this.terms;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/CombinedCondition$Operation.class */
    public enum Operation implements StringRepresentable {
        AND("AND") { // from class: net.minecraft.client.renderer.block.model.multipart.CombinedCondition.Operation.1
            @Override // net.minecraft.client.renderer.block.model.multipart.CombinedCondition.Operation
            public <V> Predicate<V> apply(List<Predicate<V>> $$0) {
                return Util.allOf($$0);
            }
        },
        OR("OR") { // from class: net.minecraft.client.renderer.block.model.multipart.CombinedCondition.Operation.2
            @Override // net.minecraft.client.renderer.block.model.multipart.CombinedCondition.Operation
            public <V> Predicate<V> apply(List<Predicate<V>> $$0) {
                return Util.anyOf($$0);
            }
        };

        public static final Codec<Operation> CODEC = StringRepresentable.fromEnum(Operation::values);
        private final String name;

        public abstract <V> Predicate<V> apply(List<Predicate<V>> list);

        Operation(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    @Override // net.minecraft.client.renderer.block.model.multipart.Condition
    public <O, S extends StateHolder<O, S>> Predicate<S> instantiate(StateDefinition<O, S> $$0) {
        return this.operation.apply(Lists.transform(this.terms, $$1 -> {
            return $$1.instantiate($$0);
        }));
    }
}
