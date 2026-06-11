package net.labymod.v1_12_2.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({ady.class})
public abstract class MixinVillager implements Villager {
    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        return VillagerJob.NITWIT;
    }
}
