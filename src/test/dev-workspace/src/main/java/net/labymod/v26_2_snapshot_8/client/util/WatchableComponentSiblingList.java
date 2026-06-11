package net.labymod.v26_2_snapshot_8.client.util;

import java.util.List;
import net.labymod.core.watcher.list.WatchableList;
import net.labymod.v26_2_snapshot_8.client.network.chat.MutableComponentAccessor;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/util/WatchableComponentSiblingList.class */
public class WatchableComponentSiblingList implements WatchableList<Component> {
    private final List<net.labymod.api.client.component.Component> children;

    public WatchableComponentSiblingList(List<net.labymod.api.client.component.Component> children) {
        this.children = children;
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAdd(Component component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.list.WatchableList
    public void onAdd(int index, Component component) {
        if (component instanceof MutableComponentAccessor) {
            MutableComponentAccessor accessor = (MutableComponentAccessor) component;
            this.children.add(index, accessor.getLabyComponent());
        }
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onRemove(Component component) {
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
