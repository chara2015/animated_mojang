package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.OptionalDynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.LegacyComponentDataFixUtils;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemStackCustomNameToOverrideComponentFix.class */
public class ItemStackCustomNameToOverrideComponentFix extends DataFix {
    private static final Set<String> MAP_NAMES = Set.of((Object[]) new String[]{"filled_map.buried_treasure", "filled_map.explorer_jungle", "filled_map.explorer_swamp", "filled_map.mansion", "filled_map.monument", "filled_map.trial_chambers", "filled_map.village_desert", "filled_map.village_plains", "filled_map.village_savanna", "filled_map.village_snowy", "filled_map.village_taiga"});

    public ItemStackCustomNameToOverrideComponentFix(Schema $$0) {
        super($$0, false);
    }

    public final TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<Pair<String, String>> $$1 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        OpticFinder<?> $$2 = $$0.findField("components");
        return fixTypeEverywhereTyped("ItemStack custom_name to item_name component fix", $$0, $$22 -> {
            Optional<Pair<String, String>> $$3 = $$22.getOptional($$1);
            Optional map = $$3.map((v0) -> {
                return v0.getSecond();
            });
            if (map.filter($$02 -> {
                return $$02.equals("minecraft:white_banner");
            }).isPresent()) {
                return $$22.updateTyped($$2, ItemStackCustomNameToOverrideComponentFix::fixBanner);
            }
            if (map.filter($$03 -> {
                return $$03.equals("minecraft:filled_map");
            }).isPresent()) {
                return $$22.updateTyped($$2, ItemStackCustomNameToOverrideComponentFix::fixMap);
            }
            return $$22;
        });
    }

    private static <T> Typed<T> fixMap(Typed<T> $$0) {
        Set<String> set = MAP_NAMES;
        Objects.requireNonNull(set);
        return fixCustomName($$0, (v1) -> {
            return r1.contains(v1);
        });
    }

    private static <T> Typed<T> fixBanner(Typed<T> $$0) {
        return fixCustomName($$0, $$02 -> {
            return $$02.equals("block.minecraft.ominous_banner");
        });
    }

    private static <T> Typed<T> fixCustomName(Typed<T> $$0, Predicate<String> $$1) {
        return Util.writeAndReadTypedOrThrow($$0, $$0.getType(), $$12 -> {
            OptionalDynamic<?> $$2 = $$12.get("minecraft:custom_name");
            Optional<String> $$3 = $$2.asString().result().flatMap(LegacyComponentDataFixUtils::extractTranslationString).filter($$1);
            if ($$3.isPresent()) {
                return $$12.renameField("minecraft:custom_name", "minecraft:item_name");
            }
            return $$12;
        });
    }
}
