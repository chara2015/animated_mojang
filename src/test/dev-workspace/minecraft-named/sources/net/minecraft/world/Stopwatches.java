package net.minecraft.world;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.UnaryOperator;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/Stopwatches.class */
public class Stopwatches extends SavedData {
    private static final Codec<Stopwatches> CODEC = Codec.unboundedMap(Identifier.CODEC, Codec.LONG).fieldOf("stopwatches").codec().xmap(Stopwatches::unpack, (v0) -> {
        return v0.pack();
    });
    public static final SavedDataType<Stopwatches> TYPE = new SavedDataType<>("stopwatches", Stopwatches::new, CODEC, DataFixTypes.SAVED_DATA_STOPWATCHES);
    private final Map<Identifier, Stopwatch> stopwatches = new Object2ObjectOpenHashMap();

    private Stopwatches() {
    }

    private static Stopwatches unpack(Map<Identifier, Long> $$0) {
        Stopwatches $$1 = new Stopwatches();
        long $$2 = currentTime();
        $$0.forEach(($$22, $$3) -> {
            $$1.stopwatches.put($$22, new Stopwatch($$2, $$3.longValue()));
        });
        return $$1;
    }

    private Map<Identifier, Long> pack() {
        long $$0 = currentTime();
        Map<Identifier, Long> $$1 = new TreeMap<>();
        this.stopwatches.forEach(($$2, $$3) -> {
            $$1.put($$2, Long.valueOf($$3.elapsedMilliseconds($$0)));
        });
        return $$1;
    }

    public Stopwatch get(Identifier $$0) {
        return this.stopwatches.get($$0);
    }

    public boolean add(Identifier $$0, Stopwatch $$1) {
        if (this.stopwatches.putIfAbsent($$0, $$1) == null) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean update(Identifier $$0, UnaryOperator<Stopwatch> $$1) {
        if (this.stopwatches.computeIfPresent($$0, ($$12, $$2) -> {
            return (Stopwatch) $$1.apply($$2);
        }) != null) {
            setDirty();
            return true;
        }
        return false;
    }

    public boolean remove(Identifier $$0) {
        boolean $$1 = this.stopwatches.remove($$0) != null;
        if ($$1) {
            setDirty();
        }
        return $$1;
    }

    @Override // net.minecraft.world.level.saveddata.SavedData
    public boolean isDirty() {
        return super.isDirty() || !this.stopwatches.isEmpty();
    }

    public List<Identifier> ids() {
        return List.copyOf(this.stopwatches.keySet());
    }

    public static long currentTime() {
        return Util.getMillis();
    }
}
