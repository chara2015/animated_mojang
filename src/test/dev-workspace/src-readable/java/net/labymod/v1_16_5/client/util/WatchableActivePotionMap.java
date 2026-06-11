package net.labymod.v1_16_5.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<aps, apu> {
    private final Map<aps, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<aps, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(aps key, apu value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(aps key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(aps key, apu value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
