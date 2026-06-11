package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/FoodToConsumableFix.class */
public class FoodToConsumableFix extends DataFix {
    public FoodToConsumableFix(Schema $$0) {
        super($$0, true);
    }

    protected TypeRewriteRule makeRule() {
        return writeFixAndRead("Food to consumable fix", getInputSchema().getType(References.DATA_COMPONENTS), getOutputSchema().getType(References.DATA_COMPONENTS), $$0 -> {
            Optional<? extends Dynamic<?>> $$1 = $$0.get("minecraft:food").result();
            if ($$1.isPresent()) {
                float $$2 = ((Dynamic) $$1.get()).get("eat_seconds").asFloat(1.6f);
                Stream<? extends Dynamic<?>> $$3 = ((Dynamic) $$1.get()).get("effects").asStream();
                Stream map = $$3.map($$0 -> {
                    return $$0.emptyMap().set(ChunkRegionIoEvent.Fields.TYPE, $$0.createString("minecraft:apply_effects")).set("effects", $$0.createList($$0.get("effect").result().stream())).set("probability", $$0.createFloat($$0.get("probability").asFloat(1.0f)));
                });
                Dynamic $$02 = Dynamic.copyField((Dynamic) $$1.get(), "using_converts_to", $$0, "minecraft:use_remainder").set("minecraft:food", ((Dynamic) $$1.get()).remove("eat_seconds").remove("effects").remove("using_converts_to"));
                return $$02.set("minecraft:consumable", $$02.emptyMap().set("consume_seconds", $$02.createFloat($$2)).set("on_consume_effects", $$02.createList(map)));
            }
            return $$0;
        });
    }
}
