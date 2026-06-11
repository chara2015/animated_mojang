package net.minecraft.client.gui.narration;

import com.google.common.collect.Maps;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/narration/ScreenNarrationCollector.class */
public class ScreenNarrationCollector {
    int generation;
    final Map<EntryKey, NarrationEntry> entries = Maps.newTreeMap(Comparator.comparing($$0 -> {
        return $$0.type;
    }).thenComparing($$02 -> {
        return Integer.valueOf($$02.depth);
    }));

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/narration/ScreenNarrationCollector$Output.class */
    class Output implements NarrationElementOutput {
        private final int depth;

        Output(int $$0) {
            this.depth = $$0;
        }

        @Override // net.minecraft.client.gui.narration.NarrationElementOutput
        public void add(NarratedElementType $$0, NarrationThunk<?> $$1) {
            ScreenNarrationCollector.this.entries.computeIfAbsent(new EntryKey($$0, this.depth), $$02 -> {
                return new NarrationEntry();
            }).update(ScreenNarrationCollector.this.generation, $$1);
        }

        @Override // net.minecraft.client.gui.narration.NarrationElementOutput
        public NarrationElementOutput nest() {
            return ScreenNarrationCollector.this.new Output(this.depth + 1);
        }
    }

    public void update(Consumer<NarrationElementOutput> $$0) {
        this.generation++;
        $$0.accept(new Output(0));
    }

    public String collectNarrationText(boolean $$0) {
        final StringBuilder $$1 = new StringBuilder();
        Consumer<String> $$2 = new Consumer<String>(this) { // from class: net.minecraft.client.gui.narration.ScreenNarrationCollector.1
            private boolean firstEntry = true;

            @Override // java.util.function.Consumer
            public void accept(String $$02) {
                if (!this.firstEntry) {
                    $$1.append(". ");
                }
                this.firstEntry = false;
                $$1.append($$02);
            }
        };
        this.entries.forEach(($$22, $$3) -> {
            if ($$3.generation == this.generation) {
                if ($$0 || !$$3.alreadyNarrated) {
                    $$3.contents.getText($$2);
                    $$3.alreadyNarrated = true;
                }
            }
        });
        return $$1.toString();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey.class */
    static final class EntryKey extends Record {
        private final NarratedElementType type;
        private final int depth;

        EntryKey(NarratedElementType $$0, int $$1) {
            this.type = $$0;
            this.depth = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EntryKey.class), EntryKey.class, "type;depth", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->type:Lnet/minecraft/client/gui/narration/NarratedElementType;", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->depth:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EntryKey.class), EntryKey.class, "type;depth", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->type:Lnet/minecraft/client/gui/narration/NarratedElementType;", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->depth:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EntryKey.class, Object.class), EntryKey.class, "type;depth", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->type:Lnet/minecraft/client/gui/narration/NarratedElementType;", "FIELD:Lnet/minecraft/client/gui/narration/ScreenNarrationCollector$EntryKey;->depth:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public NarratedElementType type() {
            return this.type;
        }

        public int depth() {
            return this.depth;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/narration/ScreenNarrationCollector$NarrationEntry.class */
    static class NarrationEntry {
        NarrationThunk<?> contents = NarrationThunk.EMPTY;
        int generation = -1;
        boolean alreadyNarrated;

        NarrationEntry() {
        }

        public NarrationEntry update(int $$0, NarrationThunk<?> $$1) {
            if (!this.contents.equals($$1)) {
                this.contents = $$1;
                this.alreadyNarrated = false;
            } else if (this.generation + 1 != $$0) {
                this.alreadyNarrated = false;
            }
            this.generation = $$0;
            return this;
        }
    }
}
