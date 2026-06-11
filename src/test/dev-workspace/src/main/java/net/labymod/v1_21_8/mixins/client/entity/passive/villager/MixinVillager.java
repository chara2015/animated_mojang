package net.labymod.v1_21_8.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({cuf.class})
public abstract class MixinVillager implements Villager {
    @Shadow
    public abstract cug gR();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        cug villagerData = gR();
        jl<cui> profession = villagerData.b();
        String name = (String) profession.e().map(key -> {
            return key.a().a();
        }).orElse("none");
        return VillagerJob.getOrCreate(name, villagerData.c());
    }
}
