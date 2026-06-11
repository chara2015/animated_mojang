package net.labymod.v1_21_11.client.util;

import java.util.Map;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.core.watcher.map.WatchableMap;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/WatchableActivePotionMap.class */
public class WatchableActivePotionMap implements WatchableMap<Holder<MobEffect>, MobEffectInstance> {
    private final Map<Holder<MobEffect>, PotionEffect> activePotions;

    public WatchableActivePotionMap(Map<Holder<MobEffect>, PotionEffect> activePotions) {
        this.activePotions = activePotions;
    }

    public void onPut(Holder<MobEffect> key, MobEffectInstance value) {
        this.activePotions.put(key, (PotionEffect) value);
    }

    public void onClear() {
        this.activePotions.clear();
    }

    public void onRemove(Holder<MobEffect> key) {
        this.activePotions.remove(key);
    }

    public void onRemove(Holder<MobEffect> key, MobEffectInstance value) {
        this.activePotions.remove(key, (PotionEffect) value);
    }
}
