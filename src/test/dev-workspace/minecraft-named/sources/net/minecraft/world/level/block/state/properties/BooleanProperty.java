package net.minecraft.world.level.block.state.properties;

import java.util.List;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/properties/BooleanProperty.class */
public final class BooleanProperty extends Property<Boolean> {
    private static final List<Boolean> VALUES = List.of(true, false);
    private static final int TRUE_INDEX = 0;
    private static final int FALSE_INDEX = 1;

    private BooleanProperty(String $$0) {
        super($$0, Boolean.class);
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public List<Boolean> getPossibleValues() {
        return VALUES;
    }

    public static BooleanProperty create(String $$0) {
        return new BooleanProperty($$0);
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public Optional<Boolean> getValue(String $$0) {
        switch ($$0) {
            case "true":
                return Optional.of(true);
            case "false":
                return Optional.of(false);
            default:
                return Optional.empty();
        }
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public String getName(Boolean $$0) {
        return $$0.toString();
    }

    @Override // net.minecraft.world.level.block.state.properties.Property
    public int getInternalIndex(Boolean $$0) {
        return $$0.booleanValue() ? 0 : 1;
    }
}
