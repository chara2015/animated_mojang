package net.labymod.core.client.resources.pack.util;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.core.client.resources.pack.AbstractResourcePackRepository;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/pack/util/EventResourcePackRepositoryCaller.class */
public final class EventResourcePackRepositoryCaller {
    private EventResourcePackRepositoryCaller() {
    }

    public static <T> void onRebuildSelected(List<T> selected) {
        ResourcePackRepository repository = Laby.references().resourcePackRepository();
        if (!(repository instanceof AbstractResourcePackRepository)) {
            return;
        }
        ((AbstractResourcePackRepository) repository).onReload(selected);
    }
}
