package net.labymod.api.util.concurrent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/AbstractCompletable.class */
public abstract class AbstractCompletable<T> implements Completable<T> {
    private final T fallback;
    private final Collection<Runnable> completableListeners;
    private boolean loading;
    private boolean error;
    private T completed;

    public AbstractCompletable(T fallback) {
        this.fallback = fallback;
        this.completableListeners = new HashSet();
        this.loading = true;
    }

    public AbstractCompletable(T completed, boolean isCompleted) {
        this.fallback = completed;
        this.completed = completed;
        this.completableListeners = new HashSet();
        this.loading = !isCompleted;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void addCompletableListener(Runnable listener) {
        if (this.loading) {
            this.completableListeners.add(listener);
        } else {
            listener.run();
        }
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void startLoading() {
        this.loading = true;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void stopLoading() {
        this.loading = false;
        this.completableListeners.clear();
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void stopLoadingOnError() {
        this.error = true;
        this.loading = false;
        for (Runnable completableListener : this.completableListeners) {
            completableListener.run();
        }
        this.completableListeners.clear();
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public boolean isLoading() {
        return this.loading;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public T getCompleted() {
        return this.completed == null ? this.fallback : this.completed;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public boolean hasResult() {
        return this.completed != null;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public boolean hasError() {
        return this.error;
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void reset() {
        this.loading = true;
        this.completed = null;
        this.completableListeners.clear();
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void executeCompletableListeners(T completed) {
        if (Objects.equals(this.fallback, completed)) {
            return;
        }
        this.completed = completed;
        this.loading = false;
        for (Runnable completableListener : this.completableListeners) {
            completableListener.run();
        }
        stopLoading();
    }

    @Override // net.labymod.api.util.concurrent.Completable
    public void updateCompletable(T completed) {
        if (this.loading) {
            executeCompletableListeners(completed);
        } else if (!Objects.equals(this.completed, completed)) {
            this.completed = completed;
        }
    }
}
