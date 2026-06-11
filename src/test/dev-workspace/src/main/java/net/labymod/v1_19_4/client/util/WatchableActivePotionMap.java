package net.labymod.v1_19_4.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<bew, bey> {
    private final Map<bew, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<bew, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(bew key, bey value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(bew key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(bew key, bey value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
