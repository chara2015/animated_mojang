package net.labymod.api.event.method;

import java.util.function.Predicate;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/method/SubscribeMethodList.class */
public interface SubscribeMethodList {
    void add(SubscribeMethod subscribeMethod);

    boolean remove(SubscribeMethod subscribeMethod);

    boolean isEmpty();

    void removeIf(Predicate<SubscribeMethod> predicate);

    void merge(SubscribeMethodList subscribeMethodList);

    void sort();

    void mergeSort(SubscribeMethodList subscribeMethodList);

    SubscribeMethod[] getSubscribeMethods();
}
