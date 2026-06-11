package net.labymod.core.laby3d.debug;

import java.lang.StackWalker;
import java.util.List;
import java.util.Queue;
import net.labymod.api.util.collection.EvictingQueue;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.debugger.DebugMessage;
import net.labymod.laby3d.api.debugger.DebugMessageCallback;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/debug/LabyModDebugMessageCallback.class */
public class LabyModDebugMessageCallback implements DebugMessageCallback {
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final Logging LOGGER = Logging.getLogger();
    private final Queue<LogEntry> logQueue = new EvictingQueue(10);
    private LogEntry lastEntry;

    public void onMessage(DebugMessage message) {
        synchronized (this.logQueue) {
            LogEntry logEntry = this.lastEntry;
            if (logEntry != null && logEntry.isSame(message)) {
                logEntry.count++;
            } else {
                LogEntry logEntry2 = new LogEntry(message);
                this.logQueue.add(logEntry2);
                this.lastEntry = logEntry2;
                LOGGER.warn("Debug message: {}", logEntry2.message);
                printStackTrace();
            }
        }
    }

    public List<String> getLastOpenGlDebugMessages() {
        List<String> messages;
        synchronized (this.logQueue) {
            messages = Lists.newArrayListWithCapacity(this.logQueue.size());
            for (LogEntry logEntry : this.logQueue) {
                messages.add(String.valueOf(logEntry.message) + " x " + logEntry.count);
            }
        }
        return messages;
    }

    private void printStackTrace() {
        if (SystemProperties.RENDER_DEVICE_DEBUG_CONTEXT_SYNC.get().booleanValue() || SystemProperties.RENDER_DEVICE_USER_DEBUG.get().booleanValue()) {
            LOGGER.warn("Stack trace:", new Object[0]);
            WALKER.forEach(stackFrame -> {
                LOGGER.warn("\t{}", stackFrame.toString());
            });
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/debug/LabyModDebugMessageCallback$LogEntry.class */
    static class LogEntry {
        private final DebugMessage message;
        private int count = 1;

        public LogEntry(DebugMessage message) {
            this.message = message;
        }

        public boolean isSame(DebugMessage message) {
            return this.message.equals(message);
        }
    }
}
