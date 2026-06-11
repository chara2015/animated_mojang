package net.minecraft.world.entity.ai.attributes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/AttributeModifier.class */
public final class AttributeModifier extends Record {
    private final Identifier id;
    private final double amount;
    private final Operation operation;
    public static final MapCodec<AttributeModifier> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
            return v0.id();
        }), Codec.DOUBLE.fieldOf("amount").forGetter((v0) -> {
            return v0.amount();
        }), Operation.CODEC.fieldOf("operation").forGetter((v0) -> {
            return v0.operation();
        })).apply($$0, (v1, v2, v3) -> {
            return new AttributeModifier(v1, v2, v3);
        });
    });
    public static final Codec<AttributeModifier> CODEC = MAP_CODEC.codec();
    public static final StreamCodec<ByteBuf, AttributeModifier> STREAM_CODEC = StreamCodec.composite(Identifier.STREAM_CODEC, (v0) -> {
        return v0.id();
    }, ByteBufCodecs.DOUBLE, (v0) -> {
        return v0.amount();
    }, Operation.STREAM_CODEC, (v0) -> {
        return v0.operation();
    }, (v1, v2, v3) -> {
        return new AttributeModifier(v1, v2, v3);
    });

    public AttributeModifier(Identifier $$0, double $$1, Operation $$2) {
        this.id = $$0;
        this.amount = $$1;
        this.operation = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AttributeModifier.class), AttributeModifier.class, "id;amount;operation", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->amount:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->operation:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AttributeModifier.class), AttributeModifier.class, "id;amount;operation", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->amount:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->operation:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AttributeModifier.class, Object.class), AttributeModifier.class, "id;amount;operation", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->amount:D", "FIELD:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;->operation:Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier id() {
        return this.id;
    }

    public double amount() {
        return this.amount;
    }

    public Operation operation() {
        return this.operation;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/AttributeModifier$Operation.class */
    public enum Operation implements StringRepresentable {
        ADD_VALUE("add_value", 0),
        ADD_MULTIPLIED_BASE("add_multiplied_base", 1),
        ADD_MULTIPLIED_TOTAL("add_multiplied_total", 2);

        public static final IntFunction<Operation> BY_ID = ByIdMap.continuous((v0) -> {
            return v0.id();
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Operation> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
            return v0.id();
        });
        public static final Codec<Operation> CODEC = StringRepresentable.fromEnum(Operation::values);
        private final String name;
        private final int id;

        Operation(String $$0, int $$1) {
            this.name = $$0;
            this.id = $$1;
        }

        public int id() {
            return this.id;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    public boolean is(Identifier $$0) {
        return $$0.equals(this.id);
    }
}
