package net.labymod.v1_18_2.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<axc, axe> {
    private final Map<axc, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<axc, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(axc key, axe value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(axc key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(axc key, axe value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
