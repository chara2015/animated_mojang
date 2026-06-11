package net.labymod.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/Service.class */
public abstract class Service {
    protected static final Logging LOGGER = Logging.getLogger();
    private boolean completed;

    @Deprecated(forRemoval = true, since = "4.1.12")
    protected Logging logging = LOGGER;
    private final List<Runnable> tasks = new ArrayList();

    protected Service() {
    }

    protected void prepare() {
    }

    public final void prepareSynchronously() {
        try {
            prepare();
            completed();
        } catch (Exception exception) {
            onServiceError(exception);
        }
    }

    public final void prepareAsynchronously() {
        LabyExecutors.executeBackgroundTask(this::prepareSynchronously);
    }

    public void onServiceError(Exception exception) {
        this.logging.error("An error occurred in the service {}", getClass().getName(), exception);
    }

    public void onServiceUnload() {
    }

    public final void unload() {
        onServiceUnload();
        this.completed = false;
    }

    public void onServiceCompleted() {
    }

    public final void completed() {
        onServiceCompleted();
        this.completed = true;
        for (Runnable task : this.tasks) {
            if (task != null) {
                task.run();
            }
        }
        this.tasks.clear();
    }

    public void executeServiceTask(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable must not be null");
        if (this.completed) {
            runnable.run();
        } else {
            this.tasks.add(runnable);
        }
    }

    public boolean isCompleted() {
        return this.completed;
    }
}
