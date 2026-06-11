package net.labymod.v1_21_11.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({dcx.class})
public abstract class MixinVillager implements Villager {
    @Shadow
    public abstract dcy gZ();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        dcy villagerData = gZ();
        jd<dda> profession = villagerData.b();
        String name = (String) profession.e().map(key -> {
            return key.a().a();
        }).orElse("none");
        return VillagerJob.getOrCreate(name, villagerData.c());
    }
}
