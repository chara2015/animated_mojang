package net.minecraft.server;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/RunningOnDifferentThreadException.class */
public final class RunningOnDifferentThreadException extends RuntimeException {
    public static final RunningOnDifferentThreadException RUNNING_ON_DIFFERENT_THREAD = new RunningOnDifferentThreadException();

    private RunningOnDifferentThreadException() {
        setStackTrace(new StackTraceElement[0]);
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }
}
