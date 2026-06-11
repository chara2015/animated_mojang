package net.minecraft;

import java.lang.Thread;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/DefaultUncaughtExceptionHandlerWithName.class */
public class DefaultUncaughtExceptionHandlerWithName implements Thread.UncaughtExceptionHandler {
    private final Logger logger;

    public DefaultUncaughtExceptionHandlerWithName(Logger $$0) {
        this.logger = $$0;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread $$0, Throwable $$1) {
        this.logger.error("Caught previously unhandled exception :");
        this.logger.error($$0.getName(), $$1);
    }
}
