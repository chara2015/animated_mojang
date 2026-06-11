package net.minecraft.util.parsing.packrat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.Util;
import net.minecraft.util.parsing.packrat.ErrorCollector;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/CachedParseState.class */
public abstract class CachedParseState<S> implements ParseState<S> {
    private final ErrorCollector<S> errorCollector;
    private int nextControlToReturn;
    private PositionCache[] positionCache = new PositionCache[256];
    private final Scope scope = new Scope();
    private SimpleControl[] controlCache = new SimpleControl[16];
    private final CachedParseState<S>.Silent silent = new Silent();

    protected CachedParseState(ErrorCollector<S> $$0) {
        this.errorCollector = $$0;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public Scope scope() {
        return this.scope;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public ErrorCollector<S> errorCollector() {
        return this.errorCollector;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public <T> T parse(NamedRule<S, T> $$0) {
        CacheEntry<?> cacheEntry;
        int $$1 = mark();
        PositionCache $$2 = getCacheForPosition($$1);
        int $$3 = $$2.findKeyIndex($$0.name());
        if ($$3 != -1) {
            CacheEntry<T> $$4 = $$2.getValue($$3);
            if ($$4 != null) {
                if ($$4 == CacheEntry.NEGATIVE) {
                    return null;
                }
                restore(((CacheEntry) $$4).markAfterParse);
                return ((CacheEntry) $$4).value;
            }
        } else {
            $$3 = $$2.allocateNewEntry($$0.name());
        }
        T $$5 = $$0.value().parse(this);
        if ($$5 == null) {
            cacheEntry = CacheEntry.negativeEntry();
        } else {
            int $$7 = mark();
            cacheEntry = new CacheEntry<>($$5, $$7);
        }
        $$2.setValue($$3, cacheEntry);
        return $$5;
    }

    private PositionCache getCacheForPosition(int $$0) {
        int $$1 = this.positionCache.length;
        if ($$0 >= $$1) {
            int $$2 = Util.growByHalf($$1, $$0 + 1);
            PositionCache[] $$3 = new PositionCache[$$2];
            System.arraycopy(this.positionCache, 0, $$3, 0, $$1);
            this.positionCache = $$3;
        }
        PositionCache $$4 = this.positionCache[$$0];
        if ($$4 == null) {
            $$4 = new PositionCache();
            this.positionCache[$$0] = $$4;
        }
        return $$4;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public Control acquireControl() {
        int $$0 = this.controlCache.length;
        if (this.nextControlToReturn >= $$0) {
            int $$1 = Util.growByHalf($$0, this.nextControlToReturn + 1);
            SimpleControl[] $$2 = new SimpleControl[$$1];
            System.arraycopy(this.controlCache, 0, $$2, 0, $$0);
            this.controlCache = $$2;
        }
        int $$3 = this.nextControlToReturn;
        this.nextControlToReturn = $$3 + 1;
        SimpleControl $$4 = this.controlCache[$$3];
        if ($$4 == null) {
            $$4 = new SimpleControl();
            this.controlCache[$$3] = $$4;
        } else {
            $$4.reset();
        }
        return $$4;
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public void releaseControl() {
        this.nextControlToReturn--;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/CachedParseState$PositionCache.class */
    static class PositionCache {
        public static final int ENTRY_STRIDE = 2;
        private static final int NOT_FOUND = -1;
        private Object[] atomCache = new Object[16];
        private int nextKey;

        PositionCache() {
        }

        public int findKeyIndex(Atom<?> $$0) {
            for (int $$1 = 0; $$1 < this.nextKey; $$1 += 2) {
                if (this.atomCache[$$1] == $$0) {
                    return $$1;
                }
            }
            return -1;
        }

        public int allocateNewEntry(Atom<?> $$0) {
            int $$1 = this.nextKey;
            this.nextKey += 2;
            int $$2 = $$1 + 1;
            int $$3 = this.atomCache.length;
            if ($$2 >= $$3) {
                int $$4 = Util.growByHalf($$3, $$2 + 1);
                Object[] $$5 = new Object[$$4];
                System.arraycopy(this.atomCache, 0, $$5, 0, $$3);
                this.atomCache = $$5;
            }
            this.atomCache[$$1] = $$0;
            return $$1;
        }

        public <T> CacheEntry<T> getValue(int $$0) {
            return (CacheEntry) this.atomCache[$$0 + 1];
        }

        public void setValue(int $$0, CacheEntry<?> $$1) {
            this.atomCache[$$0 + 1] = $$1;
        }
    }

    @Override // net.minecraft.util.parsing.packrat.ParseState
    public ParseState<S> silent() {
        return this.silent;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/CachedParseState$CacheEntry.class */
    static final class CacheEntry<T> extends Record {
        private final T value;
        private final int markAfterParse;
        public static final CacheEntry<?> NEGATIVE = new CacheEntry<>(null, -1);

        CacheEntry(T $$0, int $$1) {
            this.value = $$0;
            this.markAfterParse = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CacheEntry.class), CacheEntry.class, "value;markAfterParse", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->markAfterParse:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CacheEntry.class), CacheEntry.class, "value;markAfterParse", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->markAfterParse:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CacheEntry.class, Object.class), CacheEntry.class, "value;markAfterParse", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->value:Ljava/lang/Object;", "FIELD:Lnet/minecraft/util/parsing/packrat/CachedParseState$CacheEntry;->markAfterParse:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public T value() {
            return this.value;
        }

        public int markAfterParse() {
            return this.markAfterParse;
        }

        public static <T> CacheEntry<T> negativeEntry() {
            return (CacheEntry<T>) NEGATIVE;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/CachedParseState$Silent.class */
    class Silent implements ParseState<S> {
        private final ErrorCollector<S> silentCollector = new ErrorCollector.Nop();

        Silent() {
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public ErrorCollector<S> errorCollector() {
            return this.silentCollector;
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public Scope scope() {
            return CachedParseState.this.scope();
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public <T> T parse(NamedRule<S, T> namedRule) {
            return (T) CachedParseState.this.parse(namedRule);
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public S input() {
            return CachedParseState.this.input();
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public int mark() {
            return CachedParseState.this.mark();
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public void restore(int $$0) {
            CachedParseState.this.restore($$0);
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public Control acquireControl() {
            return CachedParseState.this.acquireControl();
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public void releaseControl() {
            CachedParseState.this.releaseControl();
        }

        @Override // net.minecraft.util.parsing.packrat.ParseState
        public ParseState<S> silent() {
            return this;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/CachedParseState$SimpleControl.class */
    static class SimpleControl implements Control {
        private boolean hasCut;

        SimpleControl() {
        }

        @Override // net.minecraft.util.parsing.packrat.Control
        public void cut() {
            this.hasCut = true;
        }

        @Override // net.minecraft.util.parsing.packrat.Control
        public boolean hasCut() {
            return this.hasCut;
        }

        public void reset() {
            this.hasCut = false;
        }
    }
}
