package net.minecraft.client.renderer.block.model.multipart;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.client.renderer.block.model.multipart.CombinedCondition;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/multipart/Condition.class */
@FunctionalInterface
public interface Condition {
    public static final Codec<Condition> CODEC = Codec.recursive("condition", $$0 -> {
        Codec<CombinedCondition> $$1 = Codec.simpleMap(CombinedCondition.Operation.CODEC, $$0.listOf(), StringRepresentable.keys(CombinedCondition.Operation.values())).codec().comapFlatMap($$0 -> {
            if ($$0.size() != 1) {
                return DataResult.error(() -> {
                    return "Invalid map size for combiner condition, expected exactly one element";
                });
            }
            Map.Entry<CombinedCondition.Operation, List<Condition>> $$12 = (Map.Entry) $$0.entrySet().iterator().next();
            return DataResult.success(new CombinedCondition($$12.getKey(), $$12.getValue()));
        }, $$02 -> {
            return Map.of($$02.operation(), $$02.terms());
        });
        return Codec.either($$1, KeyValueCondition.CODEC).flatComapMap($$03 -> {
            return (Condition) $$03.map($$03 -> {
                return $$03;
            }, $$04 -> {
                return $$04;
            });
        }, $$04 -> {
            DataResult<Either<CombinedCondition, KeyValueCondition>> dataResultError;
            Objects.requireNonNull($$04);
            switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), CombinedCondition.class, KeyValueCondition.class).dynamicInvoker().invoke($$04, 0) /* invoke-custom */) {
                case 0:
                    CombinedCondition $$3 = (CombinedCondition) $$04;
                    dataResultError = DataResult.success(Either.left($$3));
                    break;
                case 1:
                    KeyValueCondition $$4 = (KeyValueCondition) $$04;
                    dataResultError = DataResult.success(Either.right($$4));
                    break;
                default:
                    dataResultError = DataResult.error(() -> {
                        return "Unrecognized condition";
                    });
                    break;
            }
            DataResult<Either<CombinedCondition, KeyValueCondition>> $$5 = dataResultError;
            return $$5;
        });
    });

    <O, S extends StateHolder<O, S>> Predicate<S> instantiate(StateDefinition<O, S> stateDefinition);
}
