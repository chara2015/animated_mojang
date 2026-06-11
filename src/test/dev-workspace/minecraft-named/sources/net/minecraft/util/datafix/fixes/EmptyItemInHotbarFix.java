package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EmptyItemInHotbarFix.class */
public class EmptyItemInHotbarFix extends DataFix {
    public EmptyItemInHotbarFix(Schema $$0) {
        super($$0, false);
    }

    public TypeRewriteRule makeRule() {
        OpticFinder<Pair<String, Pair<Either<Pair<String, String>, Unit>, Pair<Either<?, Unit>, Dynamic<?>>>>> $$0 = DSL.typeFinder(getInputSchema().getType(References.ITEM_STACK));
        return fixTypeEverywhereTyped("EmptyItemInHotbarFix", getInputSchema().getType(References.HOTBAR), $$1 -> {
            return $$1.update($$0, $$02 -> {
                return $$02.mapSecond($$02 -> {
                    Optional<String> $$1 = ((Either) $$02.getFirst()).left().map((v0) -> {
                        return v0.getSecond();
                    });
                    Dynamic<?> $$2 = (Dynamic) ((Pair) $$02.getSecond()).getSecond();
                    boolean $$3 = $$1.isEmpty() || $$1.get().equals(JigsawBlockEntity.DEFAULT_FINAL_STATE);
                    boolean $$4 = $$2.get("Count").asInt(0) <= 0;
                    if ($$3 || $$4) {
                        return Pair.of(Either.right(Unit.INSTANCE), Pair.of(Either.right(Unit.INSTANCE), $$2.emptyMap()));
                    }
                    return $$02;
                });
            });
        });
    }
}
