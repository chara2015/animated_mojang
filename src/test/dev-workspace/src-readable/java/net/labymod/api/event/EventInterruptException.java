package net.labymod.api.event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/EventInterruptException.class */
public class EventInterruptException extends RuntimeException {
    public static final EventInterruptException INSTANCE = new EventInterruptException();

    private EventInterruptException() {
        super("Event invocation has been interrupted by Event.interruptUpcomingListeners();");
    }

    @Override // java.lang.Throwable
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
