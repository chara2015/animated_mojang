package net.minecraft.gametest.framework;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestException.class */
public abstract class GameTestException extends RuntimeException {
    public abstract Component getDescription();

    public GameTestException(String $$0) {
        super($$0);
    }
}
