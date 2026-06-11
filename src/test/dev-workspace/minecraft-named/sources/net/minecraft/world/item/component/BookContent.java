package net.minecraft.world.item.component;

import java.util.List;
import net.minecraft.server.network.Filterable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/component/BookContent.class */
public interface BookContent<T, C> {
    List<Filterable<T>> pages();

    C withReplacedPages(List<Filterable<T>> list);
}
