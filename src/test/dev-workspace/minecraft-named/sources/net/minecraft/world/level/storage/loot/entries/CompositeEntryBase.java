package net.minecraft.world.level.storage.loot.entries;

import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/CompositeEntryBase.class */
public abstract class CompositeEntryBase extends LootPoolEntryContainer {
    public static final ProblemReporter.Problem NO_CHILDREN_PROBLEM = new ProblemReporter.Problem() { // from class: net.minecraft.world.level.storage.loot.entries.CompositeEntryBase.1
        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Empty children list";
        }
    };
    protected final List<LootPoolEntryContainer> children;
    private final ComposableEntryContainer composedChildren;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/entries/CompositeEntryBase$CompositeEntryConstructor.class */
    @FunctionalInterface
    public interface CompositeEntryConstructor<T extends CompositeEntryBase> {
        T create(List<LootPoolEntryContainer> list, List<LootItemCondition> list2);
    }

    protected abstract ComposableEntryContainer compose(List<? extends ComposableEntryContainer> list);

    protected CompositeEntryBase(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
        super($$1);
        this.children = $$0;
        this.composedChildren = compose($$0);
    }

    @Override // net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer
    public void validate(ValidationContext $$0) {
        super.validate($$0);
        if (this.children.isEmpty()) {
            $$0.reportProblem(NO_CHILDREN_PROBLEM);
        }
        for (int $$1 = 0; $$1 < this.children.size(); $$1++) {
            this.children.get($$1).validate($$0.forChild(new ProblemReporter.IndexedFieldPathElement("children", $$1)));
        }
    }

    @Override // net.minecraft.world.level.storage.loot.entries.ComposableEntryContainer
    public final boolean expand(LootContext $$0, Consumer<LootPoolEntry> $$1) {
        if (!canRun($$0)) {
            return false;
        }
        return this.composedChildren.expand($$0, $$1);
    }

    public static <T extends CompositeEntryBase> MapCodec<T> createCodec(CompositeEntryConstructor<T> $$0) {
        return RecordCodecBuilder.mapCodec($$1 -> {
            Products.P2 p2And = $$1.group(LootPoolEntries.CODEC.listOf().optionalFieldOf("children", List.of()).forGetter($$02 -> {
                return $$02.children;
            })).and(commonFields($$1).t1());
            Objects.requireNonNull($$0);
            return p2And.apply($$1, $$0::create);
        });
    }
}
