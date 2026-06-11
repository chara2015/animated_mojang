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
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/MobEffectIdFix.class */
public class MobEffectIdFix extends DataFix {
    private static final Int2ObjectMap<String> ID_MAP = (Int2ObjectMap) Util.make(new Int2ObjectOpenHashMap(), $$0 -> {
        $$0.put(1, "minecraft:speed");
        $$0.put(2, "minecraft:slowness");
        $$0.put(3, "minecraft:haste");
        $$0.put(4, "minecraft:mining_fatigue");
        $$0.put(5, "minecraft:strength");
        $$0.put(6, "minecraft:instant_health");
        $$0.put(7, "minecraft:instant_damage");
        $$0.put(8, "minecraft:jump_boost");
        $$0.put(9, "minecraft:nausea");
        $$0.put(10, "minecraft:regeneration");
        $$0.put(11, "minecraft:resistance");
        $$0.put(12, "minecraft:fire_resistance");
        $$0.put(13, "minecraft:water_breathing");
        $$0.put(14, "minecraft:invisibility");
        $$0.put(15, "minecraft:blindness");
        $$0.put(16, "minecraft:night_vision");
        $$0.put(17, "minecraft:hunger");
        $$0.put(18, "minecraft:weakness");
        $$0.put(19, "minecraft:poison");
        $$0.put(20, "minecraft:wither");
        $$0.put(21, "minecraft:health_boost");
        $$0.put(22, "minecraft:absorption");
        $$0.put(23, "minecraft:saturation");
        $$0.put(24, "minecraft:glowing");
        $$0.put(25, "minecraft:levitation");
        $$0.put(26, "minecraft:luck");
        $$0.put(27, "minecraft:unluck");
        $$0.put(28, "minecraft:slow_falling");
        $$0.put(29, "minecraft:conduit_power");
        $$0.put(30, "minecraft:dolphins_grace");
        $$0.put(31, "minecraft:bad_omen");
        $$0.put(32, "minecraft:hero_of_the_village");
        $$0.put(33, "minecraft:darkness");
    });
    private static final Set<String> MOB_EFFECT_INSTANCE_CARRIER_ITEMS = Set.of("minecraft:potion", "minecraft:splash_potion", "minecraft:lingering_potion", "minecraft:tipped_arrow");

    public MobEffectIdFix(Schema $$0) {
        super($$0, false);
    }

    private static <T> Optional<Dynamic<T>> getAndConvertMobEffectId(Dynamic<T> $$0, String $$1) {
        Optional map = $$0.get($$1).asNumber().result().map($$02 -> {
            return (String) ID_MAP.get($$02.intValue());
        });
        Objects.requireNonNull($$0);
        return map.map($$0::createString);
    }

