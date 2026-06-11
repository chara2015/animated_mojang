package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/NamedEntityWriteReadFix.class */
public abstract class NamedEntityWriteReadFix extends DataFix {
    private final String name;
    private final String entityName;
    private final DSL.TypeReference type;

    protected abstract <T> Dynamic<T> fix(Dynamic<T> dynamic);

    public NamedEntityWriteReadFix(Schema $$0, boolean $$1, String $$2, DSL.TypeReference $$3, String $$4) {
        super($$0, $$1);
        this.name = $$2;
        this.type = $$3;
        this.entityName = $$4;
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(this.type);
        Type<?> $$1 = getInputSchema().getChoiceType(this.type, this.entityName);
        Type<?> $$2 = getOutputSchema().getType(this.type);
        OpticFinder<?> $$3 = DSL.namedChoice(this.entityName, $$1);
        Type<?> $$4 = ExtraDataFixUtils.patchSubType($$0, $$0, $$2);
        return fix($$0, $$2, $$4, $$3);
    }

    private <S, T, A> TypeRewriteRule fix(Type<S> $$0, Type<T> $$1, Type<?> $$2, OpticFinder<A> $$3) {
        return fixTypeEverywhereTyped(this.name, $$0, $$1, $$32 -> {
            if ($$32.getOptional($$3).isEmpty()) {
                return ExtraDataFixUtils.cast($$1, $$32);
            }
            Typed<?> $$4 = ExtraDataFixUtils.cast($$2, $$32);
            return Util.writeAndReadTypedOrThrow($$4, $$1, this::fix);
        });
    }
}
