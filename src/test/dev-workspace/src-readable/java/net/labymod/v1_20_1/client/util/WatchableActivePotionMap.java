package net.labymod.v1_20_1.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<bey, bfa> {
    private final Map<bey, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<bey, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(bey key, bfa value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(bey key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(bey key, bfa value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
