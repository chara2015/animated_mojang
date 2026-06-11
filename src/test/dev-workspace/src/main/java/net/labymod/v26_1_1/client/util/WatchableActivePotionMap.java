package net.labymod.v26_1_1.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<Holder<MobEffect>, MobEffectInstance> {
    private final Map<Holder<MobEffect>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<Holder<MobEffect>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onPut(Holder<MobEffect> key, MobEffectInstance value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onClear() {
        this.activePotions.clear();
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(Holder<MobEffect> key) {
        this.activePotions.remove(key);
    }

    @Override // net.labymod.core.watcher.map.WatchableMap
    public void onRemove(Holder<MobEffect> key, MobEffectInstance value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
