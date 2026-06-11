package net.minecraft.gametest.framework;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestTimeoutException.class */
public class GameTestTimeoutException extends GameTestException {
    protected final Component message;

    public GameTestTimeoutException(Component $$0) {
        super($$0.getString());
        this.message = $$0;
    }

    @Override // net.minecraft.gametest.framework.GameTestException
    public Component getDescription() {
        return this.message;
    }
}
