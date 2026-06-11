package net.minecraft.world.level.block.state;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.Property;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/block/state/BlockState.class */
public class BlockState extends BlockBehaviour.BlockStateBase {
    public static final Codec<BlockState> CODEC = codec(BuiltInRegistries.BLOCK.byNameCodec(), (v0) -> {
        return v0.defaultBlockState();
    }).stable();

    public BlockState(Block $$0, Reference2ObjectArrayMap<Property<?>, Comparable<?>> $$1, MapCodec<BlockState> $$2) {
        super($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase
    protected BlockState asState() {
        return this;
    }
}
