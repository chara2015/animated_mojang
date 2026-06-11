package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ReorganizePoi.class */
public class ReorganizePoi extends DataFix {
    public ReorganizePoi(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
        if (!Objects.equals($$0, getInputSchema().getType(References.POI_CHUNK))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return fixTypeEverywhere("POI reorganization", $$0, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(ReorganizePoi::cap);
            };
        });
    }

    private static <T> Dynamic<T> cap(Dynamic<T> $$0) {
        Map<Dynamic<T>, Dynamic<T>> $$1 = Maps.newHashMap();
        for (int $$2 = 0; $$2 < 16; $$2++) {
            String $$3 = String.valueOf($$2);
            Optional<Dynamic<T>> $$4 = $$0.get($$3).result();
            if ($$4.isPresent()) {
                Dynamic<T> $$5 = $$4.get();
                Dynamic<T> $$6 = $$0.createMap(ImmutableMap.of($$0.createString("Records"), $$5));
                $$1.put($$0.createString(Integer.toString($$2)), $$6);
                $$0 = $$0.remove($$3);
            }
        }
        return $$0.set("Sections", $$0.createMap($$1));
    }
}
