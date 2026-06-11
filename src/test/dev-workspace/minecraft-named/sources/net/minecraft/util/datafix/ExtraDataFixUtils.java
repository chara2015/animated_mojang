package net.minecraft.util.datafix;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.View;
import com.mojang.datafixers.functions.PointFreeRule;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.BitSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.StateHolder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/datafix/ExtraDataFixUtils.class */
public class ExtraDataFixUtils {
    public static Dynamic<?> fixBlockPos(Dynamic<?> $$0) {
        Optional<Number> $$1 = $$0.get("X").asNumber().result();
        Optional<Number> $$2 = $$0.get("Y").asNumber().result();
        Optional<Number> $$3 = $$0.get("Z").asNumber().result();
        if ($$1.isEmpty() || $$2.isEmpty() || $$3.isEmpty()) {
            return $$0;
        }
        return createBlockPos($$0, $$1.get().intValue(), $$2.get().intValue(), $$3.get().intValue());
    }

    public static Dynamic<?> fixInlineBlockPos(Dynamic<?> $$0, String $$1, String $$2, String $$3, String $$4) {
        Optional<Number> $$5 = $$0.get($$1).asNumber().result();
        Optional<Number> $$6 = $$0.get($$2).asNumber().result();
        Optional<Number> $$7 = $$0.get($$3).asNumber().result();
        if ($$5.isEmpty() || $$6.isEmpty() || $$7.isEmpty()) {
            return $$0;
        }
        return $$0.remove($$1).remove($$2).remove($$3).set($$4, createBlockPos($$0, $$5.get().intValue(), $$6.get().intValue(), $$7.get().intValue()));
    }

    public static Dynamic<?> createBlockPos(Dynamic<?> $$0, int $$1, int $$2, int $$3) {
        return $$0.createIntList(IntStream.of($$1, $$2, $$3));
    }

    public static <T, R> Typed<R> cast(Type<R> $$0, Typed<T> $$1) {
        return new Typed<>($$0, $$1.getOps(), $$1.getValue());
    }

    public static <T> Typed<T> cast(Type<T> $$0, Object $$1, DynamicOps<?> $$2) {
        return new Typed<>($$0, $$2, $$1);
    }

    public static Type<?> patchSubType(Type<?> $$0, Type<?> $$1, Type<?> $$2) {
        return $$0.all(typePatcher($$1, $$2), true, false).view().newType();
    }

    private static <A, B> TypeRewriteRule typePatcher(Type<A> $$0, Type<B> $$1) {
        RewriteResult<A, B> $$2 = RewriteResult.create(View.create("Patcher", $$0, $$1, $$02 -> {
            return $$02 -> {
                throw new UnsupportedOperationException();
            };
        }), new BitSet());
        return TypeRewriteRule.everywhere(TypeRewriteRule.ifSame($$0, $$2), PointFreeRule.nop(), true, true);
    }

    @SafeVarargs
    public static <T> Function<Typed<?>, Typed<?>> chainAllFilters(Function<Typed<?>, Typed<?>>... $$0) {
        return $$1 -> {
            for (Function function : $$0) {
                $$1 = (Typed) function.apply($$1);
            }
            return $$1;
        };
    }

    public static Dynamic<?> blockState(String $$0, Map<String, String> $$1) {
        Dynamic<Tag> $$2 = new Dynamic<>(NbtOps.INSTANCE, new CompoundTag());
        Dynamic<Tag> $$3 = $$2.set(StateHolder.NAME_TAG, $$2.createString($$0));
        if (!$$1.isEmpty()) {
            $$3 = $$3.set(StateHolder.PROPERTIES_TAG, $$2.createMap((Map) $$1.entrySet().stream().collect(Collectors.toMap($$12 -> {
                return $$2.createString((String) $$12.getKey());
            }, $$13 -> {
                return $$2.createString((String) $$13.getValue());
            }))));
        }
        return $$3;
    }

    public static Dynamic<?> blockState(String $$0) {
        return blockState($$0, Map.of());
    }

    public static Dynamic<?> fixStringField(Dynamic<?> $$0, String $$1, UnaryOperator<String> $$2) {
        return $$0.update($$1, $$22 -> {
            DataResult map = $$22.asString().map($$2);
            Objects.requireNonNull($$0);
            return (Dynamic) DataFixUtils.orElse(map.map($$0::createString).result(), $$22);
        });
    }

    public static String dyeColorIdToName(int $$0) {
        switch ($$0) {
            case 1:
                return "orange";
            case 2:
                return "magenta";
            case 3:
                return "light_blue";
            case 4:
                return "yellow";
            case 5:
                return "lime";
            case 6:
                return "pink";
            case 7:
                return "gray";
            case 8:
                return "light_gray";
            case 9:
                return "cyan";
            case 10:
                return "purple";
            case 11:
                return "blue";
            case 12:
                return "brown";
            case 13:
                return "green";
            case 14:
                return "red";
            case 15:
                return "black";
            default:
                return "white";
        }
    }

    public static <T> Typed<?> readAndSet(Typed<?> $$0, OpticFinder<T> $$1, Dynamic<?> $$2) {
        return $$0.set($$1, Util.readTypedOrThrow($$1.type(), $$2, true));
    }
}
