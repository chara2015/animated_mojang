package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityRidingToPassengersFix.class */
public class EntityRidingToPassengersFix extends DataFix {
    public EntityRidingToPassengersFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Schema $$0 = getInputSchema();
        Schema $$1 = getOutputSchema();
        Type<?> $$2 = $$0.getTypeRaw(References.ENTITY_TREE);
        Type<?> $$3 = $$1.getTypeRaw(References.ENTITY_TREE);
        Type<?> $$4 = $$0.getTypeRaw(References.ENTITY);
        return cap($$0, $$1, $$2, $$3, $$4);
    }

    private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule cap(Schema $$0, Schema $$1, Type<OldEntityTree> $$2, Type<NewEntityTree> $$3, Type<Entity> $$4) {
        Type<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> $$5 = DSL.named(References.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field("Riding", $$2)), $$4));
        Type<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> $$6 = DSL.named(References.ENTITY_TREE.typeName(), DSL.and(DSL.optional(DSL.field(Entity.TAG_PASSENGERS, DSL.list($$3))), $$4));
        Type<?> $$7 = $$0.getType(References.ENTITY_TREE);
        Type<?> $$8 = $$1.getType(References.ENTITY_TREE);
        if (!Objects.equals($$7, $$5)) {
            throw new IllegalStateException("Old entity type is not what was expected.");
        }
        if (!$$8.equals($$6, true, true)) {
            throw new IllegalStateException("New entity type is not what was expected.");
        }
        OpticFinder<Pair<String, Pair<Either<OldEntityTree, Unit>, Entity>>> $$9 = DSL.typeFinder($$5);
        OpticFinder<Pair<String, Pair<Either<List<NewEntityTree>, Unit>, Entity>>> $$10 = DSL.typeFinder($$6);
        OpticFinder<NewEntityTree> $$11 = DSL.typeFinder($$3);
        Type<?> $$12 = $$0.getType(References.PLAYER);
        Type<?> $$13 = $$1.getType(References.PLAYER);
        return TypeRewriteRule.seq(fixTypeEverywhere("EntityRidingToPassengerFix", $$5, $$6, $$52 -> {
            return $$62 -> {
                Optional optionalEmpty = Optional.empty();
                Pair pair = $$62;
                while (true) {
                    Pair pair2 = pair;
                    optionalEmpty = Optional.of(Pair.of(References.ENTITY_TREE.typeName(), Pair.of((Either) DataFixUtils.orElse(optionalEmpty.map($$42 -> {
                        return Either.left(ImmutableList.of(((Typed) $$3.pointTyped($$52).orElseThrow(() -> {
                            return new IllegalStateException("Could not create new entity tree");
                        })).set($$10, $$42).getOptional($$11).orElseThrow(() -> {
                            return new IllegalStateException("Should always have an entity tree here");
                        })));
                    }), Either.right(DSL.unit())), ((Pair) pair2.getSecond()).getSecond())));
                    Optional optionalLeft = ((Either) ((Pair) pair2.getSecond()).getFirst()).left();
                    if (!optionalLeft.isEmpty()) {
                        pair = (Pair) new Typed($$2, $$52, optionalLeft.get()).getOptional($$9).orElseThrow(() -> {
                            return new IllegalStateException("Should always have an entity here");
                        });
                    } else {
                        return (Pair) optionalEmpty.orElseThrow(() -> {
                            return new IllegalStateException("Should always have an entity tree here");
                        });
                    }
                }
            };
        }), writeAndRead("player RootVehicle injecter", $$12, $$13));
    }
}
