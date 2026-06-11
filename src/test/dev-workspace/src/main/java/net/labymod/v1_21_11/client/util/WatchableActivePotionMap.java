package net.labymod.v1_21_11.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<jd<cfk>, cfm> {
    private final Map<jd<cfk>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<jd<cfk>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(jd<cfk> key, cfm value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jd<cfk> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(jd<cfk> key, cfm value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
