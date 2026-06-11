package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LevelLegacyWorldGenSettingsFix.class */
public class LevelLegacyWorldGenSettingsFix extends DataFix {
    private static final String WORLD_GEN_SETTINGS = "WorldGenSettings";
    private static final List<String> OLD_SETTINGS_KEYS = List.of("RandomSeed", "generatorName", LevelDataGeneratorOptionsFix.GENERATOR_OPTIONS, "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest");

    public LevelLegacyWorldGenSettingsFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("LevelLegacyWorldGenSettingsFix", getInputSchema().getType(References.LEVEL), $$0 -> {
            return $$0.update(DSL.remainderFinder(), $$0 -> {
                Dynamic<?> $$1 = $$0.get(WORLD_GEN_SETTINGS).orElseEmptyMap();
                for (String $$2 : OLD_SETTINGS_KEYS) {
                    Optional<? extends Dynamic<?>> $$3 = $$0.get($$2).result();
                    if ($$3.isPresent()) {
                        $$0 = $$0.remove($$2);
                        $$1 = $$1.set($$2, (Dynamic) $$3.get());
                    }
                }
                return $$0.set(WORLD_GEN_SETTINGS, $$1);
            });
        });
    }
}
