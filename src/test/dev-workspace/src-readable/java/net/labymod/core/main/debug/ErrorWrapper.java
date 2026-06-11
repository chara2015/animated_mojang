package net.labymod.core.main.debug;

import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/ErrorWrapper.class */
public final class ErrorWrapper {
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger("Error Wrapper");

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/ErrorWrapper$ErrorWatcher.class */
    public interface ErrorWatcher {
        void onError();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/debug/ErrorWrapper$ReturnStatement.class */
    @FunctionalInterface
    public interface ReturnStatement<T> {
        T get();
    }

    public static void wrap(Runnable executor, Supplier<String> message) {
        wrap(executor, message, (ErrorWatcher) null);
    }

    public static void wrap(Runnable executor, Supplier<String> message, ErrorWatcher errorWatcher) {
        try {
            executor.run();
        } catch (Throwable throwable) {
            LOGGER.error(message.get(), throwable);
            if (errorWatcher != null) {
                errorWatcher.onError();
            }
        }
    }

    public static <T> T wrap(ReturnStatement<T> returnStatement, T t, Supplier<String> supplier) {
        return (T) wrap(returnStatement, t, supplier, null);
    }

    public static <T> T wrap(ReturnStatement<T> statement, T errorFallback, Supplier<String> message, ErrorWatcher errorWatcher) {
        try {
            return statement.get();
        } catch (Throwable throwable) {
            LOGGER.error(message.get(), throwable);
            if (errorWatcher != null) {
                errorWatcher.onError();
            }
            return errorFallback;
        }
    }
}
