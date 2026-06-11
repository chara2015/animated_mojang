package net.minecraft.network.chat.contents.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/data/BlockDataSource.class */
public final class BlockDataSource extends Record implements DataSource {
    private final String posPattern;
    private final Coordinates compiledPos;
    public static final MapCodec<BlockDataSource> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf("block").forGetter((v0) -> {
            return v0.posPattern();
        })).apply($$0, BlockDataSource::new);
    });

    public BlockDataSource(String $$0, Coordinates $$1) {
        this.posPattern = $$0;
        this.compiledPos = $$1;
    }

    public String posPattern() {
        return this.posPattern;
    }

    public Coordinates compiledPos() {
        return this.compiledPos;
    }

    public BlockDataSource(String $$0) {
        this($$0, compilePos($$0));
    }

    private static Coordinates compilePos(String $$0) {
        try {
            return BlockPosArgument.blockPos().m1294parse(new StringReader($$0));
        } catch (CommandSyntaxException e) {
            return null;
        }
    }

    @Override // net.minecraft.network.chat.contents.data.DataSource
    public Stream<CompoundTag> getData(CommandSourceStack $$0) {
        BlockEntity $$3;
        if (this.compiledPos != null) {
            ServerLevel $$1 = $$0.getLevel();
            BlockPos $$2 = this.compiledPos.getBlockPos($$0);
            if ($$1.isLoaded($$2) && ($$3 = $$1.getBlockEntity($$2)) != null) {
                return Stream.of($$3.saveWithFullMetadata($$0.registryAccess()));
            }
        }
        return Stream.empty();
    }

    @Override // net.minecraft.network.chat.contents.data.DataSource
    public MapCodec<BlockDataSource> codec() {
        return MAP_CODEC;
    }

    @Override // java.lang.Record
    public String toString() {
        return "block=" + this.posPattern;
    }

    @Override // java.lang.Record
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof BlockDataSource) {
            BlockDataSource $$1 = (BlockDataSource) $$0;
            if (this.posPattern.equals($$1.posPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Record
    public int hashCode() {
        return this.posPattern.hashCode();
    }
}
