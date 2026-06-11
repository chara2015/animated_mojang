package net.minecraft.world.level.chunk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.VarInt;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/chunk/HashMapPalette.class */
public class HashMapPalette<T> implements Palette<T> {
    private final CrudeIncrementalIntIdentityHashBiMap<T> values;
    private final int bits;

    public HashMapPalette(int $$0, List<T> $$1) {
        this($$0);
        CrudeIncrementalIntIdentityHashBiMap<T> crudeIncrementalIntIdentityHashBiMap = this.values;
        Objects.requireNonNull(crudeIncrementalIntIdentityHashBiMap);
        $$1.forEach(crudeIncrementalIntIdentityHashBiMap::add);
    }

    public HashMapPalette(int $$0) {
        this($$0, CrudeIncrementalIntIdentityHashBiMap.create(1 << $$0));
    }

    private HashMapPalette(int $$0, CrudeIncrementalIntIdentityHashBiMap<T> $$1) {
        this.bits = $$0;
        this.values = $$1;
    }

    public static <A> Palette<A> create(int $$0, List<A> $$1) {
        return new HashMapPalette($$0, $$1);
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int idFor(T $$0, PaletteResize<T> $$1) {
        int $$2 = this.values.getId($$0);
        if ($$2 == -1) {
            $$2 = this.values.add($$0);
            if ($$2 >= (1 << this.bits)) {
                $$2 = $$1.onResize(this.bits + 1, $$0);
            }
        }
        return $$2;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public boolean maybeHas(Predicate<T> $$0) {
        for (int $$1 = 0; $$1 < getSize(); $$1++) {
            if ($$0.test(this.values.byId($$1))) {
                return true;
            }
        }
        return false;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public T valueFor(int $$0) {
        T $$1 = this.values.byId($$0);
        if ($$1 == null) {
            throw new MissingPaletteEntryException($$0);
        }
        return $$1;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public void read(FriendlyByteBuf $$0, IdMap<T> $$1) {
        this.values.clear();
        int $$2 = $$0.readVarInt();
        for (int $$3 = 0; $$3 < $$2; $$3++) {
            this.values.add($$1.byIdOrThrow($$0.readVarInt()));
        }
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public void write(FriendlyByteBuf $$0, IdMap<T> $$1) {
        int $$2 = getSize();
        $$0.writeVarInt($$2);
        for (int $$3 = 0; $$3 < $$2; $$3++) {
            $$0.writeVarInt($$1.getId(this.values.byId($$3)));
        }
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int getSerializedSize(IdMap<T> $$0) {
        int $$1 = VarInt.getByteSize(getSize());
        for (int $$2 = 0; $$2 < getSize(); $$2++) {
            $$1 += VarInt.getByteSize($$0.getId(this.values.byId($$2)));
        }
        return $$1;
    }

    public List<T> getEntries() {
        ArrayList<T> $$0 = new ArrayList<>();
        Iterator<T> it = this.values.iterator();
        Objects.requireNonNull($$0);
        it.forEachRemaining($$0::add);
        return $$0;
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public int getSize() {
        return this.values.size();
    }

    @Override // net.minecraft.world.level.chunk.Palette
    public Palette<T> copy() {
        return new HashMapPalette(this.bits, this.values.copy());
    }
}
