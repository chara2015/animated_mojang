package net.minecraft.util.datafix.fixes;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.function.Supplier;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityZombieSplitFix.class */
public class EntityZombieSplitFix extends EntityRenameFix {
    private final Supplier<Type<?>> zombieVillagerType;

    public EntityZombieSplitFix(Schema $$0) {
        super("EntityZombieSplitFix", $$0, true);
        this.zombieVillagerType = Suppliers.memoize(() -> {
            return getOutputSchema().getChoiceType(References.ENTITY, "ZombieVillager");
        });
    }

    @Override // net.minecraft.util.datafix.fixes.EntityRenameFix
    protected Pair<String, Typed<?>> fix(String $$0, Typed<?> $$1) {
        String $$8;
        Typed<?> $$9;
        if (!$$0.equals("Zombie")) {
            return Pair.of($$0, $$1);
        }
        Dynamic<?> $$2 = (Dynamic) $$1.getOptional(DSL.remainderFinder()).orElseThrow();
        int $$3 = $$2.get("ZombieType").asInt(0);
        switch ($$3) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                $$8 = "ZombieVillager";
                $$9 = changeSchemaToZombieVillager($$1, $$3 - 1);
                break;
            case 6:
                $$8 = "Husk";
                $$9 = $$1;
                break;
            default:
                $$8 = "Zombie";
                $$9 = $$1;
                break;
        }
        return Pair.of($$8, $$9.update(DSL.remainderFinder(), $$02 -> {
            return $$02.remove("ZombieType");
        }));
    }

    private Typed<?> changeSchemaToZombieVillager(Typed<?> $$0, int $$1) {
        return Util.writeAndReadTypedOrThrow($$0, this.zombieVillagerType.get(), $$12 -> {
            return $$12.set("Profession", $$12.createInt($$1));
        });
    }
}
