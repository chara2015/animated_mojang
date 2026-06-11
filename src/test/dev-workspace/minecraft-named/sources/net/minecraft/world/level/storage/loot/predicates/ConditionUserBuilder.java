package net.minecraft.world.level.storage.loot.predicates;

import java.util.Iterator;
import java.util.function.Function;
import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/predicates/ConditionUserBuilder.class */
public interface ConditionUserBuilder<T extends ConditionUserBuilder<T>> {
    T when(LootItemCondition.Builder builder);

    T unwrap();

    default <E> T when(Iterable<E> iterable, Function<E, LootItemCondition.Builder> function) {
        ConditionUserBuilder conditionUserBuilderUnwrap = unwrap();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            conditionUserBuilderUnwrap = conditionUserBuilderUnwrap.when(function.apply(it.next()));
        }
        return (T) conditionUserBuilderUnwrap;
    }
}
