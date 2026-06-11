package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ClassInstanceMultiMap.class */
public class ClassInstanceMultiMap<T> extends AbstractCollection<T> {
    private final Class<T> baseClass;
    private final Map<Class<?>, List<T>> byClass = Maps.newHashMap();
    private final List<T> allInstances = Lists.newArrayList();

    public ClassInstanceMultiMap(Class<T> $$0) {
        this.baseClass = $$0;
        this.byClass.put($$0, this.allInstances);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean add(T $$0) {
        boolean $$1 = false;
        for (Map.Entry<Class<?>, List<T>> $$2 : this.byClass.entrySet()) {
            if ($$2.getKey().isInstance($$0)) {
                $$1 |= $$2.getValue().add($$0);
            }
        }
        return $$1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean remove(Object $$0) {
        boolean $$1 = false;
        for (Map.Entry<Class<?>, List<T>> $$2 : this.byClass.entrySet()) {
            if ($$2.getKey().isInstance($$0)) {
                List<T> $$3 = $$2.getValue();
                $$1 |= $$3.remove($$0);
            }
        }
        return $$1;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean contains(Object $$0) {
        return find($$0.getClass()).contains($$0);
    }

    public <S> Collection<S> find(Class<S> $$0) {
        if (!this.baseClass.isAssignableFrom($$0)) {
            throw new IllegalArgumentException("Don't know how to search for " + String.valueOf($$0));
        }
        return Collections.unmodifiableCollection(this.byClass.computeIfAbsent($$0, $$02 -> {
            Stream<T> stream = this.allInstances.stream();
            Objects.requireNonNull($$02);
            return (List) stream.filter($$02::isInstance).collect(Util.toMutableList());
        }));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        if (this.allInstances.isEmpty()) {
            return Collections.emptyIterator();
        }
        return Iterators.unmodifiableIterator(this.allInstances.iterator());
    }

    public List<T> getAllInstances() {
        return ImmutableList.copyOf(this.allInstances);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.allInstances.size();
    }
}
