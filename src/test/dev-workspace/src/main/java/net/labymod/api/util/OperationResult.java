package net.labymod.api.util;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/OperationResult.class */
public interface OperationResult<R> {
    boolean isSuccess();

    Optional<R> result();

    Optional<R> partial();

    Optional<String> message();

    <T> OperationResult<T> map(Function<? super R, ? extends T> function);

    <T> OperationResult<T> flatMap(Function<? super R, OperationResult<T>> function);

    <T> T mapOrElse(Function<? super R, ? extends T> function, Function<? super Error<R>, ? extends T> function2);

    OperationResult<R> ifSuccess(Consumer<? super R> consumer);

    OperationResult<R> ifError(Consumer<String> consumer);

    static <R> OperationResult<R> success(R value) {
        return new Success(value);
    }

    static <R> OperationResult<R> error(String message) {
        return new Error(() -> {
            return message;
        }, Optional.empty());
    }

    static <R> OperationResult<R> error(Supplier<String> message) {
        return new Error(message, Optional.empty());
    }

    static <R> OperationResult<R> error(String message, R partial) {
        return new Error(() -> {
            return message;
        }, Optional.of(partial));
    }

    static <R> OperationResult<R> error(Supplier<String> message, R partial) {
        return new Error(message, Optional.of(partial));
    }

    default boolean isError() {
        return !isSuccess();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/OperationResult$Success.class */
    public static final class Success<R> extends Record implements OperationResult<R> {
        private final R value;

        public Success(R value) {
            this.value = value;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Success.class), Success.class, "value", "FIELD:Lnet/labymod/api/util/OperationResult$Success;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Success.class, Object.class), Success.class, "value", "FIELD:Lnet/labymod/api/util/OperationResult$Success;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public R value() {
            return this.value;
        }

        @Override // net.labymod.api.util.OperationResult
        public boolean isSuccess() {
            return true;
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<R> result() {
            return Optional.of(this.value);
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<R> partial() {
            return Optional.of(this.value);
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<String> message() {
            return Optional.empty();
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> OperationResult<T> map(Function<? super R, ? extends T> function) {
            return OperationResult.success(function.apply(this.value));
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> OperationResult<T> flatMap(Function<? super R, OperationResult<T>> function) {
            return function.apply(this.value);
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> T mapOrElse(Function<? super R, ? extends T> function, Function<? super Error<R>, ? extends T> function2) {
            return function.apply(this.value);
        }

        @Override // net.labymod.api.util.OperationResult
        public OperationResult<R> ifSuccess(Consumer<? super R> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override // net.labymod.api.util.OperationResult
        public OperationResult<R> ifError(Consumer<String> consumer) {
            return this;
        }

        @Override // java.lang.Record
        @NotNull
        public String toString() {
            return "Success(" + String.valueOf(this.value) + ")";
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/OperationResult$Error.class */
    public static final class Error<R> extends Record implements OperationResult<R> {
        private final Supplier<String> messageSupplier;
        private final Optional<R> partial;

        public Error(Supplier<String> messageSupplier, Optional<R> partial) {
            this.messageSupplier = messageSupplier;
            this.partial = partial;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Error.class), Error.class, "messageSupplier;partial", "FIELD:Lnet/labymod/api/util/OperationResult$Error;->messageSupplier:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/api/util/OperationResult$Error;->partial:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Error.class, Object.class), Error.class, "messageSupplier;partial", "FIELD:Lnet/labymod/api/util/OperationResult$Error;->messageSupplier:Ljava/util/function/Supplier;", "FIELD:Lnet/labymod/api/util/OperationResult$Error;->partial:Ljava/util/Optional;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Supplier<String> messageSupplier() {
            return this.messageSupplier;
        }

        @Override // net.labymod.api.util.OperationResult
        public boolean isSuccess() {
            return false;
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<R> result() {
            return Optional.empty();
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<R> partial() {
            return this.partial;
        }

        @Override // net.labymod.api.util.OperationResult
        public Optional<String> message() {
            return Optional.of(this.messageSupplier.get());
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> OperationResult<T> map(Function<? super R, ? extends T> fn) {
            return new Error(this.messageSupplier, this.partial.map(fn));
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> OperationResult<T> flatMap(Function<? super R, OperationResult<T>> fn) {
            return (OperationResult) this.partial.map(fn).orElseGet(() -> {
                return new Error(this.messageSupplier, Optional.empty());
            });
        }

        @Override // net.labymod.api.util.OperationResult
        public <T> T mapOrElse(Function<? super R, ? extends T> successFunction, Function<? super Error<R>, ? extends T> errorFunction) {
            return errorFunction.apply(this);
        }

        @Override // net.labymod.api.util.OperationResult
        public OperationResult<R> ifSuccess(Consumer<? super R> consumer) {
            return this;
        }

        @Override // net.labymod.api.util.OperationResult
        public OperationResult<R> ifError(Consumer<String> consumer) {
            consumer.accept(this.messageSupplier.get());
            return this;
        }

        @Override // java.lang.Record
        @NotNull
        public String toString() {
            return "Error(" + this.messageSupplier.get() + ((String) this.partial.map(p -> {
                return ", partial=" + String.valueOf(p);
            }).orElse("")) + ")";
        }
    }
}
