package net.minecraft.client.data.models.blockstates;

import com.google.common.collect.ImmutableList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.properties.Property;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/blockstates/PropertyValueList.class */
public final class PropertyValueList extends Record {
    private final List<Property.Value<?>> values;
    public static final PropertyValueList EMPTY = new PropertyValueList(List.of());
    private static final Comparator<Property.Value<?>> COMPARE_BY_NAME = Comparator.comparing($$0 -> {
        return $$0.property().getName();
    });

    public PropertyValueList(List<Property.Value<?>> $$0) {
        this.values = $$0;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PropertyValueList.class), PropertyValueList.class, "values", "FIELD:Lnet/minecraft/client/data/models/blockstates/PropertyValueList;->values:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PropertyValueList.class, Object.class), PropertyValueList.class, "values", "FIELD:Lnet/minecraft/client/data/models/blockstates/PropertyValueList;->values:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<Property.Value<?>> values() {
        return this.values;
    }

    public PropertyValueList extend(Property.Value<?> $$0) {
        return new PropertyValueList(Util.copyAndAdd(this.values, $$0));
    }

    public PropertyValueList extend(PropertyValueList $$0) {
        return new PropertyValueList(ImmutableList.builder().addAll(this.values).addAll($$0.values).build());
    }

    public static PropertyValueList of(Property.Value<?>... $$0) {
        return new PropertyValueList(List.of((Object[]) $$0));
    }

    public String getKey() {
        return (String) this.values.stream().sorted(COMPARE_BY_NAME).map((v0) -> {
            return v0.toString();
        }).collect(Collectors.joining(","));
    }

    @Override // java.lang.Record
    public String toString() {
        return getKey();
    }
}
