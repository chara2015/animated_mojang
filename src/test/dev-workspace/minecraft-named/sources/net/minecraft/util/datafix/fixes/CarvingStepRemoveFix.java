package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/CarvingStepRemoveFix.class */
public class CarvingStepRemoveFix extends DataFix {
    public CarvingStepRemoveFix(Schema $$0) {
        super($$0, false);
    }

    protected TypeRewriteRule makeRule() {
        return fixTypeEverywhereTyped("CarvingStepRemoveFix", getInputSchema().getType(References.CHUNK), CarvingStepRemoveFix::fixChunk);
    }

    private static Typed<?> fixChunk(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), $$02 -> {
            Dynamic dynamic = $$02;
            Optional<? extends Dynamic<?>> $$2 = dynamic.get("CarvingMasks").result();
            if ($$2.isPresent()) {
                Optional<? extends Dynamic<?>> $$3 = ((Dynamic) $$2.get()).get("AIR").result();
                if ($$3.isPresent()) {
                    dynamic = dynamic.set("carving_mask", (Dynamic) $$3.get());
                }
            }
            return dynamic.remove("CarvingMasks");
        });
    }
}
