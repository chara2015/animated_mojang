package net.labymod.v1_20_4.client.util;

import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/util/WatchableComponentSiblingList.class */
public class WatchableComponentSiblingList implements WatchableList<vf> {
    private final List<Component> children;

    public WatchableComponentSiblingList(List<Component> children) {
        this.children = children;
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAdd(vf component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.list.WatchableList
    public void onAdd(int index, vf component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(index, accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onRemove(vf component) {
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
