package net.labymod.api.util.collection;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/collection/EvictingQueue.class */
public class EvictingQueue<E> extends AbstractQueue<E> {
    private final Deque<E> deque;
    private final int capacity;

    public EvictingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
        this.capacity = capacity;
        this.deque = new ArrayDeque(capacity);
    }

    public int capacity() {
        return this.capacity;
    }

    @Override // java.util.Queue
    public boolean offer(E e) {
        Objects.requireNonNull(e, "element");
        if (this.deque.size() == this.capacity) {
            this.deque.removeFirst();
        }
        this.deque.addLast(e);
        return true;
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue
    public boolean add(E e) {
        return offer(e);
    }

    @Override // java.util.Queue
    public E poll() {
        return this.deque.poll();
    }

    @Override // java.util.Queue
    public E peek() {
        return this.deque.peekFirst();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return this.deque.iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.deque.size();
    }

    @Override // java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection
    public void clear() {
        this.deque.clear();
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return "EvictingQueue(capacity=" + this.capacity + ", elements=" + String.valueOf(this.deque) + ")";
    }
}
