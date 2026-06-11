package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.ExtraDataFixUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityRenameFix.class */
public abstract class EntityRenameFix extends DataFix {
    protected final String name;

    protected abstract Pair<String, Typed<?>> fix(String str, Typed<?> typed);

    public EntityRenameFix(String $$0, Schema $$1, boolean $$2) {
        super($$1, $$2);
        this.name = $$0;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
        TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
        Function<String, Type<?>> $$2 = Util.memoize($$22 -> {
            Type<?> $$3 = (Type) $$0.types().get($$22);
            return ExtraDataFixUtils.patchSubType($$3, $$0, $$1);
        });
        return fixTypeEverywhere(this.name, $$0, $$1, $$23 -> {
            return $$3 -> {
                String $$4 = (String) $$3.getFirst();
                Type<?> $$5 = (Type) $$2.apply($$4);
                Pair<String, Typed<?>> $$6 = fix($$4, getEntity($$3.getSecond(), $$23, $$5));
                Type<?> $$7 = (Type) $$1.types().get($$6.getFirst());
                if (!$$7.equals(((Typed) $$6.getSecond()).getType(), true, true)) {
                    throw new IllegalStateException(String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", $$7, ((Typed) $$6.getSecond()).getType()));
                }
                return Pair.of((String) $$6.getFirst(), ((Typed) $$6.getSecond()).getValue());
            };
        });
    }

    private <A> Typed<A> getEntity(Object $$0, DynamicOps<?> $$1, Type<A> $$2) {
        return new Typed<>($$2, $$1, $$0);
    }
}
