package net.labymod.api.util.logging;

import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/logging/Logging.class */
public interface Logging {
    void info(CharSequence charSequence, Throwable th);

    void info(CharSequence charSequence, Object... objArr);

    void warn(CharSequence charSequence, Throwable th);

    void warn(CharSequence charSequence, Object... objArr);

    void error(CharSequence charSequence, Throwable th);

    void error(CharSequence charSequence, Object... objArr);

    void debug(CharSequence charSequence, Throwable th);

    void debug(CharSequence charSequence, Object... objArr);

    static Logging getLogger() {
        return create(Reflection.getCallerClass());
    }

    static Logging create(@NotNull String name) {
        return Laby.references().loggingFactory().create(name);
    }

    static Logging create(@NotNull Class<?> cls) {
        return Laby.references().loggingFactory().create(cls);
    }

    static Logging create(@NotNull String name, boolean condition) {
        return create(name, () -> {
            return condition;
        });
    }

    static Logging create(@NotNull String name, BooleanSupplier condition) {
        return Laby.references().loggingFactory().create(name, condition);
    }

    static Logging create(@NotNull Class<?> cls, boolean condition) {
        return create(cls, () -> {
            return condition;
        });
    }

    static Logging create(@NotNull Class<?> cls, BooleanSupplier condition) {
        return Laby.references().loggingFactory().create(cls, condition);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/logging/Logging$Factory.class */
    @Referenceable
    public interface Factory {
        @NotNull
        Logging create(String str);

        @NotNull
        Logging create(Class<?> cls);

        Logging create(String str, BooleanSupplier booleanSupplier);

        Logging create(Class<?> cls, BooleanSupplier booleanSupplier);

        default Logging create(String name, boolean condition) {
            return create(name, () -> {
                return condition;
            });
        }

        default Logging create(Class<?> cls, boolean condition) {
            return create(cls, () -> {
                return condition;
            });
        }
    }
}
