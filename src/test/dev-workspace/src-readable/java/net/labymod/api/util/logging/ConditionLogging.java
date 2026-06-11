package net.labymod.api.util.logging;

import java.util.function.BooleanSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/logging/ConditionLogging.class */
public class ConditionLogging implements Logging {
    private final Logging delegate;
    private final BooleanSupplier condition;

    public ConditionLogging(Logging delegate, BooleanSupplier condition) {
        this.delegate = delegate;
        this.condition = condition;
    }

    @Override // net.labymod.api.util.logging.Logging
    public void info(CharSequence message, Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.info(message, throwable);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void info(CharSequence message, Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.info(message, arguments);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void warn(CharSequence message, Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.warn(message, throwable);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void warn(CharSequence message, Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.warn(message, arguments);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void error(CharSequence message, Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.error(message, throwable);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void error(CharSequence message, Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.error(message, arguments);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void debug(CharSequence message, Throwable throwable) {
        if (this.condition.getAsBoolean()) {
            this.delegate.debug(message, throwable);
        }
    }

    @Override // net.labymod.api.util.logging.Logging
    public void debug(CharSequence message, Object... arguments) {
        if (this.condition.getAsBoolean()) {
            this.delegate.debug(message, arguments);
        }
    }
}
