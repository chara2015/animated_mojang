package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/TooltipDisplayComponentFix.class */
public class TooltipDisplayComponentFix extends DataFix {
    private static final List<String> CONVERTED_ADDITIONAL_TOOLTIP_TYPES = List.of((Object[]) new String[]{"minecraft:banner_patterns", "minecraft:bees", "minecraft:block_entity_data", "minecraft:block_state", "minecraft:bundle_contents", "minecraft:charged_projectiles", "minecraft:container", "minecraft:container_loot", "minecraft:firework_explosion", "minecraft:fireworks", "minecraft:instrument", "minecraft:map_id", "minecraft:painting/variant", "minecraft:pot_decorations", "minecraft:potion_contents", "minecraft:tropical_fish/pattern", "minecraft:written_book_content"});

    public TooltipDisplayComponentFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.DATA_COMPONENTS);
        Type<?> $$1 = getOutputSchema().getType(References.DATA_COMPONENTS);
        OpticFinder<?> $$2 = $$0.findField("minecraft:can_place_on");
        OpticFinder<?> $$3 = $$0.findField("minecraft:can_break");
        Type<?> $$4 = $$1.findFieldType("minecraft:can_place_on");
        Type<?> $$5 = $$1.findFieldType("minecraft:can_break");
        return fixTypeEverywhereTyped("TooltipDisplayComponentFix", $$0, $$1, $$42 -> {
            return fix($$42, $$2, $$3, $$4, $$5);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typed<?> fix(Typed<?> $$0, OpticFinder<?> $$1, OpticFinder<?> $$2, Type<?> $$3, Type<?> $$4) {
        Set<String> $$5 = new HashSet<>();
        return fixAdventureModePredicate(fixAdventureModePredicate($$0, $$1, $$3, "minecraft:can_place_on", $$5), $$2, $$4, "minecraft:can_break", $$5).update(DSL.remainderFinder(), $$12 -> {
            Dynamic<?> dynamicFixComponentAndUnwrap = fixComponentAndUnwrap(fixComponentAndUnwrap(fixComponentAndUnwrap(fixComponentAndUnwrap(fixComponentAndUnwrap(fixSimpleComponent(fixSimpleComponent($$12, "minecraft:trim", $$5), "minecraft:unbreakable", $$5), "minecraft:dyed_color", "rgb", $$5), "minecraft:attribute_modifiers", "modifiers", $$5), "minecraft:enchantments", "levels", $$5), "minecraft:stored_enchantments", "levels", $$5), "minecraft:jukebox_playable", "song", $$5);
            boolean $$22 = dynamicFixComponentAndUnwrap.get("minecraft:hide_tooltip").result().isPresent();
            Dynamic $$12 = dynamicFixComponentAndUnwrap.remove("minecraft:hide_tooltip");
            boolean $$32 = $$12.get("minecraft:hide_additional_tooltip").result().isPresent();
            Dynamic $$13 = $$12.remove("minecraft:hide_additional_tooltip");
            if ($$32) {
                for (String $$42 : CONVERTED_ADDITIONAL_TOOLTIP_TYPES) {
                    if ($$13.get($$42).result().isPresent()) {
                        $$5.add($$42);
                    }
                }
            }
            if ($$5.isEmpty() && !$$22) {
                return $$13;
            }
            Dynamic dynamicCreateString = $$13.createString("hide_tooltip");
            Dynamic dynamicCreateBoolean = $$13.createBoolean($$22);
            Dynamic dynamicCreateString2 = $$13.createString("hidden_components");
            Stream stream = $$5.stream();
            Objects.requireNonNull($$13);
            return $$13.set("minecraft:tooltip_display", $$13.createMap(Map.of(dynamicCreateString, dynamicCreateBoolean, dynamicCreateString2, $$13.createList(stream.map($$13::createString)))));
        });
    }

    private static Dynamic<?> fixSimpleComponent(Dynamic<?> $$0, String $$1, Set<String> $$2) {
        return fixRemainderComponent($$0, $$1, $$2, UnaryOperator.identity());
    }

    private static Dynamic<?> fixComponentAndUnwrap(Dynamic<?> $$0, String $$1, String $$2, Set<String> $$3) {
        return fixRemainderComponent($$0, $$1, $$3, $$12 -> {
            return (Dynamic) DataFixUtils.orElse($$12.get($$2).result(), $$12);
        });
    }

    private static Dynamic<?> fixRemainderComponent(Dynamic<?> $$0, String $$1, Set<String> $$2, UnaryOperator<Dynamic<?>> $$3) {
        return $$0.update($$1, $$32 -> {
            boolean $$4 = $$32.get("show_in_tooltip").asBoolean(true);
            if (!$$4) {
                $$2.add($$1);
            }
            return (Dynamic) $$3.apply($$32.remove("show_in_tooltip"));
        });
    }

    private static Typed<?> fixAdventureModePredicate(Typed<?> $$0, OpticFinder<?> $$1, Type<?> $$2, String $$3, Set<String> $$4) {
        return $$0.updateTyped($$1, $$2, $$32 -> {
            return Util.writeAndReadTypedOrThrow($$32, $$2, $$22 -> {
                OptionalDynamic<?> $$32 = $$22.get("predicates");
                if ($$32.result().isEmpty()) {
                    return $$22;
                }
                boolean $$42 = $$22.get("show_in_tooltip").asBoolean(true);
                if (!$$42) {
                    $$4.add($$3);
                }
                return (Dynamic) $$32.result().get();
            });
        });
    }
}
