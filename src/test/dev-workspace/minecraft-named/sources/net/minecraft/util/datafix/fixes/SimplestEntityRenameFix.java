package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/SimplestEntityRenameFix.class */
public abstract class SimplestEntityRenameFix extends DataFix {
    private final String name;

    protected abstract String rename(String str);

    public SimplestEntityRenameFix(String $$0, Schema $$1, boolean $$2) {
        super($$1, $$2);
        this.name = $$0;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType<String> $$0 = getInputSchema().findChoiceType(References.ENTITY);
        TaggedChoice.TaggedChoiceType<String> $$1 = getOutputSchema().findChoiceType(References.ENTITY);
        Type<Pair<String, String>> $$2 = DSL.named(References.ENTITY_NAME.typeName(), NamespacedSchema.namespacedString());
        if (!Objects.equals(getOutputSchema().getType(References.ENTITY_NAME), $$2)) {
            throw new IllegalStateException("Entity name type is not what was expected.");
        }
        return TypeRewriteRule.seq(fixTypeEverywhere(this.name, $$0, $$1, $$22 -> {
            return $$22 -> {
                return $$22.mapFirst($$22 -> {
                    String $$3 = rename($$22);
                    Type<?> $$4 = (Type) $$0.types().get($$22);
                    Type<?> $$5 = (Type) $$1.types().get($$3);
                    if (!$$5.equals($$4, true, true)) {
                        throw new IllegalStateException(String.format(Locale.ROOT, "Dynamic type check failed: %s not equal to %s", $$5, $$4));
                    }
                    return $$3;
                });
            };
        }), fixTypeEverywhere(this.name + " for entity name", $$2, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(this::rename);
            };
        }));
    }
}
