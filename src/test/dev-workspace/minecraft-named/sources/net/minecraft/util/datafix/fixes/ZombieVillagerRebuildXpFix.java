package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ZombieVillagerRebuildXpFix.class */
public class ZombieVillagerRebuildXpFix extends NamedEntityFix {
    public ZombieVillagerRebuildXpFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "Zombie Villager XP rebuild", References.ENTITY, "minecraft:zombie_villager");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            Optional<Number> $$1 = $$02.get("Xp").asNumber().result();
            if ($$1.isEmpty()) {
                int $$2 = $$02.get("VillagerData").get("level").asInt(1);
                return $$02.set("Xp", $$02.createInt(VillagerRebuildLevelAndXpFix.getMinXpPerLevel($$2)));
            }
            return $$02;
        });
    }
}
