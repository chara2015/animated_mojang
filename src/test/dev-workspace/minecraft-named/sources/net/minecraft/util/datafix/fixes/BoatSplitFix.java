package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BoatSplitFix.class */
public class BoatSplitFix extends DataFix {
    public BoatSplitFix(Schema $$0) {
        super($$0, true);
    }

    private static boolean isNormalBoat(String $$0) {
        return $$0.equals("minecraft:boat");
    }

    private static boolean isChestBoat(String $$0) {
        return $$0.equals("minecraft:chest_boat");
    }

    private static boolean isAnyBoat(String $$0) {
        return isNormalBoat($$0) || isChestBoat($$0);
    }

    private static String mapVariantToNormalBoat(String $$0) {
        switch ($$0) {
            case "spruce":
                return "minecraft:spruce_boat";
            case "birch":
                return "minecraft:birch_boat";
            case "jungle":
                return "minecraft:jungle_boat";
            case "acacia":
                return "minecraft:acacia_boat";
            case "cherry":
                return "minecraft:cherry_boat";
            case "dark_oak":
                return "minecraft:dark_oak_boat";
            case "mangrove":
                return "minecraft:mangrove_boat";
            case "bamboo":
                return "minecraft:bamboo_raft";
            default:
                return "minecraft:oak_boat";
        }
    }

    private static String mapVariantToChestBoat(String $$0) {
        switch ($$0) {
            case "spruce":
                return "minecraft:spruce_chest_boat";
            case "birch":
                return "minecraft:birch_chest_boat";
            case "jungle":
                return "minecraft:jungle_chest_boat";
            case "acacia":
                return "minecraft:acacia_chest_boat";
            case "cherry":
                return "minecraft:cherry_chest_boat";
            case "dark_oak":
                return "minecraft:dark_oak_chest_boat";
            case "mangrove":
                return "minecraft:mangrove_chest_boat";
            case "bamboo":
                return "minecraft:bamboo_chest_raft";
            default:
                return "minecraft:oak_chest_boat";
        }
    }

    public TypeRewriteRule makeRule() {
        OpticFinder<String> $$0 = DSL.fieldFinder(Entity.TAG_ID, NamespacedSchema.namespacedString());
        Type<?> $$1 = getInputSchema().getType(References.ENTITY);
        Type<?> $$2 = getOutputSchema().getType(References.ENTITY);
        return fixTypeEverywhereTyped("BoatSplitFix", $$1, $$2, $$22 -> {
            String $$7;
            Optional<String> $$3 = $$22.getOptional($$0);
            if ($$3.isPresent() && isAnyBoat($$3.get())) {
                Dynamic<?> $$4 = (Dynamic) $$22.getOrCreate(DSL.remainderFinder());
                Optional<String> $$5 = $$4.get("Type").asString().result();
                if (isChestBoat($$3.get())) {
                    $$7 = (String) $$5.map(BoatSplitFix::mapVariantToChestBoat).orElse("minecraft:oak_chest_boat");
                } else {
                    $$7 = (String) $$5.map(BoatSplitFix::mapVariantToNormalBoat).orElse("minecraft:oak_boat");
                }
                return ExtraDataFixUtils.cast($$2, $$22).update(DSL.remainderFinder(), $$02 -> {
                    return $$02.remove("Type");
                }).set($$0, $$7);
            }
            return ExtraDataFixUtils.cast($$2, $$22);
        });
    }
}
