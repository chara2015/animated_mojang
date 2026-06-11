package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.stream.Stream;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AbstractPoiSectionFix.class */
public abstract class AbstractPoiSectionFix extends DataFix {
    private final String name;

    protected abstract <T> Stream<Dynamic<T>> processRecords(Stream<Dynamic<T>> stream);

    public AbstractPoiSectionFix(Schema $$0, String $$1) {
        super($$0, false);
        this.name = $$1;
    }

    protected TypeRewriteRule makeRule() {
        Type<Pair<String, Dynamic<?>>> $$0 = DSL.named(References.POI_CHUNK.typeName(), DSL.remainderType());
        if (!Objects.equals($$0, getInputSchema().getType(References.POI_CHUNK))) {
            throw new IllegalStateException("Poi type is not what was expected.");
        }
        return fixTypeEverywhere(this.name, $$0, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(this::cap);
            };
        });
    }

    private <T> Dynamic<T> cap(Dynamic<T> $$0) {
        return $$0.update("Sections", $$02 -> {
            return $$02.updateMapValues($$02 -> {
                return $$02.mapSecond(this::processSection);
            });
        });
    }

    private Dynamic<?> processSection(Dynamic<?> $$0) {
        return $$0.update("Records", this::processSectionRecords);
    }

    private <T> Dynamic<T> processSectionRecords(Dynamic<T> $$0) {
        return (Dynamic) DataFixUtils.orElse($$0.asStreamOpt().result().map($$1 -> {
            return $$0.createList(processRecords($$1));
        }), $$0);
    }
}
