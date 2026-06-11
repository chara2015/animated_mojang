package net.labymod.v1_21_3.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jq<bun>, bup> {
    private final Map<jq<bun>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jq<bun>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jq<bun> key, bup value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jq<bun> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jq<bun> key, bup value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
