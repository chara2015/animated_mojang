package net.minecraft.util;

import java.util.Locale;
import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/StaticCache2D.class */
public class StaticCache2D<T> {
    private final int minX;
    private final int minZ;
    private final int sizeX;
    private final int sizeZ;
    private final Object[] cache;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/StaticCache2D$Initializer.class */
    @FunctionalInterface
    public interface Initializer<T> {
        T get(int i, int i2);
    }

    public static <T> StaticCache2D<T> create(int $$0, int $$1, int $$2, Initializer<T> $$3) {
        int $$4 = $$0 - $$2;
        int $$5 = $$1 - $$2;
        int $$6 = (2 * $$2) + 1;
        return new StaticCache2D<>($$4, $$5, $$6, $$6, $$3);
    }

    private StaticCache2D(int $$0, int $$1, int $$2, int $$3, Initializer<T> $$4) {
        this.minX = $$0;
        this.minZ = $$1;
        this.sizeX = $$2;
        this.sizeZ = $$3;
        this.cache = new Object[this.sizeX * this.sizeZ];
        for (int $$5 = $$0; $$5 < $$0 + $$2; $$5++) {
            for (int $$6 = $$1; $$6 < $$1 + $$3; $$6++) {
                this.cache[getIndex($$5, $$6)] = $$4.get($$5, $$6);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void forEach(Consumer<T> consumer) {
        for (Object $$1 : this.cache) {
            consumer.accept($$1);
        }
    }

    public T get(int i, int i2) {
        if (!contains(i, i2)) {
            throw new IllegalArgumentException("Requested out of range value (" + i + "," + i2 + ") from " + String.valueOf(this));
        }
        return (T) this.cache[getIndex(i, i2)];
    }

    public boolean contains(int $$0, int $$1) {
        int $$2 = $$0 - this.minX;
        int $$3 = $$1 - this.minZ;
        return $$2 >= 0 && $$2 < this.sizeX && $$3 >= 0 && $$3 < this.sizeZ;
    }

    public String toString() {
        return String.format(Locale.ROOT, "StaticCache2D[%d, %d, %d, %d]", Integer.valueOf(this.minX), Integer.valueOf(this.minZ), Integer.valueOf(this.minX + this.sizeX), Integer.valueOf(this.minZ + this.sizeZ));
    }

    private int getIndex(int $$0, int $$1) {
        int $$2 = $$0 - this.minX;
        int $$3 = $$1 - this.minZ;
        return ($$2 * this.sizeZ) + $$3;
    }
}
