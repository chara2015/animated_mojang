package net.minecraft.gametest.framework;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/TestReporter.class */
public interface TestReporter {
    void onTestFailed(GameTestInfo gameTestInfo);

    void onTestSuccess(GameTestInfo gameTestInfo);

    default void finish() {
    }
}
