package net.minecraft.gametest.framework;

import java.util.stream.Stream;
import net.minecraft.core.BlockPos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/TestPosFinder.class */
@FunctionalInterface
public interface TestPosFinder {
    Stream<BlockPos> findTestPos();
}
