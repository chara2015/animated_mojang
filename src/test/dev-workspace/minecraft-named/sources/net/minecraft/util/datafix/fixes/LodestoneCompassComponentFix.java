package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/fixes/LodestoneCompassComponentFix.class */
public class LodestoneCompassComponentFix extends DataComponentRemainderFix {
    public LodestoneCompassComponentFix(Schema $$0) {
        super($$0, "LodestoneCompassComponentFix", "minecraft:lodestone_target", "minecraft:lodestone_tracker");
    }

    @Override // net.minecraft.util.datafix.fixes.DataComponentRemainderFix
    protected <T> Dynamic<T> fixComponent(Dynamic<T> $$0) {
        Optional<Dynamic<T>> $$1 = $$0.get("pos").result();
        Optional<Dynamic<T>> $$2 = $$0.get(ChunkRegionIoEvent.Fields.DIMENSION).result();
        Dynamic<T> $$02 = $$0.remove("pos").remove(ChunkRegionIoEvent.Fields.DIMENSION);
        if ($$1.isPresent() && $$2.isPresent()) {
            $$02 = $$02.set(JigsawBlockEntity.TARGET, $$02.emptyMap().set("pos", $$1.get()).set(ChunkRegionIoEvent.Fields.DIMENSION, $$2.get()));
        }
        return $$02;
    }
}
