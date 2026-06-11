package net.minecraft.nbt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/CollectionTag.class */
public interface CollectionTag extends Iterable<Tag>, Tag {
    void clear();

    boolean setTag(int i, Tag tag);

    boolean addTag(int i, Tag tag);

    Tag remove(int i);

    Tag get(int i);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.lang.Iterable
    default Iterator<Tag> iterator() {
        return new Iterator<Tag>() { // from class: net.minecraft.nbt.CollectionTag.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < CollectionTag.this.size();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public Tag next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                CollectionTag collectionTag = CollectionTag.this;
                int i = this.index;
                this.index = i + 1;
                return collectionTag.get(i);
            }
        };
    }

    default Stream<Tag> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
