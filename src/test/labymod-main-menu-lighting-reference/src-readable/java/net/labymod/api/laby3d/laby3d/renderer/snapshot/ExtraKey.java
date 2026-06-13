package net.labymod.api.laby3d.renderer.snapshot;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.Laby;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/ExtraKey.class */
public interface ExtraKey<T> {
    String namespace();

    String name();

    Class<T> type();

    static <T> ExtraKey<T> of(String name, Class<T> type) {
        Reflection.validateStaticConstructorInvocation(1, () -> {
            return "ExtraKey.of(name, type) may only be used for constants as this is very performance intensive";
        });
        Class<?> callerClass = Reflection.getCallerClass();
        String namespace = Laby.labyAPI().getNamespace(callerClass);
        String key = namespace + ":" + name;
        return (ExtraKey) ExtraKeyCache.CACHE.computeIfAbsent(key, k -> {
            return new Simple(namespace, name, type);
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple.class */
    public static final class Simple<T> extends Record implements ExtraKey<T> {
        private final String namespace;
        private final String name;
        private final Class<T> type;

        public Simple(String namespace, String name, Class<T> type) {
            this.namespace = namespace;
            this.name = name;
            this.type = type;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Simple.class), Simple.class, "namespace;name;type", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->type:Ljava/lang/Class;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Simple.class, Object.class), Simple.class, "namespace;name;type", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->namespace:Ljava/lang/String;", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/laby3d/renderer/snapshot/ExtraKey$Simple;->type:Ljava/lang/Class;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.ExtraKey
        public String namespace() {
            return this.namespace;
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.ExtraKey
        public String name() {
            return this.name;
        }

        @Override // net.labymod.api.laby3d.renderer.snapshot.ExtraKey
        public Class<T> type() {
            return this.type;
        }

        @Override // java.lang.Record
        @NotNull
        public String toString() {
            return this.namespace + ":" + this.name;
        }
    }
}
