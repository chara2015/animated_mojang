package net.minecraft.world.item.slot;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/TransformedSlotSource.class */
public abstract class TransformedSlotSource implements SlotSource {
    protected final SlotSource slotSource;

    @Override // net.minecraft.world.item.slot.SlotSource
    public abstract MapCodec<? extends TransformedSlotSource> codec();

    protected abstract SlotCollection transform(SlotCollection slotCollection);

    protected TransformedSlotSource(SlotSource $$0) {
        this.slotSource = $$0;
    }

    protected static <T extends TransformedSlotSource> Products.P1<RecordCodecBuilder.Mu<T>, SlotSource> commonFields(RecordCodecBuilder.Instance<T> $$0) {
        return $$0.group(SlotSources.CODEC.fieldOf("slot_source").forGetter($$02 -> {
            return $$02.slotSource;
        }));
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public final SlotCollection provide(LootContext $$0) {
        return transform(this.slotSource.provide($$0));
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        this.slotSource.validate($$0.forChild(new ProblemReporter.FieldPathElement("slot_source")));
    }
}
