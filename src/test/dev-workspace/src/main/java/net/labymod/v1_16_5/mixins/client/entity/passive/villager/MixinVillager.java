package net.labymod.v1_16_5.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({bfj.class})
public abstract class MixinVillager implements Villager {
    @Shadow
    public abstract bfk eX();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        bfk villagerData = eX();
        return VillagerJob.getOrCreate(villagerData.b().toString(), villagerData.c());
    }
}
