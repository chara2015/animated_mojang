package net.labymod.api.util.collection.table;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.ObjIntConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/table/CodepointTable.class */
public class CodepointTable<T> {
    private static final int BLOCK_BITS = 8;
    private static final int BLOCK_MASK = 255;
    private static final int BLOCK_SIZE = 256;
    private static final int BLOCK_COUNT = 4352;
    private final T[] emptyBlock;
    private final T[][] blockMap;
    private final IntFunction<T[]> blockConstructor;

    public CodepointTable(IntFunction<T[]> blockConstructor, IntFunction<T[][]> blockMapConstructor) {
        this.blockConstructor = blockConstructor;
        this.emptyBlock = blockConstructor.apply(256);
        this.blockMap = blockMapConstructor.apply(4352);
        Arrays.fill(this.blockMap, this.emptyBlock);
    }

    public T get(int index) {
        int blockIndex = index >> 8;
        int blockPosition = index & 255;
        return this.blockMap[blockIndex][blockPosition];
    }

    public void set(int index, T value) {
        int blockIndex = index >> 8;
        int blockPosition = index & 255;
        T[] blockData = this.blockMap[blockIndex];
        if (blockData == this.emptyBlock) {
            blockData = this.blockConstructor.apply(256);
            this.blockMap[blockIndex] = blockData;
        }
        blockData[blockPosition] = value;
    }

    public void clear() {
        Arrays.fill(this.blockMap, this.emptyBlock);
    }

    public T computeIfAbsent(int index, IntFunction<T> function) {
        T value = get(index);
        if (value != null) {
            return value;
        }
        T value2 = function.apply(index);
        set(index, value2);
        return value2;
    }

    public void forEach(ObjIntConsumer<T> consumer) {
        Objects.requireNonNull(consumer, "consumer must not be null");
        for (int blockIndex = 0; blockIndex < this.blockMap.length; blockIndex++) {
            T[] blockData = this.blockMap[blockIndex];
            if (blockData != this.emptyBlock) {
                for (int index = 0; index < blockData.length; index++) {
                    T value = blockData[index];
                    if (value != null) {
                        consumer.accept(value, (blockIndex << 8) | index);
                    }
                }
            }
        }
    }
}
