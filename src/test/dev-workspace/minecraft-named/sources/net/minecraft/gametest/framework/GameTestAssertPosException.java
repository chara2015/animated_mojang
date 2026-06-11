package net.minecraft.gametest.framework;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestAssertPosException.class */
public class GameTestAssertPosException extends GameTestAssertException {
    private final BlockPos absolutePos;
    private final BlockPos relativePos;

    public GameTestAssertPosException(Component $$0, BlockPos $$1, BlockPos $$2, int $$3) {
        super($$0, $$3);
        this.absolutePos = $$1;
        this.relativePos = $$2;
    }

    @Override // net.minecraft.gametest.framework.GameTestAssertException, net.minecraft.gametest.framework.GameTestException
    public Component getDescription() {
        return Component.translatable("test.error.position", this.message, Integer.valueOf(this.absolutePos.getX()), Integer.valueOf(this.absolutePos.getY()), Integer.valueOf(this.absolutePos.getZ()), Integer.valueOf(this.relativePos.getX()), Integer.valueOf(this.relativePos.getY()), Integer.valueOf(this.relativePos.getZ()), Integer.valueOf(this.tick));
    }

    public Component getMessageToShowAtBlock() {
        return this.message;
    }

    public BlockPos getRelativePos() {
        return this.relativePos;
    }

    public BlockPos getAbsolutePos() {
        return this.absolutePos;
    }
}
