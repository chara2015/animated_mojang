package net.minecraft.world.level.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/WeatheringCopper.class */
public interface WeatheringCopper extends ChangeOverTimeBlock<WeatherState> {
    public static final Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
        return ImmutableBiMap.builder().put(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER).put(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER).put(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER).put(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER).put(Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER).put(Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER).put(Blocks.CHISELED_COPPER, Blocks.EXPOSED_CHISELED_COPPER).put(Blocks.EXPOSED_CHISELED_COPPER, Blocks.WEATHERED_CHISELED_COPPER).put(Blocks.WEATHERED_CHISELED_COPPER, Blocks.OXIDIZED_CHISELED_COPPER).put(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB).put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB).put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB).put(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS).put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS).put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS).put(Blocks.COPPER_DOOR, Blocks.EXPOSED_COPPER_DOOR).put(Blocks.EXPOSED_COPPER_DOOR, Blocks.WEATHERED_COPPER_DOOR).put(Blocks.WEATHERED_COPPER_DOOR, Blocks.OXIDIZED_COPPER_DOOR).put(Blocks.COPPER_TRAPDOOR, Blocks.EXPOSED_COPPER_TRAPDOOR).put(Blocks.EXPOSED_COPPER_TRAPDOOR, Blocks.WEATHERED_COPPER_TRAPDOOR).put(Blocks.WEATHERED_COPPER_TRAPDOOR, Blocks.OXIDIZED_COPPER_TRAPDOOR).putAll(Blocks.COPPER_BARS.weatheringMapping()).put(Blocks.COPPER_GRATE, Blocks.EXPOSED_COPPER_GRATE).put(Blocks.EXPOSED_COPPER_GRATE, Blocks.WEATHERED_COPPER_GRATE).put(Blocks.WEATHERED_COPPER_GRATE, Blocks.OXIDIZED_COPPER_GRATE).put(Blocks.COPPER_BULB, Blocks.EXPOSED_COPPER_BULB).put(Blocks.EXPOSED_COPPER_BULB, Blocks.WEATHERED_COPPER_BULB).put(Blocks.WEATHERED_COPPER_BULB, Blocks.OXIDIZED_COPPER_BULB).putAll(Blocks.COPPER_LANTERN.weatheringMapping()).put(Blocks.COPPER_CHEST, Blocks.EXPOSED_COPPER_CHEST).put(Blocks.EXPOSED_COPPER_CHEST, Blocks.WEATHERED_COPPER_CHEST).put(Blocks.WEATHERED_COPPER_CHEST, Blocks.OXIDIZED_COPPER_CHEST).put(Blocks.COPPER_GOLEM_STATUE, Blocks.EXPOSED_COPPER_GOLEM_STATUE).put(Blocks.EXPOSED_COPPER_GOLEM_STATUE, Blocks.WEATHERED_COPPER_GOLEM_STATUE).put(Blocks.WEATHERED_COPPER_GOLEM_STATUE, Blocks.OXIDIZED_COPPER_GOLEM_STATUE).put(Blocks.LIGHTNING_ROD, Blocks.EXPOSED_LIGHTNING_ROD).put(Blocks.EXPOSED_LIGHTNING_ROD, Blocks.WEATHERED_LIGHTNING_ROD).put(Blocks.WEATHERED_LIGHTNING_ROD, Blocks.OXIDIZED_LIGHTNING_ROD).putAll(Blocks.COPPER_CHAIN.weatheringMapping()).build();
    });
    public static final Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> {
        return NEXT_BY_BLOCK.get().inverse();
    });

    static Optional<Block> getPrevious(Block $$0) {
        return Optional.ofNullable((Block) PREVIOUS_BY_BLOCK.get().get($$0));
    }

    static Block getFirst(Block $$0) {
        Block $$1 = $$0;
        Object obj = PREVIOUS_BY_BLOCK.get().get($$1);
        while (true) {
            Block $$2 = (Block) obj;
            if ($$2 != null) {
                $$1 = $$2;
                obj = PREVIOUS_BY_BLOCK.get().get($$1);
            } else {
                return $$1;
            }
        }
    }

    static Optional<BlockState> getPrevious(BlockState $$0) {
        return getPrevious($$0.getBlock()).map($$1 -> {
            return $$1.withPropertiesOf($$0);
        });
    }

    static Optional<Block> getNext(Block $$0) {
        return Optional.ofNullable((Block) NEXT_BY_BLOCK.get().get($$0));
    }

    static BlockState getFirst(BlockState $$0) {
        return getFirst($$0.getBlock()).withPropertiesOf($$0);
    }

    @Override // net.minecraft.world.level.block.ChangeOverTimeBlock
    default Optional<BlockState> getNext(BlockState $$0) {
        return getNext($$0.getBlock()).map($$1 -> {
            return $$1.withPropertiesOf($$0);
        });
    }

    @Override // net.minecraft.world.level.block.ChangeOverTimeBlock
    default float getChanceModifier() {
        if (getAge() == WeatherState.UNAFFECTED) {
            return 0.75f;
        }
        return 1.0f;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/WeatheringCopper$WeatherState.class */
    public enum WeatherState implements StringRepresentable {
        UNAFFECTED("unaffected"),
        EXPOSED("exposed"),
        WEATHERED("weathered"),
        OXIDIZED("oxidized");

        public static final IntFunction<WeatherState> BY_ID = ByIdMap.continuous((v0) -> {
            return v0.ordinal();
        }, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        public static final Codec<WeatherState> CODEC = StringRepresentable.fromEnum(WeatherState::values);
        public static final StreamCodec<ByteBuf, WeatherState> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, (v0) -> {
            return v0.ordinal();
        });
        private final String name;

        WeatherState(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        public WeatherState next() {
            return BY_ID.apply(ordinal() + 1);
        }

        public WeatherState previous() {
            return BY_ID.apply(ordinal() - 1);
        }
    }
}
