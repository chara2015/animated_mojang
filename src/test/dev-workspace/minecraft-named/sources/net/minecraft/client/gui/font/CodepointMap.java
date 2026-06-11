package net.minecraft.client.gui.font;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.function.IntFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/CodepointMap.class */
public class CodepointMap<T> {
    private static final int BLOCK_BITS = 8;
    private static final int BLOCK_SIZE = 256;
    private static final int IN_BLOCK_MASK = 255;
    private static final int MAX_BLOCK = 4351;
    private static final int BLOCK_COUNT = 4352;
    private final T[] empty;
    private final T[][] blockMap;
    private final IntFunction<T[]> blockConstructor;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/CodepointMap$Output.class */
    @FunctionalInterface
    public interface Output<T> {
        void accept(int i, T t);
    }

    public CodepointMap(IntFunction<T[]> $$0, IntFunction<T[][]> $$1) {
        this.empty = $$0.apply(256);
        this.blockMap = $$1.apply(BLOCK_COUNT);
        Arrays.fill(this.blockMap, this.empty);
        this.blockConstructor = $$0;
    }

    public void clear() {
        Arrays.fill(this.blockMap, this.empty);
    }

    public T get(int $$0) {
        int $$1 = $$0 >> 8;
        int $$2 = $$0 & 255;
        return this.blockMap[$$1][$$2];
    }

    public T put(int $$0, T $$1) {
        int $$2 = $$0 >> 8;
        int $$3 = $$0 & 255;
        T[] $$4 = this.blockMap[$$2];
        if ($$4 == this.empty) {
            T[] $$42 = this.blockConstructor.apply(256);
            this.blockMap[$$2] = $$42;
            $$42[$$3] = $$1;
            return null;
        }
        T $$5 = $$4[$$3];
        $$4[$$3] = $$1;
        return $$5;
    }

    public T computeIfAbsent(int $$0, IntFunction<T> $$1) {
        int $$2 = $$0 >> 8;
        int $$3 = $$0 & 255;
        T[] $$4 = this.blockMap[$$2];
        T $$5 = $$4[$$3];
        if ($$5 != null) {
            return $$5;
        }
        if ($$4 == this.empty) {
            $$4 = this.blockConstructor.apply(256);
            this.blockMap[$$2] = $$4;
        }
        T $$6 = $$1.apply($$0);
        $$4[$$3] = $$6;
        return $$6;
    }

    public T remove(int $$0) {
        int $$1 = $$0 >> 8;
        int $$2 = $$0 & 255;
        T[] $$3 = this.blockMap[$$1];
        if ($$3 == this.empty) {
            return null;
        }
        T $$4 = $$3[$$2];
        $$3[$$2] = null;
        return $$4;
    }

    public void forEach(Output<T> $$0) {
        for (int $$1 = 0; $$1 < this.blockMap.length; $$1++) {
            T[] $$2 = this.blockMap[$$1];
            if ($$2 != this.empty) {
                for (int $$3 = 0; $$3 < $$2.length; $$3++) {
                    T $$4 = $$2[$$3];
                    if ($$4 != null) {
                        int $$5 = ($$1 << 8) | $$3;
                        $$0.accept($$5, $$4);
                    }
                }
            }
        }
    }

    public IntSet keySet() {
        IntOpenHashSet $$0 = new IntOpenHashSet();
        forEach(($$1, $$2) -> {
            $$0.add($$1);
        });
        return $$0;
    }
}
