package net.minecraft.util.datafix.fixes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.world.level.block.state.StateHolder;
import org.apache.commons.lang3.math.NumberUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LevelFlatGeneratorInfoFix.class */
public class LevelFlatGeneratorInfoFix extends DataFix {
    private static final String GENERATOR_OPTIONS = "generatorOptions";

    @VisibleForTesting
    static final String DEFAULT = "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
    private static final Splitter SPLITTER = Splitter.on(';').limit(5);
    private static final Splitter LAYER_SPLITTER = Splitter.on(',');
    private static final Splitter OLD_AMOUNT_SPLITTER = Splitter.on('x').limit(2);
    private static final Splitter AMOUNT_SPLITTER = Splitter.on('*').limit(2);
    private static final Splitter BLOCK_SPLITTER = Splitter.on(':').limit(3);

    public LevelFlatGeneratorInfoFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    public TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", getInputSchema().getType(References.LEVEL), $$0 -> {
            return $$0.update(DSL.remainderFinder(), this::fix);
        });
    }

    private Dynamic<?> fix(Dynamic<?> $$0) {
        if ($$0.get("generatorName").asString("").equalsIgnoreCase("flat")) {
            return $$0.update("generatorOptions", $$02 -> {
                DataResult map = $$02.asString().map(this::fixString);
                Objects.requireNonNull($$02);
                return (Dynamic) DataFixUtils.orElse(map.map($$02::createString).result(), $$02);
            });
        }
        return $$0;
    }

    @VisibleForTesting
    String fixString(String $$0) {
        int $$5;
        String $$6;
        if ($$0.isEmpty()) {
            return DEFAULT;
        }
        Iterator<String> $$1 = SPLITTER.split($$0).iterator();
        String $$2 = $$1.next();
        if ($$1.hasNext()) {
            $$5 = NumberUtils.toInt($$2, 0);
            $$6 = $$1.next();
        } else {
            $$5 = 0;
            $$6 = $$2;
        }
        if ($$5 < 0 || $$5 > 3) {
            return DEFAULT;
        }
        StringBuilder $$7 = new StringBuilder();
        Splitter $$8 = $$5 < 3 ? OLD_AMOUNT_SPLITTER : AMOUNT_SPLITTER;
        int i = $$5;
        $$7.append((String) StreamSupport.stream(LAYER_SPLITTER.split($$6).spliterator(), false).map($$22 -> {
            int $$62;
            String $$72;
            List<String> $$3 = $$8.splitToList($$22);
            if ($$3.size() == 2) {
                $$62 = NumberUtils.toInt($$3.get(0));
                $$72 = $$3.get(1);
            } else {
                $$62 = 1;
                $$72 = $$3.get(0);
            }
            List<String> $$82 = BLOCK_SPLITTER.splitToList($$72);
            int $$9 = $$82.get(0).equals("minecraft") ? 1 : 0;
            String $$10 = $$82.get($$9);
            int $$11 = i == 3 ? EntityBlockStateFix.getBlockId("minecraft:" + $$10) : NumberUtils.toInt($$10, 0);
            int $$12 = $$9 + 1;
            int $$13 = $$82.size() > $$12 ? NumberUtils.toInt($$82.get($$12), 0) : 0;
            return ($$62 == 1 ? "" : $$62 + "*") + BlockStateData.getTag(($$11 << 4) | $$13).get(StateHolder.NAME_TAG).asString("");
        }).collect(Collectors.joining(",")));
        while ($$1.hasNext()) {
            $$7.append(';').append($$1.next());
        }
        return $$7.toString();
    }
}
