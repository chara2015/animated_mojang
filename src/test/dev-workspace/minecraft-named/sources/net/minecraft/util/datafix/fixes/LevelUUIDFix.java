package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.block.state.StateHolder;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LevelUUIDFix.class */
public class LevelUUIDFix extends AbstractUUIDFix {
    private static final Logger LOGGER = LogUtils.getLogger();

    public LevelUUIDFix(Schema $$0) {
        super($$0, References.LEVEL);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(this.typeReference);
        OpticFinder<?> $$1 = $$0.findField("CustomBossEvents");
        OpticFinder<?> $$2 = DSL.typeFinder(DSL.and(DSL.optional(DSL.field(StateHolder.NAME_TAG, getInputSchema().getTypeRaw(References.TEXT_COMPONENT))), DSL.remainderType()));
        return fixTypeEverywhereTyped("LevelUUIDFix", $$0, $$22 -> {
            return $$22.update(DSL.remainderFinder(), $$02 -> {
                return updateWanderingTrader(updateDragonFight($$02));
            }).updateTyped($$1, $$12 -> {
                return $$12.updateTyped($$2, $$03 -> {
                    return $$03.update(DSL.remainderFinder(), this::updateCustomBossEvent);
                });
            });
        });
    }

    private Dynamic<?> updateWanderingTrader(Dynamic<?> $$0) {
        return replaceUUIDString($$0, "WanderingTraderId", "WanderingTraderId").orElse($$0);
    }

    private Dynamic<?> updateDragonFight(Dynamic<?> $$0) {
        return $$0.update("DimensionData", $$02 -> {
            return $$02.updateMapValues($$02 -> {
                return $$02.mapSecond($$02 -> {
                    return $$02.update("DragonFight", $$02 -> {
                        return replaceUUIDLeastMost($$02, "DragonUUID", "Dragon").orElse($$02);
                    });
                });
            });
        });
    }

    private Dynamic<?> updateCustomBossEvent(Dynamic<?> $$0) {
        return $$0.update("Players", $$1 -> {
            return $$0.createList($$1.asStream().map($$02 -> {
                return createUUIDFromML($$02).orElseGet(() -> {
                    LOGGER.warn("CustomBossEvents contains invalid UUIDs.");
                    return $$02;
                });
            }));
        });
    }
}
