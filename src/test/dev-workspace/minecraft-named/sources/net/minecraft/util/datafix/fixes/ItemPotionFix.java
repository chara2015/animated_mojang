package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemPotionFix.class */
public class ItemPotionFix extends DataFix {
    private static final int SPLASH = 16384;
    private static final String[] POTIONS = (String[]) DataFixUtils.make(new String[128], $$0 -> {
        $$0[0] = DEFAULT;
        $$0[1] = "minecraft:regeneration";
        $$0[2] = "minecraft:swiftness";
        $$0[3] = "minecraft:fire_resistance";
        $$0[4] = "minecraft:poison";
        $$0[5] = "minecraft:healing";
        $$0[6] = "minecraft:night_vision";
        $$0[7] = null;
        $$0[8] = "minecraft:weakness";
        $$0[9] = "minecraft:strength";
        $$0[10] = "minecraft:slowness";
        $$0[11] = "minecraft:leaping";
        $$0[12] = "minecraft:harming";
        $$0[13] = "minecraft:water_breathing";
        $$0[14] = "minecraft:invisibility";
        $$0[15] = null;
        $$0[16] = "minecraft:awkward";
        $$0[17] = "minecraft:regeneration";
        $$0[18] = "minecraft:swiftness";
        $$0[19] = "minecraft:fire_resistance";
        $$0[20] = "minecraft:poison";
        $$0[21] = "minecraft:healing";
        $$0[22] = "minecraft:night_vision";
        $$0[23] = null;
        $$0[24] = "minecraft:weakness";
        $$0[25] = "minecraft:strength";
        $$0[26] = "minecraft:slowness";
        $$0[27] = "minecraft:leaping";
        $$0[28] = "minecraft:harming";
        $$0[29] = "minecraft:water_breathing";
        $$0[30] = "minecraft:invisibility";
        $$0[31] = null;
        $$0[32] = "minecraft:thick";
        $$0[33] = "minecraft:strong_regeneration";
        $$0[34] = "minecraft:strong_swiftness";
        $$0[35] = "minecraft:fire_resistance";
        $$0[36] = "minecraft:strong_poison";
        $$0[37] = "minecraft:strong_healing";
        $$0[38] = "minecraft:night_vision";
        $$0[39] = null;
        $$0[40] = "minecraft:weakness";
        $$0[41] = "minecraft:strong_strength";
        $$0[42] = "minecraft:slowness";
        $$0[43] = "minecraft:strong_leaping";
        $$0[44] = "minecraft:strong_harming";
        $$0[45] = "minecraft:water_breathing";
        $$0[46] = "minecraft:invisibility";
        $$0[47] = null;
        $$0[48] = null;
        $$0[49] = "minecraft:strong_regeneration";
        $$0[50] = "minecraft:strong_swiftness";
        $$0[51] = "minecraft:fire_resistance";
        $$0[52] = "minecraft:strong_poison";
        $$0[53] = "minecraft:strong_healing";
        $$0[54] = "minecraft:night_vision";
        $$0[55] = null;
        $$0[56] = "minecraft:weakness";
        $$0[57] = "minecraft:strong_strength";
        $$0[58] = "minecraft:slowness";
        $$0[59] = "minecraft:strong_leaping";
        $$0[60] = "minecraft:strong_harming";
        $$0[61] = "minecraft:water_breathing";
        $$0[62] = "minecraft:invisibility";
        $$0[63] = null;
        $$0[64] = "minecraft:mundane";
        $$0[65] = "minecraft:long_regeneration";
        $$0[66] = "minecraft:long_swiftness";
        $$0[67] = "minecraft:long_fire_resistance";
        $$0[68] = "minecraft:long_poison";
        $$0[69] = "minecraft:healing";
        $$0[70] = "minecraft:long_night_vision";
        $$0[71] = null;
        $$0[72] = "minecraft:long_weakness";
        $$0[73] = "minecraft:long_strength";
        $$0[74] = "minecraft:long_slowness";
        $$0[75] = "minecraft:long_leaping";
        $$0[76] = "minecraft:harming";
        $$0[77] = "minecraft:long_water_breathing";
        $$0[78] = "minecraft:long_invisibility";
        $$0[79] = null;
        $$0[80] = "minecraft:awkward";
        $$0[81] = "minecraft:long_regeneration";
        $$0[82] = "minecraft:long_swiftness";
        $$0[83] = "minecraft:long_fire_resistance";
        $$0[84] = "minecraft:long_poison";
        $$0[85] = "minecraft:healing";
        $$0[86] = "minecraft:long_night_vision";
        $$0[87] = null;
        $$0[88] = "minecraft:long_weakness";
        $$0[89] = "minecraft:long_strength";
        $$0[90] = "minecraft:long_slowness";
        $$0[91] = "minecraft:long_leaping";
        $$0[92] = "minecraft:harming";
        $$0[93] = "minecraft:long_water_breathing";
        $$0[94] = "minecraft:long_invisibility";
        $$0[95] = null;
        $$0[96] = "minecraft:thick";
        $$0[97] = "minecraft:regeneration";
        $$0[98] = "minecraft:swiftness";
        $$0[99] = "minecraft:long_fire_resistance";
        $$0[100] = "minecraft:poison";
        $$0[101] = "minecraft:strong_healing";
        $$0[102] = "minecraft:long_night_vision";
        $$0[103] = null;
        $$0[104] = "minecraft:long_weakness";
        $$0[105] = "minecraft:strength";
        $$0[106] = "minecraft:long_slowness";
        $$0[107] = "minecraft:leaping";
        $$0[108] = "minecraft:strong_harming";
        $$0[109] = "minecraft:long_water_breathing";
        $$0[110] = "minecraft:long_invisibility";
        $$0[111] = null;
        $$0[112] = null;
        $$0[113] = "minecraft:regeneration";
        $$0[114] = "minecraft:swiftness";
        $$0[115] = "minecraft:long_fire_resistance";
        $$0[116] = "minecraft:poison";
        $$0[117] = "minecraft:strong_healing";
        $$0[118] = "minecraft:long_night_vision";
        $$0[119] = null;
        $$0[120] = "minecraft:long_weakness";
        $$0[121] = "minecraft:strength";
        $$0[122] = "minecraft:long_slowness";
        $$0[123] = "minecraft:leaping";
        $$0[124] = "minecraft:strong_harming";
        $$0[125] = "minecraft:long_water_breathing";
        $$0[126] = "minecraft:long_invisibility";
        $$0[127] = null;
    });
    public static final String DEFAULT = "minecraft:water";

