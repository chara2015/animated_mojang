package net.labymod.v1_21_4.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jr<btp>, btr> {
    private final Map<jr<btp>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jr<btp>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jr<btp> key, btr value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jr<btp> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jr<btp> key, btr value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
