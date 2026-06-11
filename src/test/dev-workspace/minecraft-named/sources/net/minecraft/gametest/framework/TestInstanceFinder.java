package net.minecraft.gametest.framework;

import java.util.stream.Stream;
import net.minecraft.core.Holder;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/TestInstanceFinder.class */
@FunctionalInterface
public interface TestInstanceFinder {
    Stream<Holder.Reference<GameTestInstance>> findTests();
}