    public ItemPotionFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        OpticFinder<?> $$2 = $$0.findField("tag");
        return fixTypeEverywhereTyped("ItemPotionFix", $$0, $$22 -> {
            Optional<Pair<String, String>> $$3 = $$22.getOptional($$1);
            if ($$3.isPresent() && Objects.equals($$3.get().getSecond(), "minecraft:potion")) {
                Dynamic<?> $$4 = (Dynamic) $$22.get(DSL.remainderFinder());
                Optional<? extends Typed<?>> $$5 = $$22.getOptionalTyped($$2);
                short $$6 = $$4.get("Damage").asShort((short) 0);
                if ($$5.isPresent()) {
                    Typed typed = $$22;
                    Dynamic<?> $$8 = (Dynamic) ((Typed) $$5.get()).get(DSL.remainderFinder());
                    Optional<String> $$9 = $$8.get("Potion").asString().result();
                    if ($$9.isEmpty()) {
                        String $$10 = POTIONS[$$6 & 127];
                        Typed<?> $$11 = ((Typed) $$5.get()).set(DSL.remainderFinder(), $$8.set("Potion", $$8.createString($$10 == null ? DEFAULT : $$10)));
                        typed = typed.set($$2, $$11);
                        if (($$6 & 16384) == 16384) {
                            typed = typed.set($$1, Pair.of(References.ITEM_NAME.typeName(), "minecraft:splash_potion"));
                        }
                    }
                    if ($$6 != 0) {
                        $$4 = $$4.set("Damage", $$4.createShort((short) 0));
                    }
                    return typed.set(DSL.remainderFinder(), $$4);
                }
            }
            return $$22;
        });
    }
}
