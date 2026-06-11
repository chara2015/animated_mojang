package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityEquipmentToArmorAndHandFix.class */
public class EntityEquipmentToArmorAndHandFix extends DataFix {
    public EntityEquipmentToArmorAndHandFix(Schema $$0) {
        super($$0, true);
    }

    public TypeRewriteRule makeRule() {
        return cap(getInputSchema().getTypeRaw(References.ITEM_STACK), getOutputSchema().getTypeRaw(References.ITEM_STACK));
    }

    private <ItemStackOld, ItemStackNew> TypeRewriteRule cap(Type<ItemStackOld> $$0, Type<ItemStackNew> $$1) {
        Type<Pair<String, Either<List<ItemStackOld>, Unit>>> $$2 = DSL.named(References.ENTITY_EQUIPMENT.typeName(), DSL.optional(DSL.field("Equipment", DSL.list($$0))));
        Type<Pair<String, Pair<Either<List<ItemStackNew>, Unit>, Pair<Either<List<ItemStackNew>, Unit>, Pair<Either<ItemStackNew, Unit>, Either<ItemStackNew, Unit>>>>>> $$3 = DSL.named(References.ENTITY_EQUIPMENT.typeName(), DSL.and(DSL.optional(DSL.field("ArmorItems", DSL.list($$1))), DSL.optional(DSL.field("HandItems", DSL.list($$1))), DSL.optional(DSL.field("body_armor_item", $$1)), DSL.optional(DSL.field("saddle", $$1))));
        if (!$$2.equals(getInputSchema().getType(References.ENTITY_EQUIPMENT))) {
            throw new IllegalStateException("Input entity_equipment type does not match expected");
        }
        if (!$$3.equals(getOutputSchema().getType(References.ENTITY_EQUIPMENT))) {
            throw new IllegalStateException("Output entity_equipment type does not match expected");
        }
        return TypeRewriteRule.seq(fixTypeEverywhereTyped("EntityEquipmentToArmorAndHandFix - drop chances", getInputSchema().getType(References.ENTITY), $$02 -> {
            return $$02.update(DSL.remainderFinder(), EntityEquipmentToArmorAndHandFix::fixDropChances);
        }), fixTypeEverywhere("EntityEquipmentToArmorAndHandFix - equipment", $$2, $$3, $$12 -> {
            Object first = ((Pair) $$1.read(new Dynamic($$12).emptyMap()).result().orElseThrow(() -> {
                return new IllegalStateException("Could not parse newly created empty itemstack.");
            })).getFirst();
            Either eitherRight = Either.right(DSL.unit());
            return $$22 -> {
                return $$22.mapSecond($$22 -> {
                    List list = (List) $$22.map(Function.identity(), $$03 -> {
                        return List.of();
                    });
                    Either eitherRight2 = Either.right(DSL.unit());
                    Either eitherRight3 = Either.right(DSL.unit());
                    if (!list.isEmpty()) {
                        eitherRight2 = Either.left(Lists.newArrayList(new Object[]{list.getFirst(), first}));
                    }
                    if (list.size() > 1) {
                        ArrayList arrayListNewArrayList = Lists.newArrayList(new Object[]{first, first, first, first});
                        for (int $$7 = 1; $$7 < Math.min(list.size(), 5); $$7++) {
                            arrayListNewArrayList.set($$7 - 1, list.get($$7));
                        }
                        eitherRight3 = Either.left(arrayListNewArrayList);
                    }
                    return Pair.of(eitherRight3, Pair.of(eitherRight2, Pair.of(eitherRight, eitherRight)));
                });
            };
        }));
    }

    private static Dynamic<?> fixDropChances(Dynamic<?> $$0) {
        Optional<? extends Stream<? extends Dynamic<?>>> $$1 = $$0.get("DropChances").asStreamOpt().result();
        Dynamic<?> $$02 = $$0.remove("DropChances");
        if ($$1.isPresent()) {
            Iterator<Float> $$2 = Stream.concat(((Stream) $$1.get()).map($$03 -> {
                return Float.valueOf($$03.asFloat(0.0f));
            }), Stream.generate(() -> {
                return Float.valueOf(0.0f);
            })).iterator();
            float $$3 = $$2.next().floatValue();
            if ($$02.get("HandDropChances").result().isEmpty()) {
                Stream streamOf = Stream.of((Object[]) new Float[]{Float.valueOf($$3), Float.valueOf(0.0f)});
                Objects.requireNonNull($$02);
                $$02 = $$02.set("HandDropChances", $$02.createList(streamOf.map((v1) -> {
                    return r4.createFloat(v1);
                })));
            }
            if ($$02.get("ArmorDropChances").result().isEmpty()) {
                Stream streamOf2 = Stream.of((Object[]) new Float[]{$$2.next(), $$2.next(), $$2.next(), $$2.next()});
                Dynamic<?> dynamic = $$02;
                Objects.requireNonNull(dynamic);
                $$02 = $$02.set("ArmorDropChances", $$02.createList(streamOf2.map((v1) -> {
                    return r4.createFloat(v1);
                })));
            }
        }
        return $$02;
    }
}
