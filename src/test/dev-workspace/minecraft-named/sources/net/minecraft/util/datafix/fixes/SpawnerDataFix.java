package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import net.minecraft.world.level.BaseSpawner;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/SpawnerDataFix.class */
public class SpawnerDataFix extends DataFix {
    public SpawnerDataFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.UNTAGGED_SPAWNER);
        Type<?> $$1 = getOutputSchema().getType(References.UNTAGGED_SPAWNER);
        OpticFinder<?> $$2 = $$0.findField(BaseSpawner.SPAWN_DATA_TAG);
        Type<?> $$3 = $$1.findField(BaseSpawner.SPAWN_DATA_TAG).type();
        OpticFinder<?> $$4 = $$0.findField("SpawnPotentials");
        Type<?> $$5 = $$1.findField("SpawnPotentials").type();
        return fixTypeEverywhereTyped("Fix mob spawner data structure", $$0, $$1, $$42 -> {
            return $$42.updateTyped($$2, $$3, $$12 -> {
                return wrapEntityToSpawnData($$3, $$12);
            }).updateTyped($$4, $$5, $$13 -> {
                return wrapSpawnPotentialsToWeightedEntries($$5, $$13);
            });
        });
    }

    private <T> Typed<T> wrapEntityToSpawnData(Type<T> $$0, Typed<?> $$1) {
        DynamicOps<?> $$2 = $$1.getOps();
        return new Typed<>($$0, $$2, Pair.of($$1.getValue(), new Dynamic($$2)));
    }

    private <T> Typed<T> wrapSpawnPotentialsToWeightedEntries(Type<T> $$0, Typed<?> $$1) {
        DynamicOps<?> $$2 = $$1.getOps();
        List<?> $$3 = (List) $$1.getValue();
        return new Typed<>($$0, $$2, $$3.stream().map($$12 -> {
            Pair<Object, Dynamic<?>> $$22 = (Pair) $$12;
            int $$32 = ((Number) ((Dynamic) $$22.getSecond()).get("Weight").asNumber().result().orElse(1)).intValue();
            Dynamic<?> $$4 = new Dynamic<>($$2);
            Dynamic<?> $$42 = $$4.set("weight", $$4.createInt($$32));
            Dynamic<?> $$5 = ((Dynamic) $$22.getSecond()).remove("Weight").remove("Entity");
            return Pair.of(Pair.of($$22.getFirst(), $$5), $$42);
        }).toList());
    }
}
