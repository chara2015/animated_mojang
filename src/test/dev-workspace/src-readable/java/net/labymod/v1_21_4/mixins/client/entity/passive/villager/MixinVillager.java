package net.labymod.v1_21_4.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({coj.class})
public abstract class MixinVillager implements Villager {
    @Shadow
    public abstract cok gC();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        cok villagerData = gC();
        return VillagerJob.getOrCreate(villagerData.b().a(), villagerData.c());
    }
}
