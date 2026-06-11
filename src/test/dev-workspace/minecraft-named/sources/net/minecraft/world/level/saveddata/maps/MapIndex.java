package net.minecraft.world.level.saveddata.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/saveddata/maps/MapIndex.class */
public class MapIndex extends SavedData {
    private static final int NO_MAP_ID = -1;
    public static final Codec<MapIndex> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Codec.INT.optionalFieldOf("map", -1).forGetter($$0 -> {
            return Integer.valueOf($$0.lastMapId);
        })).apply($$0, (v1) -> {
            return new MapIndex(v1);
        });
    });
    public static final SavedDataType<MapIndex> TYPE = new SavedDataType<>("idcounts", MapIndex::new, CODEC, DataFixTypes.SAVED_DATA_MAP_INDEX);
    private int lastMapId;

    public MapIndex() {
        this(-1);
    }

    public MapIndex(int $$0) {
        this.lastMapId = $$0;
    }

    public MapId getNextMapId() {
        int i = this.lastMapId + 1;
        this.lastMapId = i;
        MapId $$0 = new MapId(i);
        setDirty();
        return $$0;
    }
}
