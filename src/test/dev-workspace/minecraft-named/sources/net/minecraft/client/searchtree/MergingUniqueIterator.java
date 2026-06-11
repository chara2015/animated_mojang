package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/searchtree/MergingUniqueIterator.class */
public class MergingUniqueIterator<T> extends AbstractIterator<T> {
    private final PeekingIterator<T> firstIterator;
    private final PeekingIterator<T> secondIterator;
    private final Comparator<T> comparator;

    public MergingUniqueIterator(Iterator<T> $$0, Iterator<T> $$1, Comparator<T> $$2) {
        this.firstIterator = Iterators.peekingIterator($$0);
        this.secondIterator = Iterators.peekingIterator($$1);
        this.comparator = $$2;
    }

    protected T computeNext() {
        boolean z = !this.firstIterator.hasNext();
        boolean z2 = !this.secondIterator.hasNext();
        if (z && z2) {
            return (T) endOfData();
        }
        if (z) {
            return (T) this.secondIterator.next();
        }
        if (z2) {
            return (T) this.firstIterator.next();
        }
        int iCompare = this.comparator.compare((T) this.firstIterator.peek(), (T) this.secondIterator.peek());
        if (iCompare == 0) {
            this.secondIterator.next();
        }
        return iCompare <= 0 ? (T) this.firstIterator.next() : (T) this.secondIterator.next();
    }
}
