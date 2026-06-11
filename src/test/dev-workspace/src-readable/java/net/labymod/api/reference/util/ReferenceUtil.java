package net.labymod.api.reference.util;

import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/util/ReferenceUtil.class */
public final class ReferenceUtil {
    private static Consumer<Throwable> throwableConsumer = (v0) -> {
        v0.printStackTrace();
    };

    private ReferenceUtil() {
    }

    public static void setThrowableConsumer(Consumer<Throwable> throwableConsumer2) {
        throwableConsumer = throwableConsumer2;
    }

    public static void onError(Throwable throwable) {
        Consumer<Throwable> consumer = throwableConsumer;
        if (consumer == null) {
            return;
        }
        consumer.accept(throwable);
    }
}
