package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockNameFlatteningFix.class */
public class BlockNameFlatteningFix extends DataFix {
    public BlockNameFlatteningFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.BLOCK_NAME);
        Type<?> $$1 = getOutputSchema().getType(References.BLOCK_NAME);
        Type<Pair<String, Either<Integer, String>>> $$2 = DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString()));
        Type<Pair<String, String>> $$3 = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
        if (!Objects.equals($$0, $$2) || !Objects.equals($$1, $$3)) {
            throw new IllegalStateException("Expected and actual types don't match.");
        }
        return fixTypeEverywhere("BlockNameFlatteningFix", $$2, $$3, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond($$02 -> {
                    return (String) $$02.map((v0) -> {
                        return BlockStateData.upgradeBlock(v0);
                    }, $$02 -> {
                        return BlockStateData.upgradeBlock(NamespacedSchema.ensureNamespaced($$02));
                    });
                });
            };
        });
    }
}
