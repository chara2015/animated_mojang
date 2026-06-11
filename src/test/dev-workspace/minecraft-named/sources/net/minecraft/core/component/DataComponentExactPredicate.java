package net.minecraft.core.component;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentExactPredicate.class */
public final class DataComponentExactPredicate implements Predicate<DataComponentGetter> {
    public static final Codec<DataComponentExactPredicate> CODEC = DataComponentType.VALUE_MAP_CODEC.xmap($$0 -> {
        return new DataComponentExactPredicate((List) $$0.entrySet().stream().map(TypedDataComponent::fromEntryUnchecked).collect(Collectors.toList()));
    }, $$02 -> {
        return (Map) $$02.expectedComponents.stream().filter($$02 -> {
            return !$$02.type().isTransient();
        }).collect(Collectors.toMap((v0) -> {
            return v0.type();
        }, (v0) -> {
            return v0.value();
        }));
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, DataComponentExactPredicate> STREAM_CODEC = TypedDataComponent.STREAM_CODEC.apply(ByteBufCodecs.list()).map(DataComponentExactPredicate::new, $$0 -> {
        return $$0.expectedComponents;
    });
    public static final DataComponentExactPredicate EMPTY = new DataComponentExactPredicate(List.of());
    private final List<TypedDataComponent<?>> expectedComponents;

    DataComponentExactPredicate(List<TypedDataComponent<?>> $$0) {
        this.expectedComponents = $$0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static <T> DataComponentExactPredicate expect(DataComponentType<T> $$0, T $$1) {
        return new DataComponentExactPredicate(List.of(new TypedDataComponent($$0, $$1)));
    }

    public static DataComponentExactPredicate allOf(DataComponentMap $$0) {
        return new DataComponentExactPredicate(ImmutableList.copyOf($$0));
    }

    public static DataComponentExactPredicate someOf(DataComponentMap $$0, DataComponentType<?>... $$1) {
        Builder $$2 = new Builder();
        for (DataComponentType<?> $$3 : $$1) {
            TypedDataComponent<?> $$4 = $$0.getTyped($$3);
            if ($$4 != null) {
                $$2.expect($$4);
            }
        }
        return $$2.build();
    }

    public boolean isEmpty() {
        return this.expectedComponents.isEmpty();
    }

    public boolean equals(Object $$0) {
        if ($$0 instanceof DataComponentExactPredicate) {
            DataComponentExactPredicate $$1 = (DataComponentExactPredicate) $$0;
            if (this.expectedComponents.equals($$1.expectedComponents)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.expectedComponents.hashCode();
    }

    public String toString() {
        return this.expectedComponents.toString();
    }

    @Override // java.util.function.Predicate
    public boolean test(DataComponentGetter $$0) {
        for (TypedDataComponent<?> $$1 : this.expectedComponents) {
            Object $$2 = $$0.get($$1.type());
            if (!Objects.equals($$1.value(), $$2)) {
                return false;
            }
        }
        return true;
    }

    public boolean alwaysMatches() {
        return this.expectedComponents.isEmpty();
    }

    public DataComponentPatch asPatch() {
        DataComponentPatch.Builder $$0 = DataComponentPatch.builder();
        for (TypedDataComponent<?> $$1 : this.expectedComponents) {
            $$0.set($$1);
        }
        return $$0.build();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/DataComponentExactPredicate$Builder.class */
    public static class Builder {
        private final List<TypedDataComponent<?>> expectedComponents = new ArrayList();

        Builder() {
        }

        public <T> Builder expect(TypedDataComponent<T> $$0) {
            return expect($$0.type(), $$0.value());
        }

        public <T> Builder expect(DataComponentType<? super T> $$0, T $$1) {
            for (TypedDataComponent<?> $$2 : this.expectedComponents) {
                if ($$2.type() == $$0) {
                    throw new IllegalArgumentException("Predicate already has component of type: '" + String.valueOf($$0) + "'");
                }
            }
            this.expectedComponents.add(new TypedDataComponent<>($$0, $$1));
            return this;
        }

        public DataComponentExactPredicate build() {
            return new DataComponentExactPredicate(List.copyOf(this.expectedComponents));
        }
    }
}
