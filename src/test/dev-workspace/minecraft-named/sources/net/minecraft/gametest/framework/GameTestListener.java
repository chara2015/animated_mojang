package net.minecraft.gametest.framework;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestListener.class */
public interface GameTestListener {
    void testStructureLoaded(GameTestInfo gameTestInfo);

    void testPassed(GameTestInfo gameTestInfo, GameTestRunner gameTestRunner);

    void testFailed(GameTestInfo gameTestInfo, GameTestRunner gameTestRunner);

    void testAddedForRerun(GameTestInfo gameTestInfo, GameTestInfo gameTestInfo2, GameTestRunner gameTestRunner);
}
