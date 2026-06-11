package net.labymod.core.test.integration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.labymod.api.test.TestPhase;
import net.labymod.api.test.TestResult;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.test.integration.IntegrationTestDiscovery;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/IntegrationTestExecutor.class */
public final class IntegrationTestExecutor {
    private static final Logging LOGGER = Logging.create((Class<?>) IntegrationTestExecutor.class);
    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "IntegrationTestExecutor");
        thread.setDaemon(true);
        return thread;
    });

    public TestResult execute(IntegrationTestDiscovery.TestMethod test, TestPhase phase) {
        String suiteName = test.suiteName();
        String testName = test.testName();
        String description = test.description();
        long timeout = test.timeout();
        LOGGER.debug("Executing test: {}.{}", suiteName, testName);
        DefaultTestContext context = new DefaultTestContext(phase, test.fullName());
        long startTime = System.currentTimeMillis();
        Callable<Void> testCallable = () -> {
            Method method = test.method();
            method.setAccessible(true);
            method.invoke(test.suite(), context);
            return null;
        };
        try {
            Future<Void> future = this.executor.submit(testCallable);
            future.get(timeout, TimeUnit.MILLISECONDS);
            return TestResult.success(suiteName, testName, description, phase, System.currentTimeMillis() - startTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return TestResult.failure(suiteName, testName, description, phase, System.currentTimeMillis() - startTime, new AssertionError("Test was interrupted"));
        } catch (ExecutionException e2) {
            long duration = System.currentTimeMillis() - startTime;
            Throwable cause = e2.getCause();
            if (cause instanceof InvocationTargetException) {
                cause = cause.getCause();
            }
            if (cause instanceof AssertionError) {
                return TestResult.failure(suiteName, testName, description, phase, duration, cause);
            }
            LOGGER.error("Test {} threw unexpected exception", test.fullName(), cause);
            return TestResult.failure(suiteName, testName, description, phase, duration, cause);
        } catch (TimeoutException e3) {
            LOGGER.warn("Test {} timed out after {}ms", test.fullName(), Long.valueOf(timeout));
            return TestResult.timeout(suiteName, testName, description, phase, timeout);
        }
    }

    public void shutdown() {
        this.executor.shutdownNow();
    }
}
