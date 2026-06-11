package com.mojang.realmsclient.exception;

import java.lang.Thread;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/exception/RealmsDefaultUncaughtExceptionHandler.class */
public class RealmsDefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final Logger logger;

    public RealmsDefaultUncaughtExceptionHandler(Logger $$0) {
        this.logger = $$0;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread $$0, Throwable $$1) {
        this.logger.error("Caught previously unhandled exception", $$1);
    }
}
