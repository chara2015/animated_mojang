package net.minecraft.client.searchtree;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/searchtree/IntersectionIterator.class */
public class IntersectionIterator<T> extends AbstractIterator<T> {
    private final PeekingIterator<T> firstIterator;
    private final PeekingIterator<T> secondIterator;
    private final Comparator<T> comparator;

    public IntersectionIterator(Iterator<T> $$0, Iterator<T> $$1, Comparator<T> $$2) {
        this.firstIterator = Iterators.peekingIterator($$0);
        this.secondIterator = Iterators.peekingIterator($$1);
        this.comparator = $$2;
    }

    protected T computeNext() {
        while (this.firstIterator.hasNext() && this.secondIterator.hasNext()) {
            int iCompare = this.comparator.compare((T) this.firstIterator.peek(), (T) this.secondIterator.peek());
            if (iCompare == 0) {
                this.secondIterator.next();
                return (T) this.firstIterator.next();
            }
            if (iCompare < 0) {
                this.firstIterator.next();
            } else {
                this.secondIterator.next();
            }
        }
        return (T) endOfData();
    }
}
