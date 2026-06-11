package net.minecraft.util.parsing.packrat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ErrorCollector.class */
public interface ErrorCollector<S> {
    void store(int i, SuggestionSupplier<S> suggestionSupplier, Object obj);

    void finish(int i);

    default void store(int $$0, Object $$1) {
        store($$0, SuggestionSupplier.empty(), $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ErrorCollector$Nop.class */
    public static class Nop<S> implements ErrorCollector<S> {
        @Override // net.minecraft.util.parsing.packrat.ErrorCollector
        public void store(int $$0, SuggestionSupplier<S> $$1, Object $$2) {
        }

        @Override // net.minecraft.util.parsing.packrat.ErrorCollector
        public void finish(int $$0) {
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ErrorCollector$LongestOnly.class */
    public static class LongestOnly<S> implements ErrorCollector<S> {
        private int nextErrorEntry;
        private MutableErrorEntry<S>[] entries = new MutableErrorEntry[16];
        private int lastCursor = -1;

        /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ErrorCollector$LongestOnly$MutableErrorEntry.class */
        static class MutableErrorEntry<S> {
            SuggestionSupplier<S> suggestions = SuggestionSupplier.empty();
            Object reason = "empty";

            MutableErrorEntry() {
            }
        }

        private void discardErrorsFromShorterParse(int $$0) {
            if ($$0 > this.lastCursor) {
                this.lastCursor = $$0;
                this.nextErrorEntry = 0;
            }
        }

        @Override // net.minecraft.util.parsing.packrat.ErrorCollector
        public void finish(int $$0) {
            discardErrorsFromShorterParse($$0);
        }

        @Override // net.minecraft.util.parsing.packrat.ErrorCollector
        public void store(int $$0, SuggestionSupplier<S> $$1, Object $$2) {
            discardErrorsFromShorterParse($$0);
            if ($$0 == this.lastCursor) {
                addErrorEntry($$1, $$2);
            }
        }

        private void addErrorEntry(SuggestionSupplier<S> $$0, Object $$1) {
            int $$2 = this.entries.length;
            if (this.nextErrorEntry >= $$2) {
                int $$3 = Util.growByHalf($$2, this.nextErrorEntry + 1);
                MutableErrorEntry<S>[] $$4 = new MutableErrorEntry[$$3];
                System.arraycopy(this.entries, 0, $$4, 0, $$2);
                this.entries = $$4;
            }
            int $$5 = this.nextErrorEntry;
            this.nextErrorEntry = $$5 + 1;
            MutableErrorEntry<S> $$6 = this.entries[$$5];
            if ($$6 == null) {
                $$6 = new MutableErrorEntry<>();
                this.entries[$$5] = $$6;
            }
            $$6.suggestions = $$0;
            $$6.reason = $$1;
        }

        public List<ErrorEntry<S>> entries() {
            int $$0 = this.nextErrorEntry;
            if ($$0 == 0) {
                return List.of();
            }
            List<ErrorEntry<S>> $$1 = new ArrayList<>($$0);
            for (int $$2 = 0; $$2 < $$0; $$2++) {
                MutableErrorEntry<S> $$3 = this.entries[$$2];
                $$1.add(new ErrorEntry<>(this.lastCursor, $$3.suggestions, $$3.reason));
            }
            return $$1;
        }

        public int cursor() {
            return this.lastCursor;
        }
    }
}
