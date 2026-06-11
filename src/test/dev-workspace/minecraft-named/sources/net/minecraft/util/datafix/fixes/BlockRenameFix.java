package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/BlockRenameFix.class */
public abstract class BlockRenameFix extends DataFix {
    private final String name;

    protected abstract String renameBlock(String str);

    public BlockRenameFix(Schema $$0, String $$1) {
        super($$0, false);
        this.name = $$1;
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.BLOCK_NAME);
        Type<Pair<String, String>> $$1 = DSL.named(References.BLOCK_NAME.typeName(), NamespacedSchema.namespacedString());
        if (!Objects.equals($$0, $$1)) {
            throw new IllegalStateException("block type is not what was expected.");
        }
        TypeRewriteRule $$2 = fixTypeEverywhere(this.name + " for block", $$1, $$02 -> {
            return $$02 -> {
                return $$02.mapSecond(this::renameBlock);
            };
        });
        TypeRewriteRule $$3 = fixTypeEverywhereTyped(this.name + " for block_state", getInputSchema().getType(References.BLOCK_STATE), $$03 -> {
            return $$03.update(DSL.remainderFinder(), this::fixBlockState);
        });
        TypeRewriteRule $$4 = fixTypeEverywhereTyped(this.name + " for flat_block_state", getInputSchema().getType(References.FLAT_BLOCK_STATE), $$04 -> {
            return $$04.update(DSL.remainderFinder(), $$04 -> {
                Optional map = $$04.asString().result().map(this::fixFlatBlockState);
                Objects.requireNonNull($$04);
                return (Dynamic) DataFixUtils.orElse(map.map($$04::createString), $$04);
            });
        });
        return TypeRewriteRule.seq($$2, new TypeRewriteRule[]{$$3, $$4});
    }

    private Dynamic<?> fixBlockState(Dynamic<?> $$0) {
        Optional<String> $$1 = $$0.get(StateHolder.NAME_TAG).asString().result();
        if ($$1.isPresent()) {
            return $$0.set(StateHolder.NAME_TAG, $$0.createString(renameBlock($$1.get())));
        }
        return $$0;
    }

    private String fixFlatBlockState(String $$0) {
        int $$1 = $$0.indexOf(91);
        int $$2 = $$0.indexOf(123);
        int $$3 = $$0.length();
        if ($$1 > 0) {
            $$3 = $$1;
        }
        if ($$2 > 0) {
            $$3 = Math.min($$3, $$2);
        }
        String $$4 = $$0.substring(0, $$3);
        String $$5 = renameBlock($$4);
        return $$5 + $$0.substring($$3);
    }

    public static DataFix create(Schema $$0, String $$1, final Function<String, String> $$2) {
        return new BlockRenameFix($$0, $$1) { // from class: net.minecraft.util.datafix.fixes.BlockRenameFix.1
            @Override // net.minecraft.util.datafix.fixes.BlockRenameFix
            protected String renameBlock(String $$02) {
                return (String) $$2.apply($$02);
            }
        };
    }
}
