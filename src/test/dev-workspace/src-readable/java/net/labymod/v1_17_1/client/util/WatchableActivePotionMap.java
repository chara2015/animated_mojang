package net.labymod.v1_17_1.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<asy, ata> {
    private final Map<asy, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<asy, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(asy key, ata value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(asy key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(asy key, ata value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
