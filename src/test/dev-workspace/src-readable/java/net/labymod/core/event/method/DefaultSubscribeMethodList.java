package net.labymod.core.event.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import net.labymod.api.event.method.SubscribeMethod;
import net.labymod.api.event.method.SubscribeMethodList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/method/DefaultSubscribeMethodList.class */
public class DefaultSubscribeMethodList implements SubscribeMethodList {
    private final List<SubscribeMethod> subscribeMethods = new ArrayList();
    private SubscribeMethod[] bakedSubscribeMethods = new SubscribeMethod[0];

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public void add(SubscribeMethod method) {
        this.subscribeMethods.add(method);
        buildBakedArray();
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public boolean remove(SubscribeMethod method) {
        boolean remove = this.subscribeMethods.remove(method);
        if (remove) {
            buildBakedArray();
        }
        return remove;
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public boolean isEmpty() {
        return this.bakedSubscribeMethods.length == 0;
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public void removeIf(Predicate<SubscribeMethod> filter) {
        if (this.subscribeMethods.removeIf(filter)) {
            buildBakedArray();
        }
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public void merge(SubscribeMethodList other) {
        this.subscribeMethods.addAll(Arrays.asList(other.getSubscribeMethods()));
        buildBakedArray();
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public void sort() {
        sortMethods();
        buildBakedArray();
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public void mergeSort(SubscribeMethodList other) {
        this.subscribeMethods.addAll(Arrays.asList(other.getSubscribeMethods()));
        sortMethods();
        buildBakedArray();
    }

    private void sortMethods() {
        this.subscribeMethods.sort(Comparator.comparingInt((v0) -> {
            return v0.getPriority();
        }));
    }

    @Override // net.labymod.api.event.method.SubscribeMethodList
    public SubscribeMethod[] getSubscribeMethods() {
        return this.bakedSubscribeMethods;
    }

    private void buildBakedArray() {
        this.bakedSubscribeMethods = (SubscribeMethod[]) this.subscribeMethods.toArray(new SubscribeMethod[0]);
    }
}
