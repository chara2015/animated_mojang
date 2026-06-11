package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/MapBannerBlockPosFormatFix.class */
public class MapBannerBlockPosFormatFix extends DataFix {
    public MapBannerBlockPosFormatFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getType(References.SAVED_DATA_MAP_DATA);
        OpticFinder<?> $$1 = $$0.findField("data");
        OpticFinder<?> $$2 = $$1.type().findField("banners");
        OpticFinder<?> $$3 = DSL.typeFinder($$2.type().getElement());
        return fixTypeEverywhereTyped("MapBannerBlockPosFormatFix", $$0, $$32 -> {
            return $$32.updateTyped($$1, $$22 -> {
                return $$22.updateTyped($$2, $$12 -> {
                    return $$12.updateTyped($$3, $$02 -> {
                        return $$02.update(DSL.remainderFinder(), $$02 -> {
                            return $$02.update(Entity.TAG_POS, ExtraDataFixUtils::fixBlockPos);
                        });
                    });
                });
            });
        });
    }
}
