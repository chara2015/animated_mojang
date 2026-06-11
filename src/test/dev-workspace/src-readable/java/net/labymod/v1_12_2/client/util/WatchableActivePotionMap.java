package net.labymod.v1_12_2.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<uz, va> {
    private final Map<uz, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<uz, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(uz key, va value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(uz key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(uz key, va value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
