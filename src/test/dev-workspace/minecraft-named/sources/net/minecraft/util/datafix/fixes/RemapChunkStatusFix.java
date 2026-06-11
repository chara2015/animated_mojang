package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/RemapChunkStatusFix.class */
public class RemapChunkStatusFix extends DataFix {
    private final String name;
    private final UnaryOperator<String> mapper;

    public RemapChunkStatusFix(Schema $$0, String $$1, UnaryOperator<String> $$2) {
        super($$0, false);
        this.name = $$1;
        this.mapper = $$2;
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.CHUNK), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                return $$0.update("Status", this::fixStatus).update("below_zero_retrogen", $$0 -> {
                    return $$0.update("target_status", this::fixStatus);
                });
            });
        });
    }

    private <T> Dynamic<T> fixStatus(Dynamic<T> $$0) {
        Optional map = $$0.asString().result().map(NamespacedSchema::ensureNamespaced).map(this.mapper);
        Objects.requireNonNull($$0);
        Optional<Dynamic<T>> $$1 = map.map($$0::createString);
        return (Dynamic) DataFixUtils.orElse($$1, $$0);
    }
}
