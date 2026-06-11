package net.labymod.v1_21_8.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jl<byo>, byq> {
    private final Map<jl<byo>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jl<byo>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jl<byo> key, byq value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jl<byo> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jl<byo> key, byq value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