    private static <T> Dynamic<T> updateMobEffectIdField(Dynamic<T> $$0, String $$1, Dynamic<T> $$2, String $$3) {
        Optional<Dynamic<T>> $$4 = getAndConvertMobEffectId($$0, $$1);
        return $$2.replaceField($$1, $$3, $$4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Dynamic<T> updateMobEffectIdField(Dynamic<T> $$0, String $$1, String $$2) {
        return updateMobEffectIdField($$0, $$1, $$0, $$2);
    }

    private static <T> Dynamic<T> updateMobEffectInstance(Dynamic<T> $$0) {
        Dynamic<T> $$02 = updateMobEffectIdField($$0, "Id", Entity.TAG_ID).renameField("Ambient", "ambient").renameField("Amplifier", "amplifier").renameField("Duration", "duration").renameField("ShowParticles", "show_particles").renameField("ShowIcon", "show_icon");
        Optional<Dynamic<T>> $$1 = $$02.get("HiddenEffect").result().map(MobEffectIdFix::updateMobEffectInstance);
        return $$02.replaceField("HiddenEffect", "hidden_effect", $$1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> Dynamic<T> updateMobEffectInstanceList(Dynamic<T> $$0, String $$1, String $$2) {
        Optional<Dynamic<T>> $$3 = $$0.get($$1).asStreamOpt().result().map($$12 -> {
            return $$0.createList($$12.map(MobEffectIdFix::updateMobEffectInstance));
        });
        return $$0.replaceField($$1, $$2, $$3);
    }

    private static <T> Dynamic<T> updateSuspiciousStewEntry(Dynamic<T> $$0, Dynamic<T> $$1) {
        Dynamic<T> $$12 = updateMobEffectIdField($$0, "EffectId", $$1, Entity.TAG_ID);
        Optional<Dynamic<T>> $$2 = $$0.get("EffectDuration").result();
        return $$12.replaceField("EffectDuration", "duration", $$2);
    }

    private static <T> Dynamic<T> updateSuspiciousStewEntry(Dynamic<T> $$0) {
        return updateSuspiciousStewEntry($$0, $$0);
    }

    private Typed<?> updateNamedChoice(Typed<?> $$0, DSL.TypeReference $$1, String $$2, Function<Dynamic<?>, Dynamic<?>> $$3) {
        Type<?> $$4 = getInputSchema().getChoiceType($$1, $$2);
        Type<?> $$5 = getOutputSchema().getChoiceType($$1, $$2);
        return $$0.updateTyped(DSL.namedChoice($$2, $$4), $$5, $$12 -> {
            return $$12.update(DSL.remainderFinder(), $$3);
        });
    }

    private TypeRewriteRule blockEntityFixer() {
        Type<?> $$0 = getInputSchema().getType(References.BLOCK_ENTITY);
        return fixTypeEverywhereTyped("BlockEntityMobEffectIdFix", $$0, $$02 -> {
            return updateNamedChoice($$02, References.BLOCK_ENTITY, "minecraft:beacon", $$02 -> {
                return updateMobEffectIdField(updateMobEffectIdField($$02, "Primary", "primary_effect"), "Secondary", "secondary_effect");
            });
        });
    }

    private static <T> Dynamic<T> fixMooshroomTag(Dynamic<T> $$0) {
        Dynamic<T> $$1 = $$0.emptyMap();
        Dynamic<T> $$2 = updateSuspiciousStewEntry($$0, $$1);
        if (!$$2.equals($$1)) {
            $$0 = $$0.set("stew_effects", $$0.createList(Stream.of($$2)));
        }
        return $$0.remove("EffectId").remove("EffectDuration");
    }

    private static <T> Dynamic<T> fixArrowTag(Dynamic<T> $$0) {
        return updateMobEffectInstanceList($$0, "CustomPotionEffects", "custom_potion_effects");
    }

    private static <T> Dynamic<T> fixAreaEffectCloudTag(Dynamic<T> $$0) {
        return updateMobEffectInstanceList($$0, "Effects", "effects");
    }

    private static Dynamic<?> updateLivingEntityTag(Dynamic<?> $$0) {
        return updateMobEffectInstanceList($$0, "ActiveEffects", "active_effects");
    }

    private TypeRewriteRule entityFixer() {
        Type<?> $$0 = getInputSchema().getType(References.ENTITY);
        return fixTypeEverywhereTyped("EntityMobEffectIdFix", $$0, $$02 -> {
            return updateNamedChoice(updateNamedChoice(updateNamedChoice($$02, References.ENTITY, "minecraft:mooshroom", MobEffectIdFix::fixMooshroomTag), References.ENTITY, "minecraft:arrow", MobEffectIdFix::fixArrowTag), References.ENTITY, "minecraft:area_effect_cloud", MobEffectIdFix::fixAreaEffectCloudTag).update(DSL.remainderFinder(), MobEffectIdFix::updateLivingEntityTag);
        });
    }

    private TypeRewriteRule playerFixer() {
        Type<?> $$0 = getInputSchema().getType(References.PLAYER);
        return fixTypeEverywhereTyped("PlayerMobEffectIdFix", $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), MobEffectIdFix::updateLivingEntityTag);
        });
    }

    private static <T> Dynamic<T> fixSuspiciousStewTag(Dynamic<T> $$0) {
        Optional<Dynamic<T>> $$1 = $$0.get("Effects").asStreamOpt().result().map($$12 -> {
            return $$0.createList($$12.map(MobEffectIdFix::updateSuspiciousStewEntry));
        });
        return $$0.replaceField("Effects", "effects", $$1);
    }

    private TypeRewriteRule itemStackFixer() {
        OpticFinder<Pair<String, String>> $$0 = DSL.fieldFinder(Entity.TAG_ID, DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
        Type<?> $$1 = getInputSchema().getType(References.ITEM_STACK);
        OpticFinder<?> $$2 = $$1.findField("tag");
        return fixTypeEverywhereTyped("ItemStackMobEffectIdFix", $$1, $$22 -> {
            Optional<Pair<String, String>> $$3 = $$22.getOptional($$0);
            if ($$3.isPresent()) {
                String $$4 = (String) $$3.get().getSecond();
                if ($$4.equals("minecraft:suspicious_stew")) {
                    return $$22.updateTyped($$2, $$02 -> {
                        return $$02.update(DSL.remainderFinder(), MobEffectIdFix::fixSuspiciousStewTag);
                    });
                }
                if (MOB_EFFECT_INSTANCE_CARRIER_ITEMS.contains($$4)) {
                    return $$22.updateTyped($$2, $$03 -> {
                        return $$03.update(DSL.remainderFinder(), $$03 -> {
                            return updateMobEffectInstanceList($$03, "CustomPotionEffects", "custom_potion_effects");
                        });
                    });
                }
            }
            return $$22;
        });
    }

    protected TypeRewriteRule makeRule() {
        return TypeRewriteRule.seq(blockEntityFixer(), new TypeRewriteRule[]{entityFixer(), playerFixer(), itemStackFixer()});
    }
}
