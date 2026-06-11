package net.minecraft.util;

import java.io.Serializable;
import java.util.Deque;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ListAndDeque.class */
public interface ListAndDeque<T> extends Serializable, Cloneable, Deque<T>, List<T>, RandomAccess {
    @Override // 
    /* JADX INFO: renamed from: reversed, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    ListAndDeque<T> mo2325reversed();

    T getFirst();

    T getLast();

    void addFirst(T t);

    void addLast(T t);

    T removeFirst();

    T removeLast();

    @Override // java.util.Deque, java.util.Queue
    default boolean offer(T $$0) {
        return offerLast($$0);
    }

    @Override // java.util.Deque, java.util.Queue
    default T remove() {
        return removeFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    default T poll() {
        return pollFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    default T element() {
        return getFirst();
    }

    @Override // java.util.Deque, java.util.Queue
    default T peek() {
        return peekFirst();
    }

    @Override // java.util.Deque
    default void push(T $$0) {
        addFirst($$0);
    }

    @Override // java.util.Deque
    default T pop() {
        return removeFirst();
    }
}
