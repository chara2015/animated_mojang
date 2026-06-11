package net.labymod.core.test.integration;

import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.EventBus;
import net.labymod.api.test.InputEmulator;
import net.labymod.api.test.TestContext;
import net.labymod.api.test.TestPhase;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/DefaultTestContext.class */
public final class DefaultTestContext implements TestContext {
    private static final Logging LOGGER = Logging.create((Class<?>) DefaultTestContext.class);
    private final TestPhase currentPhase;
    private final String currentTestName;
    private final InputEmulator inputEmulator = new DefaultInputEmulator();

    public DefaultTestContext(TestPhase currentPhase, String currentTestName) {
        this.currentPhase = currentPhase;
        this.currentTestName = currentTestName;
    }

    @Override // net.labymod.api.test.TestContext
    public LabyAPI labyAPI() {
        return Laby.labyAPI();
    }

    @Override // net.labymod.api.test.TestContext
    public Minecraft minecraft() {
        LabyAPI api = Laby.labyAPI();
        if (api == null) {
            return null;
        }
        return api.minecraft();
    }

    @Override // net.labymod.api.test.TestContext
    public EventBus eventBus() {
        return Laby.references().eventBus();
    }

    @Override // net.labymod.api.test.TestContext
    public boolean awaitCondition(BooleanSupplier condition, long timeout, TimeUnit unit) {
        long timeoutMillis = unit.toMillis(timeout);
        long startTime = System.currentTimeMillis();
        long pollInterval = Math.min(100L, timeoutMillis / 10);
        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            if (condition.getAsBoolean()) {
                return true;
            }
            try {
                Thread.sleep(pollInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return condition.getAsBoolean();
    }

    @Override // net.labymod.api.test.TestContext
    public void log(String message) {
        LOGGER.info("[{}] {}", this.currentTestName, message);
    }

    @Override // net.labymod.api.test.TestContext
    public void log(String format, Object... args) {
        log(String.format(format, args));
    }

    @Override // net.labymod.api.test.TestContext
    public TestPhase currentPhase() {
        return this.currentPhase;
    }

    @Override // net.labymod.api.test.TestContext
    public String currentTestName() {
        return this.currentTestName;
    }

    @Override // net.labymod.api.test.TestContext
    public InputEmulator input() {
        return this.inputEmulator;
    }
}
