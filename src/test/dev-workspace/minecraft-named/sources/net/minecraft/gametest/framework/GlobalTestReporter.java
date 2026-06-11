package net.minecraft.gametest.framework;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GlobalTestReporter.class */
public class GlobalTestReporter {
    private static TestReporter DELEGATE = new LogTestReporter();

    public static void replaceWith(TestReporter $$0) {
        DELEGATE = $$0;
    }

    public static void onTestFailed(GameTestInfo $$0) {
        DELEGATE.onTestFailed($$0);
    }

    public static void onTestSuccess(GameTestInfo $$0) {
        DELEGATE.onTestSuccess($$0);
    }

    public static void finish() {
        DELEGATE.finish();
    }
}
