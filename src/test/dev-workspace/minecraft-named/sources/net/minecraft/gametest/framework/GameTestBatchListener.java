package net.minecraft.gametest.framework;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestBatchListener.class */
public interface GameTestBatchListener {
    void testBatchStarting(GameTestBatch gameTestBatch);

    void testBatchFinished(GameTestBatch gameTestBatch);
}
