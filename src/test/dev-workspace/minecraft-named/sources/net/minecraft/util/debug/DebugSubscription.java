package net.minecraft.util.debug;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugSubscription.class */
public class DebugSubscription<T> {
    public static final int DOES_NOT_EXPIRE = 0;
    final StreamCodec<? super RegistryFriendlyByteBuf, T> valueStreamCodec;
    private final int expireAfterTicks;

    public DebugSubscription(StreamCodec<? super RegistryFriendlyByteBuf, T> $$0, int $$1) {
        this.valueStreamCodec = $$0;
        this.expireAfterTicks = $$1;
    }

    public DebugSubscription(StreamCodec<? super RegistryFriendlyByteBuf, T> $$0) {
        this($$0, 0);
    }

    public Update<T> packUpdate(T $$0) {
        return new Update<>(this, Optional.ofNullable($$0));
    }

    public Update<T> emptyUpdate() {
        return new Update<>(this, Optional.empty());
    }

    public Event<T> packEvent(T $$0) {
        return new Event<>(this, $$0);
    }

    public String toString() {
        return Util.getRegisteredName(BuiltInRegistries.DEBUG_SUBSCRIPTION, this);
    }

    public StreamCodec<? super RegistryFriendlyByteBuf, T> valueStreamCodec() {
        return this.valueStreamCodec;
    }

    public int expireAfterTicks() {
        return this.expireAfterTicks;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugSubscription$Update.class */
    public static final class Update<T> extends Record {
        private final DebugSubscription<T> subscription;
        private final Optional<T> value;
        public static final StreamCodec<RegistryFriendlyByteBuf, Update<?>> STREAM_CODEC = ByteBufCodecs.registry(Registries.DEBUG_SUBSCRIPTION).dispatch((v0) -> {
            return v0.subscription();
        }, Update::streamCodec);

        public Update(DebugSubscription<T> $$0, Optional<T> $$1) {
            this.subscription = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Update.class), Update.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->value:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Update.class), Update.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->value:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Update.class, Object.class), Update.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Update;->value:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DebugSubscription<T> subscription() {
            return this.subscription;
        }

        public Optional<T> value() {
            return this.value;
        }

        private static <T> StreamCodec<? super RegistryFriendlyByteBuf, Update<T>> streamCodec(DebugSubscription<T> $$0) {
            return ByteBufCodecs.optional((StreamCodec) Objects.requireNonNull($$0.valueStreamCodec)).map($$1 -> {
                return new Update($$0, $$1);
            }, (v0) -> {
                return v0.value();
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/debug/DebugSubscription$Event.class */
    public static final class Event<T> extends Record {
        private final DebugSubscription<T> subscription;
        private final T value;
        public static final StreamCodec<RegistryFriendlyByteBuf, Event<?>> STREAM_CODEC = ByteBufCodecs.registry(Registries.DEBUG_SUBSCRIPTION).dispatch((v0) -> {
            return v0.subscription();
        }, Event::streamCodec);

        public Event(DebugSubscription<T> $$0, T $$1) {
            this.subscription = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Event.class), Event.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Event.class), Event.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Event.class, Object.class), Event.class, "subscription;value", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->subscription:Lnet/minecraft/util/debug/DebugSubscription;", "FIELD:Lnet/minecraft/util/debug/DebugSubscription$Event;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public DebugSubscription<T> subscription() {
            return this.subscription;
        }

        public T value() {
            return this.value;
        }

        private static <T> StreamCodec<? super RegistryFriendlyByteBuf, Event<T>> streamCodec(DebugSubscription<T> $$0) {
            return ((StreamCodec) Objects.requireNonNull($$0.valueStreamCodec)).map($$1 -> {
                return new Event($$0, $$1);
            }, (v0) -> {
                return v0.value();
            });
        }
    }
}
