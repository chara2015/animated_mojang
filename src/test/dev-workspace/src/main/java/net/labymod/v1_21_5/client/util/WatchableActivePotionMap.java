package net.labymod.v1_21_5.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jg<bwg>, bwi> {
    private final Map<jg<bwg>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jg<bwg>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jg<bwg> key, bwi value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jg<bwg> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jg<bwg> key, bwi value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
