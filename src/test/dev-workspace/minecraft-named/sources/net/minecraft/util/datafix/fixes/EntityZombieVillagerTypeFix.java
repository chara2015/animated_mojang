package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityZombieVillagerTypeFix.class */
public class EntityZombieVillagerTypeFix extends NamedEntityFix {
    private static final int PROFESSION_MAX = 6;

    public EntityZombieVillagerTypeFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "EntityZombieVillagerTypeFix", References.ENTITY, "Zombie");
    }

    public Dynamic<?> fixTag(Dynamic<?> $$0) {
        if ($$0.get("IsVillager").asBoolean(false)) {
            if ($$0.get("ZombieType").result().isEmpty()) {
                int $$1 = getVillagerProfession($$0.get("VillagerProfession").asInt(-1));
                if ($$1 == -1) {
                    $$1 = getVillagerProfession(RandomSource.create().nextInt(6));
                }
                $$0 = $$0.set("ZombieType", $$0.createInt($$1));
            }
            $$0 = $$0.remove("IsVillager");
        }
        return $$0;
    }

    private int getVillagerProfession(int $$0) {
        if ($$0 < 0 || $$0 >= 6) {
            return -1;
        }
        return $$0;
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixTag);
    }
}
