package net.labymod.core.test.integration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Laby;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.world.WorldEnterEvent;
import net.labymod.api.test.IntegrationTestSuite;
import net.labymod.api.test.TestPhase;
import net.labymod.api.test.TestResult;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.test.integration.IntegrationTestDiscovery;
import net.labymod.util.property.SystemProperties;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/IntegrationTestRunner.class */
public final class IntegrationTestRunner {
    private static final Logging LOGGER = Logging.create((Class<?>) IntegrationTestRunner.class);
    private static IntegrationTestRunner instance;
    private final boolean exitAfterTests = SystemProperties.TEST_EXIT_AFTER.get().booleanValue();
    private final long globalTimeout = SystemProperties.TEST_GLOBAL_TIMEOUT.get().longValue();
    private final boolean autoJoinServer = SystemProperties.TEST_AUTO_JOIN_SERVER.get().booleanValue();
    private final Map<TestPhase, List<IntegrationTestDiscovery.TestMethod>> testsByPhase = new EnumMap(TestPhase.class);
    private final List<TestResult> results;
    private final IntegrationTestExecutor executor;
    private final IntegrationTestReporter reporter;
    private final AutoServerJoiner serverJoiner;
    private Task globalTimeoutTask;
    private volatile boolean globalTimeoutReached;
    private boolean inWorldTestsRan;

    private IntegrationTestRunner() {
        for (TestPhase phase : TestPhase.values()) {
            this.testsByPhase.put(phase, new ArrayList());
        }
        this.results = new ArrayList();
        this.executor = new IntegrationTestExecutor();
        this.reporter = new IntegrationTestReporter(SystemProperties.TEST_REPORT_DIR.get());
        this.serverJoiner = new AutoServerJoiner();
    }

    public static void initialize() {
        if (!SystemProperties.TEST_ENABLED.get().booleanValue()) {
            return;
        }
        LOGGER.info("Integration test mode enabled", new Object[0]);
        instance = new IntegrationTestRunner();
        instance.setup();
    }

    private void setup() {
        List<IntegrationTestSuite> suites = IntegrationTestDiscovery.discoverTestSuites();
        LOGGER.info("Discovered {} test suites", Integer.valueOf(suites.size()));
        for (IntegrationTestSuite suite : suites) {
            List<IntegrationTestDiscovery.TestMethod> methods = IntegrationTestDiscovery.discoverTests(suite);
            for (IntegrationTestDiscovery.TestMethod method : methods) {
                this.testsByPhase.get(method.phase()).add(method);
            }
        }
        for (List<IntegrationTestDiscovery.TestMethod> tests : this.testsByPhase.values()) {
            tests.sort(Comparator.comparingInt((v0) -> {
                return v0.priority();
            }));
        }
        int totalTests = 0;
        for (List<IntegrationTestDiscovery.TestMethod> tests2 : this.testsByPhase.values()) {
            totalTests += tests2.size();
        }
        LOGGER.info("Found {} tests across all phases", Integer.valueOf(totalTests));
        startGlobalTimeoutTask();
        Laby.references().eventBus().registerListener(this);
    }

