package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EquipmentFormatFix.class */
public class EquipmentFormatFix extends DataFix {
    public EquipmentFormatFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getTypeRaw(References.ITEM_STACK);
        Type<?> $$1 = getOutputSchema().getTypeRaw(References.ITEM_STACK);
        OpticFinder<?> $$2 = $$0.findField(Entity.TAG_ID);
        return fix($$0, $$1, $$2);
    }

    private <ItemStackOld, ItemStackNew> TypeRewriteRule fix(Type<ItemStackOld> $$0, Type<ItemStackNew> $$1, OpticFinder<?> $$2) {
        Type<Pair<String, Pair<Either<List<ItemStackOld>, Unit>, Pair<Either<List<ItemStackOld>, Unit>, Pair<Either<ItemStackOld, Unit>, Either<ItemStackOld, Unit>>>>>> $$3 = DSL.named(References.ENTITY_EQUIPMENT.typeName(), DSL.and(DSL.optional(DSL.field("ArmorItems", DSL.list($$0))), DSL.optional(DSL.field("HandItems", DSL.list($$0))), DSL.optional(DSL.field("body_armor_item", $$0)), DSL.optional(DSL.field("saddle", $$0))));
        Type<Pair<String, Either<Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Pair<Either<ItemStackNew, Unit>, Dynamic<?>>>>>>>>>, Unit>>> $$4 = DSL.named(References.ENTITY_EQUIPMENT.typeName(), DSL.optional(DSL.field(LivingEntity.TAG_EQUIPMENT, DSL.and(DSL.optional(DSL.field("mainhand", $$1)), DSL.optional(DSL.field("offhand", $$1)), DSL.optional(DSL.field(PartNames.FEET, $$1)), DSL.and(DSL.optional(DSL.field("legs", $$1)), DSL.optional(DSL.field("chest", $$1)), DSL.optional(DSL.field(PartNames.HEAD, $$1)), DSL.and(DSL.optional(DSL.field(PartNames.BODY, $$1)), DSL.optional(DSL.field("saddle", $$1)), DSL.remainderType()))))));
        if (!$$3.equals(getInputSchema().getType(References.ENTITY_EQUIPMENT))) {
            throw new IllegalStateException("Input entity_equipment type does not match expected");
        }
        if (!$$4.equals(getOutputSchema().getType(References.ENTITY_EQUIPMENT))) {
            throw new IllegalStateException("Output entity_equipment type does not match expected");
        }
        return fixTypeEverywhere("EquipmentFormatFix", $$3, $$4, $$22 -> {
            Predicate predicate = $$32 -> {
                return new Typed($$0, $$22, $$32).getOptional($$2).isEmpty();
            };
            return $$22 -> {
                String $$33 = (String) $$22.getFirst();
                Pair pair = (Pair) $$22.getSecond();
                List list = (List) ((Either) pair.getFirst()).map(Function.identity(), $$02 -> {
                    return List.of();
                });
                List list2 = (List) ((Either) ((Pair) pair.getSecond()).getFirst()).map(Function.identity(), $$03 -> {
                    return List.of();
                });
                Either either = (Either) ((Pair) ((Pair) pair.getSecond()).getSecond()).getFirst();
                Either either2 = (Either) ((Pair) ((Pair) pair.getSecond()).getSecond()).getSecond();
                Either itemFromList = getItemFromList(0, list, predicate);
                Either itemFromList2 = getItemFromList(1, list, predicate);
                Either itemFromList3 = getItemFromList(2, list, predicate);
                Either itemFromList4 = getItemFromList(3, list, predicate);
                Either itemFromList5 = getItemFromList(0, list2, predicate);
                Either itemFromList6 = getItemFromList(1, list2, predicate);
                if (areAllEmpty(either, either2, itemFromList, itemFromList2, itemFromList3, itemFromList4, itemFromList5, itemFromList6)) {
                    return Pair.of($$33, Either.right(Unit.INSTANCE));
                }
                return Pair.of($$33, Either.left(Pair.of(itemFromList5, Pair.of(itemFromList6, Pair.of(itemFromList, Pair.of(itemFromList2, Pair.of(itemFromList3, Pair.of(itemFromList4, Pair.of(either, Pair.of(either2, new Dynamic($$22)))))))))));
            };
        });
    }

    @SafeVarargs
    private static boolean areAllEmpty(Either<?, Unit>... $$0) {
        for (Either<?, Unit> $$1 : $$0) {
            if ($$1.right().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private static <ItemStack> Either<ItemStack, Unit> getItemFromList(int $$0, List<ItemStack> $$1, Predicate<ItemStack> $$2) {
        if ($$0 >= $$1.size()) {
            return Either.right(Unit.INSTANCE);
        }
        ItemStack $$3 = $$1.get($$0);
        if ($$2.test($$3)) {
            return Either.right(Unit.INSTANCE);
        }
        return Either.left($$3);
    }
}
