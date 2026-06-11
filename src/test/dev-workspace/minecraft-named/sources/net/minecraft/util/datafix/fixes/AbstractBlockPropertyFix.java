package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/AbstractBlockPropertyFix.class */
public abstract class AbstractBlockPropertyFix extends DataFix {
    private final String name;

    protected abstract boolean shouldFix(String str);

    protected abstract <T> Dynamic<T> fixProperties(String str, Dynamic<T> dynamic);

    public AbstractBlockPropertyFix(Schema $$0, String $$1) {
        super($$0, false);
        this.name = $$1;
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped(this.name, getInputSchema().getType(References.BLOCK_STATE), $$0 -> {
            return $$0.update(DSL.remainderFinder(), this::fixBlockState);
        });
    }

    private Dynamic<?> fixBlockState(Dynamic<?> $$0) {
        Optional<String> $$1 = $$0.get(StateHolder.NAME_TAG).asString().result().map(NamespacedSchema::ensureNamespaced);
        if ($$1.isPresent() && shouldFix($$1.get())) {
            return $$0.update(StateHolder.PROPERTIES_TAG, $$12 -> {
                return fixProperties((String) $$1.get(), $$12);
            });
        }
        return $$0;
    }
}
