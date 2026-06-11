package net.labymod.api.client.crash;

import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/crash/GameCrashReport.class */
public interface GameCrashReport {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/crash/GameCrashReport$Category.class */
    public interface Category {
        void setDetail(String str, Supplier<String> supplier);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/crash/GameCrashReport$Factory.class */
    @Referenceable
    public interface Factory {
        GameCrashReport create(@NotNull String str, Throwable th);
    }

    Category addCategory(String str);

    <T> T crashReport();

    static GameCrashReport forThrowable(@NotNull String title, Throwable throwable) {
        return Laby.references().gameCrashReportFactory().create(title, throwable);
    }

    default void crashGame() {
        Laby.labyAPI().minecraft().crashGame(this);
    }
}
