package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EffectDurationFix.class */
public class EffectDurationFix extends DataFix {
    private static final Set<String> POTION_ITEMS = Set.of("minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:tipped_arrow");

    public EffectDurationFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Schema $$0 = getInputSchema();
        Type<?> $$1 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<Pair<String, String>> $$2 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        OpticFinder<?> $$3 = $$1.findField("tag");
        return TypeRewriteRule.seq(fixTypeEverywhereTyped("EffectDurationEntity", $$0.getType(References.ENTITY), $$02 -> {
            return $$02.update(DSL.remainderFinder(), this::updateEntity);
        }), new TypeRewriteRule[]{fixTypeEverywhereTyped("EffectDurationPlayer", $$0.getType(References.PLAYER), $$03 -> {
            return $$03.update(DSL.remainderFinder(), this::updateEntity);
        }), fixTypeEverywhereTyped("EffectDurationItem", $$1, $$22 -> {
            if ($$22.getOptional($$2).filter($$04 -> {
                return POTION_ITEMS.contains($$04.getSecond());
            }).isPresent()) {
                Optional<? extends Typed<?>> $$32 = $$22.getOptionalTyped($$3);
                if ($$32.isPresent()) {
                    Dynamic<?> $$4 = (Dynamic) ((Typed) $$32.get()).get(DSL.remainderFinder());
                    Typed<?> $$5 = ((Typed) $$32.get()).set(DSL.remainderFinder(), $$4.update("CustomPotionEffects", this::fix));
                    return $$22.set($$3, $$5);
                }
            }
            return $$22;
        })});
    }

    private Dynamic<?> fixEffect(Dynamic<?> $$0) {
        return $$0.update("FactorCalculationData", $$1 -> {
            int $$2 = $$1.get("effect_changed_timestamp").asInt(-1);
            Dynamic $$1 = $$1.remove("effect_changed_timestamp");
            int $$3 = $$0.get("Duration").asInt(-1);
            int $$4 = $$2 - $$3;
            return $$1.set("ticks_active", $$1.createInt($$4));
        });
    }

    private Dynamic<?> fix(Dynamic<?> $$0) {
        return $$0.createList($$0.asStream().map(this::fixEffect));
    }

    private Dynamic<?> updateEntity(Dynamic<?> $$0) {
        return $$0.update("Effects", this::fix).update("ActiveEffects", this::fix).update("CustomPotionEffects", this::fix);
    }
}
