package net.minecraft.world.item.slot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/slot/CompositeSlotSource.class */
public abstract class CompositeSlotSource implements SlotSource {
    protected final List<SlotSource> terms;
    private final Function<LootContext, SlotCollection> compositeSlotSource;

    @Override // net.minecraft.world.item.slot.SlotSource
    public abstract MapCodec<? extends CompositeSlotSource> codec();

    protected CompositeSlotSource(List<SlotSource> $$0) {
        this.terms = $$0;
        this.compositeSlotSource = SlotSources.group($$0);
    }

    protected static <T extends CompositeSlotSource> MapCodec<T> createCodec(Function<List<SlotSource>, T> $$0) {
        return RecordCodecBuilder.mapCodec($$1 -> {
            return $$1.group(SlotSources.CODEC.listOf().fieldOf("terms").forGetter($$02 -> {
                return $$02.terms;
            })).apply($$1, $$0);
        });
    }

    protected static <T extends CompositeSlotSource> Codec<T> createInlineCodec(Function<List<SlotSource>, T> $$0) {
        return SlotSources.CODEC.listOf().xmap($$0, $$02 -> {
            return $$02.terms;
        });
    }

    @Override // net.minecraft.world.item.slot.SlotSource
    public SlotCollection provide(LootContext $$0) {
        return this.compositeSlotSource.apply($$0);
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        for (int $$1 = 0; $$1 < this.terms.size(); $$1++) {
            this.terms.get($$1).validate($$0.forChild(new ProblemReporter.IndexedFieldPathElement("terms", $$1)));
        }
    }
}
