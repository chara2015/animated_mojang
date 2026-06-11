package net.labymod.v1_20_4.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<blg, bli> {
    private final Map<blg, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<blg, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(blg key, bli value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(blg key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(blg key, bli value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
