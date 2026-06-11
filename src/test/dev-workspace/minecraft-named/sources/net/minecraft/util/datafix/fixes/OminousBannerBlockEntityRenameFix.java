package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/OminousBannerBlockEntityRenameFix.class */
public class OminousBannerBlockEntityRenameFix extends NamedEntityFix {
    public OminousBannerBlockEntityRenameFix(Schema $$0, boolean $$1) {
        super($$0, $$1, "OminousBannerBlockEntityRenameFix", References.BLOCK_ENTITY, "minecraft:banner");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        OpticFinder<?> $$1 = $$0.getType().findField(Entity.TAG_CUSTOM_NAME);
        OpticFinder<Pair<String, String>> $$2 = DSL.typeFinder(getInputSchema().getType(References.TEXT_COMPONENT));
        return $$0.updateTyped($$1, $$12 -> {
            return $$12.update($$2, $$02 -> {
                return $$02.mapSecond($$02 -> {
                    return $$02.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
                });
            });
        });
    }
}
