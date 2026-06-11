package net.labymod.v1_8_9.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<Integer, pf> {
    private final Map<Integer, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<Integer, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(Integer key, pf value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(Integer key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(Integer key, pf value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
