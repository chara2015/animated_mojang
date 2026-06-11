package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/MobSpawnerEntityIdentifiersFix.class */
public class MobSpawnerEntityIdentifiersFix extends DataFix {
    public MobSpawnerEntityIdentifiersFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    private Dynamic<?> fix(Dynamic<?> $$0) {
        if (!"MobSpawner".equals($$0.get(Entity.TAG_ID).asString(""))) {
            return $$0;
        }
        Optional<String> $$1 = $$0.get("EntityId").asString().result();
        if ($$1.isPresent()) {
            Dynamic<?> $$2 = (Dynamic) DataFixUtils.orElse($$0.get(BaseSpawner.SPAWN_DATA_TAG).result(), $$0.emptyMap());
            $$0 = $$0.set(BaseSpawner.SPAWN_DATA_TAG, $$2.set(Entity.TAG_ID, $$2.createString($$1.get().isEmpty() ? "Pig" : $$1.get()))).remove("EntityId");
        }
        Optional<? extends Stream<? extends Dynamic<?>>> $$3 = $$0.get("SpawnPotentials").asStreamOpt().result();
        if ($$3.isPresent()) {
            $$0 = $$0.set("SpawnPotentials", $$0.createList(((Stream) $$3.get()).map($$02 -> {
                Optional<String> $$12 = $$02.get("Type").asString().result();
                if ($$12.isPresent()) {
                    Dynamic<?> $$22 = ((Dynamic) DataFixUtils.orElse($$02.get(StateHolder.PROPERTIES_TAG).result(), $$02.emptyMap())).set(Entity.TAG_ID, $$02.createString($$12.get()));
                    return $$02.set("Entity", $$22).remove("Type").remove(StateHolder.PROPERTIES_TAG);
                }
                return $$02;
            })));
        }
        return $$0;
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getOutputSchema().getType(References.UNTAGGED_SPAWNER);
        return fixTypeEverywhereTyped("MobSpawnerEntityIdentifiersFix", getInputSchema().getType(References.UNTAGGED_SPAWNER), $$0, $$1 -> {
            Dynamic<?> $$2 = (Dynamic) $$1.get(DSL.remainderFinder());
            DataResult<? extends Pair<? extends Typed<?>, ?>> $$3 = $$0.readTyped(fix($$2.set(Entity.TAG_ID, $$2.createString("MobSpawner"))));
            if ($$3.result().isEmpty()) {
                return $$1;
            }
            return (Typed) ((Pair) $$3.result().get()).getFirst();
        });
    }
}
