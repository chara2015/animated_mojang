package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/DataComponentRemainderFix.class */
public abstract class DataComponentRemainderFix extends DataFix {
    private final String name;
    private final String componentId;
    private final String newComponentId;

    protected abstract <T> Dynamic<T> fixComponent(Dynamic<T> dynamic);

    public DataComponentRemainderFix(Schema $$0, String $$1, String $$2) {
        this($$0, $$1, $$2, $$2);
    }

    public DataComponentRemainderFix(Schema $$0, String $$1, String $$2, String $$3) {
        super($$0, false);
        this.name = $$1;
        this.componentId = $$2;
        this.newComponentId = $$3;
    }

    public final TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.DATA_COMPONENTS);
        return fixTypeEverywhereTyped(this.name, $$0, $$02 -> {
            return $$02.update(DSL.remainderFinder(), $$02 -> {
                Optional<? extends Dynamic<?>> $$1 = $$02.get(this.componentId).result();
                if ($$1.isEmpty()) {
                    return $$02;
                }
                Dynamic<?> $$2 = fixComponent((Dynamic) $$1.get());
                return $$02.remove(this.componentId).setFieldIfPresent(this.newComponentId, Optional.ofNullable($$2));
            });
        });
    }
}
