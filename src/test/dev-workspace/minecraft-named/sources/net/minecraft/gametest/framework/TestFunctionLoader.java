package net.minecraft.gametest.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/TestFunctionLoader.class */
public abstract class TestFunctionLoader {
    private static final List<TestFunctionLoader> loaders = new ArrayList();

    public abstract void load(BiConsumer<ResourceKey<Consumer<GameTestHelper>>, Consumer<GameTestHelper>> biConsumer);

    public static void registerLoader(TestFunctionLoader $$0) {
        loaders.add($$0);
    }

    public static void runLoaders(Registry<Consumer<GameTestHelper>> $$0) {
        for (TestFunctionLoader $$1 : loaders) {
            $$1.load(($$12, $$2) -> {
                Registry.register($$0, $$12, $$2);
            });
        }
    }
}
