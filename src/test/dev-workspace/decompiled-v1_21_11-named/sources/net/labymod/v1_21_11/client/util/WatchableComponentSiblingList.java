package net.labymod.v1_21_11.client.util;

import java.util.List;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.v1_21_11.client.network.chat.MutableComponentAccessor;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/WatchableComponentSiblingList.class */
public class WatchableComponentSiblingList implements WatchableList<Component> {
    private final List<net.labymod.api.client.component.Component> children;

    public WatchableComponentSiblingList(List<net.labymod.api.client.component.Component> children) {
        this.children = children;
    }

    public void onAdd(Component component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(accessor.getLabyComponent());
        }
    }

    public void onAdd(int index, Component component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(index, accessor.getLabyComponent());
        }
    }

    public void onRemove(Component component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.remove(accessor.getLabyComponent());
        }
    }

    public void onClear() {
        this.children.clear();
    }
}
