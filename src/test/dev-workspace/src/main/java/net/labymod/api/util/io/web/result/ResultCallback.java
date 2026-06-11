package net.labymod.api.util.io.web.result;

import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/result/ResultCallback.class */
public interface ResultCallback<T> extends Consumer<Result<T>> {
    @Override // java.util.function.Consumer
    void accept(Result<T> result);

    default void acceptRaw(T value) {
        accept((Result) Result.ofNullable(value));
    }

    default void acceptException(@NotNull Exception e) {
        Result<T> result = Result.empty();
        result.setException(e);
        accept((Result) result);
    }
}
