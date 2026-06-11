package net.minecraft.util;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.ListBuilder;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/AbstractListBuilder.class */
abstract class AbstractListBuilder<T, B> implements ListBuilder<T> {
    private final DynamicOps<T> ops;
    protected DataResult<B> builder = DataResult.success(initBuilder(), Lifecycle.stable());

    protected abstract B initBuilder();

    protected abstract B append(B b, T t);

    protected abstract DataResult<T> build(B b, T t);

    protected AbstractListBuilder(DynamicOps<T> $$0) {
        this.ops = $$0;
    }

    public DynamicOps<T> ops() {
        return this.ops;
    }

    public ListBuilder<T> add(T $$0) {
        this.builder = this.builder.map(obj -> {
            return append(obj, $$0);
        });
        return this;
    }

    public ListBuilder<T> add(DataResult<T> $$0) {
        this.builder = this.builder.apply2stable(this::append, $$0);
        return this;
    }

    public ListBuilder<T> withErrorsFrom(DataResult<?> $$0) {
        this.builder = this.builder.flatMap($$1 -> {
            return $$0.map($$1 -> {
                return $$1;
            });
        });
        return this;
    }

    public ListBuilder<T> mapError(UnaryOperator<String> $$0) {
        this.builder = this.builder.mapError($$0);
        return this;
    }

    public DataResult<T> build(T $$0) {
        DataResult<T> $$1 = this.builder.flatMap(obj -> {
            return build(obj, $$0);
        });
        this.builder = DataResult.success(initBuilder(), Lifecycle.stable());
        return $$1;
    }
}
