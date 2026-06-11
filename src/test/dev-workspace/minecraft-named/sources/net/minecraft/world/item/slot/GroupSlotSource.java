package net.minecraft.world.item.slot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/GroupSlotSource.class */
public class GroupSlotSource extends CompositeSlotSource {
    public static final MapCodec<GroupSlotSource> MAP_CODEC = createCodec(GroupSlotSource::new);
    public static final Codec<GroupSlotSource> INLINE_CODEC = createInlineCodec(GroupSlotSource::new);

    private GroupSlotSource(List<SlotSource> $$0) {
        super($$0);
    }

    @Override // net.minecraft.world.item.slot.CompositeSlotSource, net.minecraft.world.item.slot.SlotSource
    public MapCodec<GroupSlotSource> codec() {
        return MAP_CODEC;
    }
}
