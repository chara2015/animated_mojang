package net.minecraft.gametest.framework;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestAssertException.class */
public class GameTestAssertException extends GameTestException {
    protected final Component message;
    protected final int tick;

    public GameTestAssertException(Component $$0, int $$1) {
        super($$0.getString());
        this.message = $$0;
        this.tick = $$1;
    }

    @Override // net.minecraft.gametest.framework.GameTestException
    public Component getDescription() {
        return Component.translatable("test.error.tick", this.message, Integer.valueOf(this.tick));
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getDescription().getString();
    }
}
