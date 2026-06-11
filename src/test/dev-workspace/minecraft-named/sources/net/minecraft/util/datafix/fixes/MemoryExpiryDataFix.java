package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/MemoryExpiryDataFix.class */
public class MemoryExpiryDataFix extends NamedEntityFix {
    public MemoryExpiryDataFix(Schema $$0, String $$1) {
        super($$0, false, "Memory expiry data fix (" + $$1 + ")", References.ENTITY, $$1);
    }

    @Override // net.minecraft.util.datafix.fixes.NamedEntityFix
    protected Typed<?> fix(Typed<?> $$0) {
        return $$0.update(DSL.remainderFinder(), this::fixTag);
    }

    public Dynamic<?> fixTag(Dynamic<?> $$0) {
        return $$0.update(LivingEntity.TAG_BRAIN, this::updateBrain);
    }

    private Dynamic<?> updateBrain(Dynamic<?> $$0) {
        return $$0.update("memories", this::updateMemories);
    }

    private Dynamic<?> updateMemories(Dynamic<?> $$0) {
        return $$0.updateMapValues(this::updateMemoryEntry);
    }

    private Pair<Dynamic<?>, Dynamic<?>> updateMemoryEntry(Pair<Dynamic<?>, Dynamic<?>> $$0) {
        return $$0.mapSecond(this::wrapMemoryValue);
    }

    private Dynamic<?> wrapMemoryValue(Dynamic<?> $$0) {
        return $$0.createMap(ImmutableMap.of($$0.createString("value"), $$0));
    }
}
