package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.UnaryOperator;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/NamespacedTypeRenameFix.class */
public class NamespacedTypeRenameFix extends DataFix {
    private final String name;
    private final DSL.TypeReference type;
    private final UnaryOperator<String> renamer;

    public NamespacedTypeRenameFix(Schema $$0, String $$1, DSL.TypeReference $$2, UnaryOperator<String> $$3) {
        super($$0, false);
        this.name = $$1;
        this.type = $$2;
        this.renamer = $$3;
    }

    protected TypeRewriteRule makeRule() {
        Type<Pair<String, String>> $$0 = DSL.named(this.type.typeName(), NamespacedSchema.namespacedString());
        if (!Objects.equals($$0, getInputSchema().getType(this.type))) {
            throw new IllegalStateException("\"" + this.type.typeName() + "\" is not what was expected.");
        }
        return fixTypeEverywhere(this.name, $$0, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(this.renamer);
            };
        });
    }
}
