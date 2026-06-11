package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import java.util.BitSet;
import java.util.List;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.LevelLightEngine;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/game/ClientboundLightUpdatePacketData.class */
public class ClientboundLightUpdatePacketData {
    private static final StreamCodec<ByteBuf, byte[]> DATA_LAYER_STREAM_CODEC = ByteBufCodecs.byteArray(2048);
    private final BitSet skyYMask;
    private final BitSet blockYMask;
    private final BitSet emptySkyYMask;
    private final BitSet emptyBlockYMask;
    private final List<byte[]> skyUpdates;
    private final List<byte[]> blockUpdates;

    public ClientboundLightUpdatePacketData(ChunkPos $$0, LevelLightEngine $$1, BitSet $$2, BitSet $$3) {
        this.skyYMask = new BitSet();
        this.blockYMask = new BitSet();
        this.emptySkyYMask = new BitSet();
        this.emptyBlockYMask = new BitSet();
        this.skyUpdates = Lists.newArrayList();
        this.blockUpdates = Lists.newArrayList();
        for (int $$4 = 0; $$4 < $$1.getLightSectionCount(); $$4++) {
            if ($$2 == null || $$2.get($$4)) {
                prepareSectionData($$0, $$1, LightLayer.SKY, $$4, this.skyYMask, this.emptySkyYMask, this.skyUpdates);
            }
            if ($$3 == null || $$3.get($$4)) {
                prepareSectionData($$0, $$1, LightLayer.BLOCK, $$4, this.blockYMask, this.emptyBlockYMask, this.blockUpdates);
            }
        }
    }

    public ClientboundLightUpdatePacketData(FriendlyByteBuf $$0, int $$1, int $$2) {
        this.skyYMask = $$0.readBitSet();
        this.blockYMask = $$0.readBitSet();
        this.emptySkyYMask = $$0.readBitSet();
        this.emptyBlockYMask = $$0.readBitSet();
        this.skyUpdates = $$0.readList(DATA_LAYER_STREAM_CODEC);
        this.blockUpdates = $$0.readList(DATA_LAYER_STREAM_CODEC);
    }

    public void write(FriendlyByteBuf $$0) {
        $$0.writeBitSet(this.skyYMask);
        $$0.writeBitSet(this.blockYMask);
        $$0.writeBitSet(this.emptySkyYMask);
        $$0.writeBitSet(this.emptyBlockYMask);
        $$0.writeCollection(this.skyUpdates, DATA_LAYER_STREAM_CODEC);
        $$0.writeCollection(this.blockUpdates, DATA_LAYER_STREAM_CODEC);
    }

    private void prepareSectionData(ChunkPos $$0, LevelLightEngine $$1, LightLayer $$2, int $$3, BitSet $$4, BitSet $$5, List<byte[]> $$6) {
        DataLayer $$7 = $$1.getLayerListener($$2).getDataLayerData(SectionPos.of($$0, $$1.getMinLightSection() + $$3));
        if ($$7 != null) {
            if ($$7.isEmpty()) {
                $$5.set($$3);
            } else {
                $$4.set($$3);
                $$6.add($$7.copy().getData());
            }
        }
    }

    public BitSet getSkyYMask() {
        return this.skyYMask;
    }

    public BitSet getEmptySkyYMask() {
        return this.emptySkyYMask;
    }

    public List<byte[]> getSkyUpdates() {
        return this.skyUpdates;
    }

    public BitSet getBlockYMask() {
        return this.blockYMask;
    }

    public BitSet getEmptyBlockYMask() {
        return this.emptyBlockYMask;
    }

    public List<byte[]> getBlockUpdates() {
        return this.blockUpdates;
    }
}
