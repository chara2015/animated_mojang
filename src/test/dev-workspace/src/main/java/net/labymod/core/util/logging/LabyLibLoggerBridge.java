package net.labymod.core.util.logging;

import net.laby.lib.core.log.Level;
import net.laby.lib.core.log.Logger;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/logging/LabyLibLoggerBridge.class */
final class LabyLibLoggerBridge implements Logger {
    private final String name;
    private final Logging delegate;

    LabyLibLoggerBridge(String name, Logging delegate) {
        this.name = name;
        this.delegate = delegate;
    }

    public String name() {
        return this.name;
    }

    public boolean isEnabled(Level level) {
        return true;
    }

    /* JADX INFO: renamed from: net.labymod.core.util.logging.LabyLibLoggerBridge$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/logging/LabyLibLoggerBridge$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$laby$lib$core$log$Level = new int[Level.values().length];

        static {
            try {
                $SwitchMap$net$laby$lib$core$log$Level[Level.TRACE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$laby$lib$core$log$Level[Level.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$laby$lib$core$log$Level[Level.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$laby$lib$core$log$Level[Level.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$laby$lib$core$log$Level[Level.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public void log(Level level, String message, Throwable throwable) {
        switch (AnonymousClass1.$SwitchMap$net$laby$lib$core$log$Level[level.ordinal()]) {
            case 1:
            case 2:
                if (throwable == null) {
                    this.delegate.debug(message, new Object[0]);
                } else {
                    this.delegate.debug(message, throwable);
                }
                break;
            case 3:
                if (throwable == null) {
                    this.delegate.info(message, new Object[0]);
                } else {
                    this.delegate.info(message, throwable);
                }
                break;
            case 4:
                if (throwable == null) {
                    this.delegate.warn(message, new Object[0]);
                } else {
                    this.delegate.warn(message, throwable);
                }
                break;
            case 5:
                if (throwable == null) {
                    this.delegate.error(message, new Object[0]);
                } else {
                    this.delegate.error(message, throwable);
                }
                break;
        }
    }
}
