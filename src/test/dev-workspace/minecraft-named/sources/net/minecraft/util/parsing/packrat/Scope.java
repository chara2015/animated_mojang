package net.minecraft.util.parsing.packrat;

import com.google.common.annotations.VisibleForTesting;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Scope.class */
public final class Scope {
    private static final int NOT_FOUND = -1;
    private static final Object FRAME_START_MARKER;
    private static final int ENTRY_STRIDE = 2;
    private Object[] stack = new Object[128];
    private int topEntryKeyIndex = 0;
    private int topMarkerKeyIndex = 0;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Scope.class.desiredAssertionStatus();
        FRAME_START_MARKER = new Object() { // from class: net.minecraft.util.parsing.packrat.Scope.1
            public String toString() {
                return "frame";
            }
        };
    }

    public Scope() {
        this.stack[0] = FRAME_START_MARKER;
        this.stack[1] = null;
    }

    private int valueIndex(Atom<?> $$0) {
        for (int $$1 = this.topEntryKeyIndex; $$1 > this.topMarkerKeyIndex; $$1 -= 2) {
            Object $$2 = this.stack[$$1];
            if (!$assertionsDisabled && !($$2 instanceof Atom)) {
                throw new AssertionError();
            }
            if ($$2 == $$0) {
                return $$1 + 1;
            }
        }
        return -1;
    }

    public int valueIndexForAny(Atom<?>... $$0) {
        for (int $$1 = this.topEntryKeyIndex; $$1 > this.topMarkerKeyIndex; $$1 -= 2) {
            Object $$2 = this.stack[$$1];
            if (!$assertionsDisabled && !($$2 instanceof Atom)) {
                throw new AssertionError();
            }
            for (Atom<?> $$3 : $$0) {
                if ($$3 == $$2) {
                    return $$1 + 1;
                }
            }
        }
        return -1;
    }

    private void ensureCapacity(int $$0) {
        int $$1 = this.stack.length;
        int $$2 = this.topEntryKeyIndex + 1;
        int $$3 = $$2 + ($$0 * 2);
        if ($$3 >= $$1) {
            int $$4 = Util.growByHalf($$1, $$3 + 1);
            Object[] $$5 = new Object[$$4];
            System.arraycopy(this.stack, 0, $$5, 0, $$1);
            this.stack = $$5;
        }
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    private void setupNewFrame() {
        this.topEntryKeyIndex += 2;
        this.stack[this.topEntryKeyIndex] = FRAME_START_MARKER;
        this.stack[this.topEntryKeyIndex + 1] = Integer.valueOf(this.topMarkerKeyIndex);
        this.topMarkerKeyIndex = this.topEntryKeyIndex;
    }

    public void pushFrame() {
        ensureCapacity(1);
        setupNewFrame();
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    private int getPreviousMarkerIndex(int $$0) {
        return ((Integer) this.stack[$$0 + 1]).intValue();
    }

    public void popFrame() {
        if (!$assertionsDisabled && this.topMarkerKeyIndex == 0) {
            throw new AssertionError();
        }
        this.topEntryKeyIndex = this.topMarkerKeyIndex - 2;
        this.topMarkerKeyIndex = getPreviousMarkerIndex(this.topMarkerKeyIndex);
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    public void splitFrame() {
        int $$0 = this.topMarkerKeyIndex;
        int $$1 = (this.topEntryKeyIndex - this.topMarkerKeyIndex) / 2;
        ensureCapacity($$1 + 1);
        setupNewFrame();
        int $$2 = $$0 + 2;
        int $$3 = this.topEntryKeyIndex;
        for (int $$4 = 0; $$4 < $$1; $$4++) {
            $$3 += 2;
            Object $$5 = this.stack[$$2];
            if (!$assertionsDisabled && $$5 == null) {
                throw new AssertionError();
            }
            this.stack[$$3] = $$5;
            this.stack[$$3 + 1] = null;
            $$2 += 2;
        }
        this.topEntryKeyIndex = $$3;
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    public void clearFrameValues() {
        for (int $$0 = this.topEntryKeyIndex; $$0 > this.topMarkerKeyIndex; $$0 -= 2) {
            if (!$assertionsDisabled && !(this.stack[$$0] instanceof Atom)) {
                throw new AssertionError();
            }
            this.stack[$$0 + 1] = null;
        }
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    public void mergeFrame() {
        int $$0 = getPreviousMarkerIndex(this.topMarkerKeyIndex);
        int $$1 = $$0;
        int $$2 = this.topMarkerKeyIndex;
        while ($$2 < this.topEntryKeyIndex) {
            $$1 += 2;
            $$2 += 2;
            Object $$3 = this.stack[$$2];
            if (!$assertionsDisabled && !($$3 instanceof Atom)) {
                throw new AssertionError();
            }
            Object $$4 = this.stack[$$2 + 1];
            Object $$5 = this.stack[$$1];
            if ($$5 != $$3) {
                this.stack[$$1] = $$3;
                this.stack[$$1 + 1] = $$4;
            } else if ($$4 != null) {
                this.stack[$$1 + 1] = $$4;
            }
        }
        this.topEntryKeyIndex = $$1;
        this.topMarkerKeyIndex = $$0;
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    public <T> void put(Atom<T> $$0, T $$1) {
        int $$2 = valueIndex($$0);
        if ($$2 != -1) {
            this.stack[$$2] = $$1;
        } else {
            ensureCapacity(1);
            this.topEntryKeyIndex += 2;
            this.stack[this.topEntryKeyIndex] = $$0;
            this.stack[this.topEntryKeyIndex + 1] = $$1;
        }
        if (!$assertionsDisabled && !validateStructure()) {
            throw new AssertionError();
        }
    }

    public <T> T get(Atom<T> atom) {
        int iValueIndex = valueIndex(atom);
        if (iValueIndex != -1) {
            return (T) this.stack[iValueIndex];
        }
        return null;
    }

    public <T> T getOrThrow(Atom<T> atom) {
        int iValueIndex = valueIndex(atom);
        if (iValueIndex == -1) {
            throw new IllegalArgumentException("No value for atom " + String.valueOf(atom));
        }
        return (T) this.stack[iValueIndex];
    }

    public <T> T getOrDefault(Atom<T> atom, T t) {
        int iValueIndex = valueIndex(atom);
        return iValueIndex != -1 ? (T) this.stack[iValueIndex] : t;
    }

    @SafeVarargs
    public final <T> T getAny(Atom<? extends T>... atomArr) {
        int iValueIndexForAny = valueIndexForAny(atomArr);
        if (iValueIndexForAny != -1) {
            return (T) this.stack[iValueIndexForAny];
        }
        return null;
    }

    @SafeVarargs
    public final <T> T getAnyOrThrow(Atom<? extends T>... atomArr) {
        int iValueIndexForAny = valueIndexForAny(atomArr);
        if (iValueIndexForAny == -1) {
            throw new IllegalArgumentException("No value for atoms " + Arrays.toString(atomArr));
        }
        return (T) this.stack[iValueIndexForAny];
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        boolean $$1 = true;
        for (int $$2 = 0; $$2 <= this.topEntryKeyIndex; $$2 += 2) {
            Object $$3 = this.stack[$$2];
            Object $$4 = this.stack[$$2 + 1];
            if ($$3 == FRAME_START_MARKER) {
                $$0.append('|');
                $$1 = true;
            } else {
                if (!$$1) {
                    $$0.append(',');
                }
                $$1 = false;
                $$0.append($$3).append(':').append($$4);
            }
        }
        return $$0.toString();
    }

    @VisibleForTesting
    public Map<Atom<?>, ?> lastFrame() {
        HashMap<Atom<?>, Object> $$0 = new HashMap<>();
        for (int $$1 = this.topEntryKeyIndex; $$1 > this.topMarkerKeyIndex; $$1 -= 2) {
            Object $$2 = this.stack[$$1];
            Object $$3 = this.stack[$$1 + 1];
            $$0.put((Atom) $$2, $$3);
        }
        return $$0;
    }

    public boolean hasOnlySingleFrame() {
        for (int $$0 = this.topEntryKeyIndex; $$0 > 0; $$0--) {
            if (this.stack[$$0] == FRAME_START_MARKER) {
                return false;
            }
        }
        if (this.stack[0] != FRAME_START_MARKER) {
            throw new IllegalStateException("Corrupted stack");
        }
        return true;
    }

    private boolean validateStructure() {
        if (!$assertionsDisabled && this.topMarkerKeyIndex < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.topEntryKeyIndex < this.topMarkerKeyIndex) {
            throw new AssertionError();
        }
        for (int $$0 = 0; $$0 <= this.topEntryKeyIndex; $$0 += 2) {
            Object $$1 = this.stack[$$0];
            if ($$1 != FRAME_START_MARKER && !($$1 instanceof Atom)) {
                return false;
            }
        }
        int previousMarkerIndex = this.topMarkerKeyIndex;
        while (true) {
            int $$2 = previousMarkerIndex;
            if ($$2 != 0) {
                Object $$3 = this.stack[$$2];
                if ($$3 != FRAME_START_MARKER) {
                    return false;
                }
                previousMarkerIndex = getPreviousMarkerIndex($$2);
            } else {
                return true;
            }
        }
    }
}
