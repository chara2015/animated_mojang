package net.labymod.v1_21_8.client.util;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.v1_21_8.client.network.chat.MutableComponentAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/util/WatchableComponentSiblingList.class */
public class WatchableComponentSiblingList implements WatchableList<xo> {
    private final List<Component> children;

    public WatchableComponentSiblingList(List<Component> children) {
        this.children = children;
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAdd(xo component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.list.WatchableList
    public void onAdd(int index, xo component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(index, accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onRemove(xo component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.remove(accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onClear() {
        this.children.clear();
    }
}
