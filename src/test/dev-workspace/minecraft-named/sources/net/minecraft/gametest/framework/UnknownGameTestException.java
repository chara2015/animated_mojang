package net.minecraft.gametest.framework;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/UnknownGameTestException.class */
public class UnknownGameTestException extends GameTestException {
    private final Throwable reason;

    public UnknownGameTestException(Throwable $$0) {
        super($$0.getMessage());
        this.reason = $$0;
    }

    @Override // net.minecraft.gametest.framework.GameTestException
    public Component getDescription() {
        return Component.translatable("test.error.unknown", this.reason.getMessage());
    }
}
