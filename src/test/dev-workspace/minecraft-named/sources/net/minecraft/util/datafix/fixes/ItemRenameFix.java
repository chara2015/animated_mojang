package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/ItemRenameFix.class */
public abstract class ItemRenameFix extends DataFix {
    private final String name;

    protected abstract String fixItem(String str);

    public ItemRenameFix(Schema $$0, String $$1) {
        super($$0, false);
        this.name = $$1;
    }

    public TypeRewriteRule makeRule() {
        Type<Pair<String, String>> $$0 = DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString());
        if (!Objects.equals(getInputSchema().getType(References.ITEM_NAME), $$0)) {
            throw new IllegalStateException("item name type is not what was expected.");
        }
        return fixTypeEverywhere(this.name, $$0, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(this::fixItem);
            };
        });
    }

    public static DataFix create(Schema $$0, String $$1, final Function<String, String> $$2) {
        return new ItemRenameFix($$0, $$1) { // from class: net.minecraft.util.datafix.fixes.ItemRenameFix.1
            @Override // net.minecraft.util.datafix.fixes.ItemRenameFix
            protected String fixItem(String $$02) {
                return (String) $$2.apply($$02);
            }
        };
    }
}
