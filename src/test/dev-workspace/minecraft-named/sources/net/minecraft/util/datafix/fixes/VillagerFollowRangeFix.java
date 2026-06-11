package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.levelgen.Density;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/VillagerFollowRangeFix.class */
public class VillagerFollowRangeFix extends NamedEntityFix {
    private static final double ORIGINAL_VALUE = 16.0d;
    private static final double NEW_BASE_VALUE = 48.0d;

    public VillagerFollowRangeFix(Schema $$0) {
        super($$0, false, "Villager Follow Range Fix", References.ENTITY, "minecraft:villager");
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), VillagerFollowRangeFix::fixValue);
    }

    private static Dynamic<?> fixValue(Dynamic<?> $$0) {
        return $$0.update("Attributes", $$1 -> {
            return $$0.createList($$1.asStream().map($$02 -> {
                if (!$$02.get(StateHolder.NAME_TAG).asString("").equals("generic.follow_range") || $$02.get("Base").asDouble(Density.SURFACE) != 16.0d) {
                    return $$02;
                }
                return $$02.set("Base", $$02.createDouble(NEW_BASE_VALUE));
            }));
        });
    }
}
