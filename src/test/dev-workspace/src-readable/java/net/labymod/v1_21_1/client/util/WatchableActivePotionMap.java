package net.labymod.v1_21_1.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jm<brx>, brz> {
    private final Map<jm<brx>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jm<brx>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jm<brx> key, brz value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jm<brx> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jm<brx> key, brz value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
