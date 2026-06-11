package net.labymod.api.util.concurrent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/concurrent/Completable.class */
public interface Completable<T> {
    void addCompletableListener(Runnable runnable);

    boolean isLoading();

    void startLoading();

    void stopLoading();

    void stopLoadingOnError();

    T getCompleted();

    boolean hasResult();

    boolean hasError();

    void reset();

    void executeCompletableListeners(T t);

    void updateCompletable(T t);
}
