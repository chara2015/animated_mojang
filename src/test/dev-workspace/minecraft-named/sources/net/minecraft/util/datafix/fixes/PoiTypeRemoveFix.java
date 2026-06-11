package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/PoiTypeRemoveFix.class */
public class PoiTypeRemoveFix extends AbstractPoiSectionFix {
    private final Predicate<String> typesToKeep;

    public PoiTypeRemoveFix(Schema $$0, String $$1, Predicate<String> $$2) {
        super($$0, $$1);
        this.typesToKeep = $$2.negate();
    }

    @Override // net.minecraft.util.datafix.fixes.AbstractPoiSectionFix
    protected <T> Stream<Dynamic<T>> processRecords(Stream<Dynamic<T>> $$0) {
        return $$0.filter(this::shouldKeepRecord);
    }

    private <T> boolean shouldKeepRecord(Dynamic<T> $$0) {
        return $$0.get(ChunkRegionIoEvent.Fields.TYPE).asString().result().filter(this.typesToKeep).isPresent();
    }
}
