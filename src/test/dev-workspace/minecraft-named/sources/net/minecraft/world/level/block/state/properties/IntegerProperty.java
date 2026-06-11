package net.minecraft.world.level.block.state.properties;

import it.unimi.dsi.fastutil.ints.IntImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/IntegerProperty.class */
public final class IntegerProperty extends Property<Integer> {
    private final IntImmutableList values;
    private final int min;
    private final int max;

    private IntegerProperty(String $$0, int $$1, int $$2) {
        super($$0, Integer.class);
        if ($$1 < 0) {
            throw new IllegalArgumentException("Min value of " + $$0 + " must be 0 or greater");
        }
        if ($$2 <= $$1) {
            throw new IllegalArgumentException("Max value of " + $$0 + " must be greater than min (" + $$1 + ")");
        }
        this.min = $$1;
        this.max = $$2;
        this.values = IntImmutableList.toList(IntStream.range($$1, $$2 + 1));
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public List<Integer> getPossibleValues() {
        return this.values;
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if (!($$0 instanceof IntegerProperty)) {
            return false;
        }
        IntegerProperty $$1 = (IntegerProperty) $$0;
        if (super.equals($$0)) {
            return this.values.equals($$1.values);
        }
        return false;
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public int generateHashCode() {
        return (31 * super.generateHashCode()) + this.values.hashCode();
    }

    public static IntegerProperty create(String $$0, int $$1, int $$2) {
        return new IntegerProperty($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public Optional<Integer> getValue(String $$0) {
        try {
            int $$1 = Integer.parseInt($$0);
            return ($$1 < this.min || $$1 > this.max) ? Optional.empty() : Optional.of(Integer.valueOf($$1));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public String getName(Integer $$0) {
        return $$0.toString();
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public int getInternalIndex(Integer $$0) {
        if ($$0.intValue() <= this.max) {
            return $$0.intValue() - this.min;
        }
        return -1;
    }
}
