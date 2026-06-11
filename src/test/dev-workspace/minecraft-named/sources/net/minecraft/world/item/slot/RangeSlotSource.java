package net.minecraft.world.item.slot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.SlotProvider;
import net.minecraft.world.inventory.SlotRange;
import net.minecraft.world.inventory.SlotRanges;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextArg;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/RangeSlotSource.class */
public class RangeSlotSource implements SlotSource {
    public static final MapCodec<RangeSlotSource> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(LootContextArg.ENTITY_OR_BLOCK.fieldOf("source").forGetter($$0 -> {
            return $$0.source;
        }), SlotRanges.CODEC.fieldOf("slots").forGetter($$02 -> {
            return $$02.slotRange;
        })).apply($$0, RangeSlotSource::new);
    });
    private final LootContextArg<Object> source;
    private final SlotRange slotRange;

    private RangeSlotSource(LootContextArg<Object> $$0, SlotRange $$1) {
        this.source = $$0;
        this.slotRange = $$1;
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public MapCodec<RangeSlotSource> codec() {
        return MAP_CODEC;
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Set.of(this.source.contextParam());
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public final SlotCollection provide(LootContext $$0) {
        Object $$1 = this.source.get($$0);
        if ($$1 instanceof SlotProvider) {
            SlotProvider $$2 = (SlotProvider) $$1;
            return $$2.getSlotsFromRange(this.slotRange.slots());
        }
        return SlotCollection.EMPTY;
    }
}