    private void startGlobalTimeoutTask() {
        if (this.globalTimeout <= 0) {
            return;
        }
        LOGGER.info("Global timeout set to {}ms", Long.valueOf(this.globalTimeout));
        this.globalTimeoutTask = Task.builder(this::onGlobalTimeout).delay(this.globalTimeout, TimeUnit.MILLISECONDS).build();
        this.globalTimeoutTask.execute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void onGlobalTimeout() throws MatchException {
        if (this.globalTimeoutReached) {
            return;
        }
        this.globalTimeoutReached = true;
        LOGGER.error("Global timeout of {}ms reached! Aborting remaining tests.", Long.valueOf(this.globalTimeout));
        finishTests();
    }

    private void cancelGlobalTimeoutTask() {
        if (this.globalTimeoutTask != null) {
            this.globalTimeoutTask.cancel();
            this.globalTimeoutTask = null;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Subscribe(126)
    public void onGameInitialize(GameInitializeEvent event) throws MatchException {
        GameInitializeEvent.Lifecycle lifecycle = event.getLifecycle();
        if (lifecycle == GameInitializeEvent.Lifecycle.PRE_GAME_STARTED) {
            runTestsForPhase(TestPhase.AFTER_INIT);
            return;
        }
        if (lifecycle == GameInitializeEvent.Lifecycle.POST_STARTUP) {
            runTestsForPhase(TestPhase.AFTER_STARTUP);
        } else if (lifecycle == GameInitializeEvent.Lifecycle.POST_GAME_STARTED) {
            runTestsForPhase(TestPhase.AFTER_GAME_STARTED);
            handlePostGameStarted();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Subscribe
    public void onWorldEnter(WorldEnterEvent event) throws MatchException {
        if (this.inWorldTestsRan) {
            return;
        }
        this.inWorldTestsRan = true;
        runTestsForPhase(TestPhase.IN_WORLD);
        finishTests();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void handlePostGameStarted() throws MatchException {
        List<IntegrationTestDiscovery.TestMethod> inWorldTests = this.testsByPhase.get(TestPhase.IN_WORLD);
        if (inWorldTests.isEmpty()) {
            finishTests();
        } else if (this.autoJoinServer) {
            LOGGER.info("Auto-joining test server {} for IN_WORLD tests...", this.serverJoiner.getServerAddress());
            this.serverJoiner.joinServer();
        } else {
            LOGGER.info("Waiting for world/server entry to run {} IN_WORLD tests...", Integer.valueOf(inWorldTests.size()));
            LOGGER.info("Set -Dlabymod.test.autoJoinServer=true to auto-join {}", this.serverJoiner.getServerAddress());
        }
    }

    private void runTestsForPhase(TestPhase phase) {
        if (this.globalTimeoutReached) {
            LOGGER.warn("Skipping phase {} due to global timeout", phase);
            return;
        }
        List<IntegrationTestDiscovery.TestMethod> tests = this.testsByPhase.get(phase);
        if (tests.isEmpty()) {
            LOGGER.info("No tests for phase {}", phase);
            return;
        }
        LOGGER.info("Running {} tests for phase {}...", Integer.valueOf(tests.size()), phase);
        for (IntegrationTestDiscovery.TestMethod test : tests) {
            if (this.globalTimeoutReached) {
                LOGGER.warn("Skipping remaining tests in phase {} due to global timeout", phase);
                return;
            }
            TestResult result = this.executor.execute(test, phase);
            this.results.add(result);
            String status = result.isPassed() ? "[PASS]" : "[FAIL]";
            String message = result.fullTestName();
            if (result.description() != null && !result.description().isEmpty()) {
                message = message + " - " + result.description();
            }
            if (result.isPassed()) {
                LOGGER.info("{} {} ({}ms)", status, message, Long.valueOf(result.durationMillis()));
            } else {
                LOGGER.error("{} {} - {}", status, message, result.failureMessage());
                if (result.failure() != null) {
                    LOGGER.error("Stack trace:", result.failure());
                }
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void finishTests() throws MatchException {
        cancelGlobalTimeoutTask();
        LOGGER.info("All integration tests completed", new Object[0]);
        this.reporter.generateReport(this.results);
        long passed = 0;
        long failed = 0;
        long skipped = 0;
        for (TestResult result : this.results) {
            if (result.isPassed()) {
                passed++;
            } else if (result.isFailed()) {
                failed++;
            } else if (result.status() == TestResult.Status.SKIPPED) {
                skipped++;
            }
        }
        LOGGER.info("========================================", new Object[0]);
        LOGGER.info("Integration Test Results:", new Object[0]);
        LOGGER.info("  Passed:  {}", Long.valueOf(passed));
        LOGGER.info("  Failed:  {}", Long.valueOf(failed));
        LOGGER.info("  Skipped: {}", Long.valueOf(skipped));
        LOGGER.info("  Total:   {}", Integer.valueOf(this.results.size()));
        LOGGER.info("========================================", new Object[0]);
        if (this.exitAfterTests) {
            int exitCode = failed > 0 ? 1 : 0;
            LOGGER.info("Exiting with code {} (exitAfterTests=true)", Integer.valueOf(exitCode));
            Runtime.getRuntime().halt(exitCode);
        }
    }

    public static IntegrationTestRunner getInstance() {
        return instance;
    }

    public static boolean isEnabled() {
        return SystemProperties.TEST_ENABLED.get().booleanValue();
    }
}
