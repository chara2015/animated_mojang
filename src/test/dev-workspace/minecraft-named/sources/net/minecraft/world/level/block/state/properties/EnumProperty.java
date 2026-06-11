package net.minecraft.world.level.block.state.properties;

import com.google.common.collect.ImmutableMap;
import java.lang.Enum;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/EnumProperty.class */
public final class EnumProperty<T extends Enum<T> & StringRepresentable> extends Property<T> {
    private final List<T> values;
    private final Map<String, T> names;
    private final int[] ordinalToIndex;

    private EnumProperty(String str, Class<T> cls, List<T> list) {
        super(str, cls);
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Trying to make empty EnumProperty '" + str + "'");
        }
        this.values = List.copyOf(list);
        Enum[] enumArr = (Enum[]) cls.getEnumConstants();
        this.ordinalToIndex = new int[enumArr.length];
        for (Enum r0 : enumArr) {
            this.ordinalToIndex[r0.ordinal()] = list.indexOf(r0);
        }
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (T t : list) {
            builder.put(((StringRepresentable) t).getSerializedName(), t);
        }
        this.names = builder.buildOrThrow();
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public List<T> getPossibleValues() {
        return this.values;
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public Optional<T> getValue(String str) {
        return Optional.ofNullable((Enum) this.names.get(str));
    }

    /* JADX WARN: Incorrect types in method signature: (TT;)Ljava/lang/String; */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.minecraft.world.level.block.state.properties.Property
    public String getName(Enum r3) {
        return ((StringRepresentable) r3).getSerializedName();
    }

    /* JADX WARN: Incorrect types in method signature: (TT;)I */
    @Override // net.minecraft.world.level.block.state.properties.Property
    public int getInternalIndex(Enum r4) {
        return this.ordinalToIndex[r4.ordinal()];
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof EnumProperty)) {
            return false;
        }
        EnumProperty<?> $$1 = (EnumProperty) $$0;
        if (super.equals($$0)) {
            return this.values.equals($$1.values);
        }
        return false;
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public int generateHashCode() {
        int $$0 = super.generateHashCode();
        return (31 * $$0) + this.values.hashCode();
    }

    public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1) {
        return create($$0, $$1, $$02 -> {
            return true;
        });
    }

    public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String str, Class<T> cls, Predicate<T> predicate) {
        return create(str, cls, (List) Arrays.stream((Enum[]) cls.getEnumConstants()).filter(predicate).collect(Collectors.toList()));
    }

    /* JADX WARN: Incorrect types in method signature: <T:Ljava/lang/Enum<TT;>;:Lnet/minecraft/util/StringRepresentable;>(Ljava/lang/String;Ljava/lang/Class<TT;>;[TT;)Lnet/minecraft/world/level/block/state/properties/EnumProperty<TT;>; */
    @SafeVarargs
    public static EnumProperty create(String $$0, Class cls, Enum... enumArr) {
        return create($$0, cls, List.of((Object[]) enumArr));
    }

    public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1, List<T> $$2) {
        return new EnumProperty<>($$0, $$1, $$2);
    }
}
