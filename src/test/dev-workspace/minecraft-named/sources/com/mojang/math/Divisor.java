package com.mojang.math;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/math/Divisor.class */
public class Divisor implements IntIterator {
    private final int denominator;
    private final int quotient;
    private final int mod;
    private int returnedParts;
    private int remainder;

    public Divisor(int $$0, int $$1) {
        this.denominator = $$1;
        if ($$1 > 0) {
            this.quotient = $$0 / $$1;
            this.mod = $$0 % $$1;
        } else {
            this.quotient = 0;
            this.mod = 0;
        }
    }

    public boolean hasNext() {
        return this.returnedParts < this.denominator;
    }

    public int nextInt() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int $$0 = this.quotient;
        this.remainder += this.mod;
        if (this.remainder >= this.denominator) {
            this.remainder -= this.denominator;
            $$0++;
        }
        this.returnedParts++;
        return $$0;
    }

    @VisibleForTesting
    public static Iterable<Integer> asIterable(int $$0, int $$1) {
        return () -> {
            return new Divisor($$0, $$1);
        };
    }
}
