package net.minecraft.world.level.block.state.properties;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.lang.Comparable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/Property.class */
public abstract class Property<T extends Comparable<T>> {
    private final Class<T> clazz;
    private final String name;
    private Integer hashCode;
    private final Codec<T> codec = Codec.STRING.comapFlatMap($$0 -> {
        return (DataResult) getValue($$0).map((v0) -> {
            return DataResult.success(v0);
        }).orElseGet(() -> {
            return DataResult.error(() -> {
                return "Unable to read property: " + String.valueOf(this) + " with value: " + $$0;
            });
        });
    }, this::getName);
    private final Codec<Value<T>> valueCodec = this.codec.xmap(this::value, (v0) -> {
        return v0.value();
    });

    public abstract List<T> getPossibleValues();

    public abstract String getName(T t);

    public abstract Optional<T> getValue(String str);

    public abstract int getInternalIndex(T t);

    protected Property(String $$0, Class<T> $$1) {
        this.clazz = $$1;
        this.name = $$0;
    }

    public Value<T> value(T $$0) {
        return new Value<>(this, $$0);
    }

    public Value<T> value(StateHolder<?, ?> $$0) {
        return new Value<>(this, $$0.getValue(this));
    }

    public Stream<Value<T>> getAllValues() {
        return (Stream<Value<T>>) getPossibleValues().stream().map(this::value);
    }

    public Codec<T> codec() {
        return this.codec;
    }

    public Codec<Value<T>> valueCodec() {
        return this.valueCodec;
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getValueClass() {
        return this.clazz;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add(JigsawBlockEntity.NAME, this.name).add("clazz", this.clazz).add("values", getPossibleValues()).toString();
    }

    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof Property)) {
            return false;
        }
        Property<?> $$1 = (Property) $$0;
        return this.clazz.equals($$1.clazz) && this.name.equals($$1.name);
    }

    public final int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = Integer.valueOf(generateHashCode());
        }
        return this.hashCode.intValue();
    }

    public int generateHashCode() {
        return (31 * this.clazz.hashCode()) + this.name.hashCode();
    }

    public <U, S extends StateHolder<?, S>> DataResult<S> parseValue(DynamicOps<U> $$0, S $$1, U $$2) {
        DataResult<T> $$3 = this.codec.parse($$0, $$2);
        return $$3.map($$12 -> {
            return (StateHolder) $$1.setValue(this, $$12);
        }).setPartial($$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/Property$Value.class */
    public static final class Value<T extends Comparable<T>> extends Record {
        private final Property<T> property;
        private final T value;

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Value.class), Value.class, "property;value", "FIELD:Lnet/minecraft/world/level/block/state/properties/Property$Value;->property:Lnet/minecraft/world/level/block/state/properties/Property;", "FIELD:Lnet/minecraft/world/level/block/state/properties/Property$Value;->value:Ljava/lang/Comparable;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Value.class, Object.class), Value.class, "property;value", "FIELD:Lnet/minecraft/world/level/block/state/properties/Property$Value;->property:Lnet/minecraft/world/level/block/state/properties/Property;", "FIELD:Lnet/minecraft/world/level/block/state/properties/Property$Value;->value:Ljava/lang/Comparable;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Property<T> property() {
            return this.property;
        }

        public T value() {
            return this.value;
        }

        public Value(Property<T> $$0, T $$1) {
            if ($$0.getPossibleValues().contains($$1)) {
                this.property = $$0;
                this.value = $$1;
                return;
            }
            throw new IllegalArgumentException("Value " + String.valueOf($$1) + " does not belong to property " + String.valueOf($$0));
        }

        @Override // java.lang.Record
        public String toString() {
            return this.property.getName() + "=" + this.property.getName(this.value);
        }
    }
}
