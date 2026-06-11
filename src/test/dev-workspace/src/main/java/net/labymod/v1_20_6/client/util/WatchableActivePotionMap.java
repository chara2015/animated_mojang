package net.labymod.v1_20_6.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<ji<bsc>, bse> {
    private final Map<ji<bsc>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<ji<bsc>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(ji<bsc> key, bse value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(ji<bsc> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(ji<bsc> key, bse value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
