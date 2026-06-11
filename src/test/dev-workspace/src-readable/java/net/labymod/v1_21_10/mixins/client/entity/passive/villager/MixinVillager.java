package net.labymod.v1_21_10.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({cyx.class})
public abstract class MixinVillager implements Villager {
    @Shadow
    public abstract cyy gR();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        cyy villagerData = gR();
        jk<cza> profession = villagerData.b();
        String name = (String) profession.e().map(key -> {
            return key.a().a();
        }).orElse("none");
        return VillagerJob.getOrCreate(name, villagerData.c());
    }
}
