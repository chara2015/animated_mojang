package net.labymod.api.client.component.flattener;

import net.labymod.api.client.component.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/flattener/FlattenerListener.class */
public interface FlattenerListener {
    void component(String str);

    default void push(Component source) {
    }

    default void pop(Component source) {
    }
}
