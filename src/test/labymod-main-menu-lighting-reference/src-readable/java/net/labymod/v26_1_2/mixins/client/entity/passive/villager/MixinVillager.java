package net.labymod.v26_1_2.mixins.client.entity.passive.villager;

import net.labymod.api.client.entity.passive.villager.VillagerJob;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerData;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/entity/passive/villager/MixinVillager.class */
@Mixin({Villager.class})
public abstract class MixinVillager implements net.labymod.api.client.entity.passive.villager.Villager {
    @Shadow
    public abstract VillagerData getVillagerData();

    @Override // net.labymod.api.client.entity.passive.villager.Villager
    public VillagerJob job() {
        VillagerData villagerData = getVillagerData();
        Holder<VillagerProfession> profession = villagerData.profession();
        String name = (String) profession.unwrapKey().map(key -> {
            return key.identifier().getPath();
        }).orElse("none");
        return VillagerJob.getOrCreate(name, villagerData.level());
    }
}
