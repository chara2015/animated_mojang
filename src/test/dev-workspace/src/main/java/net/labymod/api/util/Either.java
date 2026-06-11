package net.labymod.api.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Either.class */
public interface Either<L, R> {
    Optional<L> left();

    Optional<R> right();

    <T> T map(Function<? super L, ? extends T> function, Function<? super R, ? extends T> function2);

    Either<L, R> ifLeft(Consumer<? super L> consumer);

    Either<L, R> ifRight(Consumer<? super R> consumer);

    static <L, R> Either<L, R> left(L value) {
        return new Left(value);
    }

    static <L, R> Either<L, R> right(R value) {
        return new Right(value);
    }

    default <T> Either<T, R> mapLeft(Function<? super L, ? extends T> l) {
        return (Either) map(t -> {
            return left(l.apply(t));
        }, Either::right);
    }

    default <T> Either<L, T> mapRight(Function<? super R, ? extends T> r) {
        return (Either) map(Either::left, t -> {
            return right(r.apply(t));
        });
    }

    default Either<R, L> swap() {
        return (Either) map(Either::right, Either::left);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Either$Left.class */
    public static class Left<L, R> implements Either<L, R> {
        private final L value;

        public Left(L value) {
            this.value = value;
        }

        @Override // net.labymod.api.util.Either
        public Optional<L> left() {
            return Optional.of(this.value);
        }

        @Override // net.labymod.api.util.Either
        public Optional<R> right() {
            return Optional.empty();
        }

        @Override // net.labymod.api.util.Either
        public <T> T map(Function<? super L, ? extends T> function, Function<? super R, ? extends T> function2) {
            return function.apply(this.value);
        }

        @Override // net.labymod.api.util.Either
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            consumer.accept(this.value);
            return this;
        }

        @Override // net.labymod.api.util.Either
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            return this;
        }

        public String toString() {
            return "Left[" + String.valueOf(this.value) + "]";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Left<?, ?> left = (Left) obj;
            return Objects.equals(this.value, left.value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Either$Right.class */
    public static class Right<L, R> implements Either<L, R> {
        private final R value;

        public Right(R value) {
            this.value = value;
        }

        @Override // net.labymod.api.util.Either
        public Optional<L> left() {
            return Optional.empty();
        }

        @Override // net.labymod.api.util.Either
        public Optional<R> right() {
            return Optional.of(this.value);
        }

        @Override // net.labymod.api.util.Either
        public <T> T map(Function<? super L, ? extends T> function, Function<? super R, ? extends T> function2) {
            return function2.apply(this.value);
        }

        @Override // net.labymod.api.util.Either
        public Either<L, R> ifLeft(Consumer<? super L> consumer) {
            return this;
        }

        @Override // net.labymod.api.util.Either
        public Either<L, R> ifRight(Consumer<? super R> consumer) {
            consumer.accept(this.value);
            return this;
        }

        public String toString() {
            return "Right[" + String.valueOf(this.value) + "]";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Right<?, ?> right = (Right) obj;
            return Objects.equals(this.value, right.value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }
    }
}
