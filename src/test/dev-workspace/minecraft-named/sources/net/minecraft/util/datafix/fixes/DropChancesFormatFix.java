package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.List;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.Mob;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/DropChancesFormatFix.class */
public class DropChancesFormatFix extends DataFix {
    private static final List<String> ARMOR_SLOT_NAMES = List.of(PartNames.FEET, "legs", "chest", PartNames.HEAD);
    private static final List<String> HAND_SLOT_NAMES = List.of("mainhand", "offhand");
    private static final float DEFAULT_CHANCE = 0.085f;

    public DropChancesFormatFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("DropChancesFormatFix", getInputSchema().getType(References.ENTITY), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                List<Float> $$1 = parseDropChances($$0.get("ArmorDropChances"));
                List<Float> $$2 = parseDropChances($$0.get("HandDropChances"));
                float $$3 = ((Float) $$0.get("body_armor_drop_chance").asNumber().result().map((v0) -> {
                    return v0.floatValue();
                }).orElse(Float.valueOf(0.085f))).floatValue();
                Dynamic $$0 = $$0.remove("ArmorDropChances").remove("HandDropChances").remove("body_armor_drop_chance");
                Dynamic<?> $$4 = addSlotChances(addSlotChances($$0.emptyMap(), $$1, ARMOR_SLOT_NAMES), $$2, HAND_SLOT_NAMES);
                if ($$3 != 0.085f) {
                    $$4 = $$4.set(PartNames.BODY, $$0.createFloat($$3));
                }
                if (!$$4.equals($$0.emptyMap())) {
                    return $$0.set(Mob.TAG_DROP_CHANCES, $$4);
                }
                return $$0;
            });
        });
    }

    private static Dynamic<?> addSlotChances(Dynamic<?> $$0, List<Float> $$1, List<String> $$2) {
        for (int $$3 = 0; $$3 < $$2.size() && $$3 < $$1.size(); $$3++) {
            String $$4 = $$2.get($$3);
            float $$5 = $$1.get($$3).floatValue();
            if ($$5 != 0.085f) {
                $$0 = $$0.set($$4, $$0.createFloat($$5));
            }
        }
        return $$0;
    }

    private static List<Float> parseDropChances(OptionalDynamic<?> $$0) {
        return $$0.asStream().map($$02 -> {
            return Float.valueOf($$02.asFloat(0.085f));
        }).toList();
    }
}
