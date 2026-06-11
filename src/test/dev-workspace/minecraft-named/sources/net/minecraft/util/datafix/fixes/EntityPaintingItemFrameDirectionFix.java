package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/EntityPaintingItemFrameDirectionFix.class */
public class EntityPaintingItemFrameDirectionFix extends DataFix {
    private static final int[][] DIRECTIONS = {new int[]{0, 0, 1}, new int[]{-1, 0, 0}, new int[]{0, 0, -1}, new int[]{1, 0, 0}};

    public EntityPaintingItemFrameDirectionFix(Schema $$0, boolean $$1) {
        super($$0, $$1);
    }

    private Dynamic<?> doFix(Dynamic<?> $$0, boolean $$1, boolean $$2) {
        int $$3;
        Dynamic<?> $$02;
        if (($$1 || $$2) && $$0.get("Facing").asNumber().result().isEmpty()) {
            if ($$0.get("Direction").asNumber().result().isPresent()) {
                $$3 = $$0.get("Direction").asByte((byte) 0) % DIRECTIONS.length;
                int[] $$4 = DIRECTIONS[$$3];
                Dynamic<?> $$03 = $$0.set("TileX", $$0.createInt($$0.get("TileX").asInt(0) + $$4[0]));
                Dynamic<?> $$04 = $$03.set("TileY", $$03.createInt($$03.get("TileY").asInt(0) + $$4[1]));
                $$02 = $$04.set("TileZ", $$04.createInt($$04.get("TileZ").asInt(0) + $$4[2])).remove("Direction");
                if ($$2 && $$02.get("ItemRotation").asNumber().result().isPresent()) {
                    $$02 = $$02.set("ItemRotation", $$02.createByte((byte) ($$02.get("ItemRotation").asByte((byte) 0) * 2)));
                }
            } else {
                $$3 = $$0.get("Dir").asByte((byte) 0) % DIRECTIONS.length;
                $$02 = $$0.remove("Dir");
            }
            $$0 = $$02.set("Facing", $$02.createByte((byte) $$3));
        }
        return $$0;
    }

    public TypeRewriteRule makeRule() {
        Type<?> $$0 = getInputSchema().getChoiceType(References.ENTITY, "Painting");
        OpticFinder<?> $$1 = DSL.namedChoice("Painting", $$0);
        Type<?> $$2 = getInputSchema().getChoiceType(References.ENTITY, "ItemFrame");
        OpticFinder<?> $$3 = DSL.namedChoice("ItemFrame", $$2);
        Type<?> $$4 = getInputSchema().getType(References.ENTITY);
        TypeRewriteRule $$5 = fixTypeEverywhereTyped("EntityPaintingFix", $$4, $$22 -> {
            return $$22.updateTyped($$1, $$0, $$02 -> {
                return $$02.update(DSL.remainderFinder(), $$02 -> {
                    return doFix($$02, true, false);
                });
            });
        });
        TypeRewriteRule $$6 = fixTypeEverywhereTyped("EntityItemFrameFix", $$4, $$23 -> {
            return $$23.updateTyped($$3, $$2, $$02 -> {
                return $$02.update(DSL.remainderFinder(), $$02 -> {
                    return doFix($$02, false, true);
                });
            });
        });
        return TypeRewriteRule.seq($$5, $$6);
    }
}
