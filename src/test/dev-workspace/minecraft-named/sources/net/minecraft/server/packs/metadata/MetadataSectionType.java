package net.minecraft.server.packs.metadata;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/metadata/MetadataSectionType.class */
public final class MetadataSectionType<T> extends Record {
    private final String name;
    private final Codec<T> codec;

    public MetadataSectionType(String $$0, Codec<T> $$1) {
        this.name = $$0;
        this.codec = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MetadataSectionType.class), MetadataSectionType.class, "name;codec", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MetadataSectionType.class), MetadataSectionType.class, "name;codec", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MetadataSectionType.class, Object.class), MetadataSectionType.class, "name;codec", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType;->codec:Lcom/mojang/serialization/Codec;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public Codec<T> codec() {
        return this.codec;
    }

    public WithValue<T> withValue(T $$0) {
        return new WithValue<>(this, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/metadata/MetadataSectionType$WithValue.class */
    public static final class WithValue<T> extends Record {
        private final MetadataSectionType<T> type;
        private final T value;

        public WithValue(MetadataSectionType<T> $$0, T $$1) {
            this.type = $$0;
            this.value = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WithValue.class), WithValue.class, "type;value", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->type:Lnet/minecraft/server/packs/metadata/MetadataSectionType;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WithValue.class), WithValue.class, "type;value", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->type:Lnet/minecraft/server/packs/metadata/MetadataSectionType;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WithValue.class, Object.class), WithValue.class, "type;value", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->type:Lnet/minecraft/server/packs/metadata/MetadataSectionType;", "FIELD:Lnet/minecraft/server/packs/metadata/MetadataSectionType$WithValue;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public MetadataSectionType<T> type() {
            return this.type;
        }

        public T value() {
            return this.value;
        }

        public <U> Optional<U> unwrapToType(MetadataSectionType<U> $$0) {
            return $$0 == this.type ? Optional.of(this.value) : Optional.empty();
        }
    }
}
