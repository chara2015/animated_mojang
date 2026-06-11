package net.labymod.api.test;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.EventBus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/TestContext.class */
public interface TestContext {
    LabyAPI labyAPI();

    Minecraft minecraft();

    EventBus eventBus();

    boolean awaitCondition(BooleanSupplier booleanSupplier, long j, TimeUnit timeUnit);

    void log(String str);

    void log(String str, Object... objArr);

    TestPhase currentPhase();

    String currentTestName();

    InputEmulator input();

    default boolean awaitCondition(BooleanSupplier condition) {
        return awaitCondition(condition, 10L, TimeUnit.SECONDS);
    }
}
