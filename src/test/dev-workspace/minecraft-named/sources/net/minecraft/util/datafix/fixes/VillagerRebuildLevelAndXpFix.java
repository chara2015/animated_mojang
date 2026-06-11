package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/VillagerRebuildLevelAndXpFix.class */
public class VillagerRebuildLevelAndXpFix extends DataFix {
    private static final int TRADES_PER_LEVEL = 2;
    private static final int[] LEVEL_XP_THRESHOLDS = {0, 10, 50, 100, 150};

    public static int getMinXpPerLevel(int $$0) {
        return LEVEL_XP_THRESHOLDS[Mth.clamp($$0 - 1, 0, LEVEL_XP_THRESHOLDS.length - 1)];
    }

    public VillagerRebuildLevelAndXpFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getChoiceType(References.ENTITY, "minecraft:villager");
        OpticFinder<?> $$1 = DSL.namedChoice("minecraft:villager", $$0);
        OpticFinder<?> $$2 = $$0.findField("Offers");
        Type<?> $$3 = $$2.type();
        OpticFinder<?> $$4 = $$3.findField("Recipes");
        List.ListType<?> $$5 = $$4.type();
        OpticFinder<?> $$6 = $$5.getElement().finder();
        return fixTypeEverywhereTyped("Villager level and xp rebuild", getInputSchema().getType(References.ENTITY), $$52 -> {
            return $$52.updateTyped($$1, $$0, $$32 -> {
                Dynamic<?> $$42 = (Dynamic) $$32.get(DSL.remainderFinder());
                int $$52 = $$42.get("VillagerData").get("level").asInt(0);
                Typed typedAddLevel = $$32;
                if ($$52 == 0 || $$52 == 1) {
                    int $$7 = ((Integer) $$32.getOptionalTyped($$2).flatMap($$12 -> {
                        return $$12.getOptionalTyped($$4);
                    }).map($$13 -> {
                        return Integer.valueOf($$13.getAllTyped($$6).size());
                    }).orElse(0)).intValue();
                    $$52 = Mth.clamp($$7 / 2, 1, 5);
                    if ($$52 > 1) {
                        typedAddLevel = addLevel(typedAddLevel, $$52);
                    }
                }
                Optional<Number> $$8 = $$42.get("Xp").asNumber().result();
                if ($$8.isEmpty()) {
                    typedAddLevel = addXpFromLevel(typedAddLevel, $$52);
                }
                return typedAddLevel;
            });
        });
    }

    private static Typed<?> addLevel(Typed<?> $$0, int $$1) {
        return $$0.update(DSL.remainderFinder(), $$12 -> {
            return $$12.update("VillagerData", $$12 -> {
                return $$12.set("level", $$12.createInt($$1));
            });
        });
    }

    private static Typed<?> addXpFromLevel(Typed<?> $$0, int $$1) {
        int $$2 = getMinXpPerLevel($$1);
        return $$0.update(DSL.remainderFinder(), $$12 -> {
            return $$12.set("Xp", $$12.createInt($$2));
        });
    }
}
