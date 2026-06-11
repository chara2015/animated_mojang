package net.labymod.api.util.lang;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/lang/Runnables.class */
public final class Runnables {
    private static final Runnable EMPTY_RUNNABLE = () -> {
    };

    public static Runnable doNothing() {
        return EMPTY_RUNNABLE;
    }

    public static void runIfNotNull(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        runnable.run();
    }
}
