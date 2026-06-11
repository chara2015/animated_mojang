package net.labymod.core.test.integration;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.service.CustomServiceLoader;
import net.labymod.api.test.IntegrationTest;
import net.labymod.api.test.IntegrationTestSuite;
import net.labymod.api.test.TestContext;
import net.labymod.api.test.TestPhase;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/IntegrationTestDiscovery.class */
public final class IntegrationTestDiscovery {
    private static final Logging LOGGER = Logging.create((Class<?>) IntegrationTestDiscovery.class);

    private IntegrationTestDiscovery() {
    }

    public static List<IntegrationTestSuite> discoverTestSuites() {
        List<IntegrationTestSuite> suites = new ArrayList<>();
        CustomServiceLoader<IntegrationTestSuite> loader = CustomServiceLoader.load(IntegrationTestSuite.class, IntegrationTestDiscovery.class.getClassLoader(), CustomServiceLoader.ServiceType.ADVANCED);
        for (IntegrationTestSuite suite : loader) {
            suites.add(suite);
            LOGGER.debug("Discovered test suite: {}", suite.getClass().getName());
        }
        return suites;
    }

    public static List<TestMethod> discoverTests(IntegrationTestSuite suite) {
        List<TestMethod> tests = new ArrayList<>();
        Class<?> suiteClass = suite.getClass();
        for (Method method : suiteClass.getDeclaredMethods()) {
            IntegrationTest annotation = (IntegrationTest) method.getAnnotation(IntegrationTest.class);
            if (annotation != null) {
                if (!isValidTestMethod(method)) {
                    LOGGER.warn("Invalid test method signature: {}.{} - must be public, non-static, void return type, with single TestContext parameter", suiteClass.getSimpleName(), method.getName());
                } else {
                    tests.add(new TestMethod(suite, method, annotation.phase(), annotation.timeout(), annotation.priority(), annotation.description()));
                    LOGGER.debug("Discovered test: {}.{} (phase={}, priority={})", suiteClass.getSimpleName(), method.getName(), annotation.phase(), Integer.valueOf(annotation.priority()));
                }
            }
        }
        return tests;
    }

    private static boolean isValidTestMethod(Method method) {
        if (!Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers()) || method.getReturnType() != Void.TYPE) {
            return false;
        }
        Class<?>[] params = method.getParameterTypes();
        if (params.length != 1) {
            return false;
        }
        return TestContext.class.isAssignableFrom(params[0]);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/integration/IntegrationTestDiscovery$TestMethod.class */
    public static final class TestMethod {
        private final IntegrationTestSuite suite;
        private final Method method;
        private final TestPhase phase;
        private final long timeout;
        private final int priority;
        private final String description;

        TestMethod(IntegrationTestSuite suite, Method method, TestPhase phase, long timeout, int priority, String description) {
            this.suite = suite;
            this.method = method;
            this.phase = phase;
            this.timeout = timeout;
            this.priority = priority;
            this.description = description;
        }

        public IntegrationTestSuite suite() {
            return this.suite;
        }

        public Method method() {
            return this.method;
        }

        public TestPhase phase() {
            return this.phase;
        }

        public long timeout() {
            return this.timeout;
        }

        public int priority() {
            return this.priority;
        }

        public String description() {
            return this.description;
        }

        public String suiteName() {
            return this.suite.getClass().getSimpleName();
        }

        public String testName() {
            return this.method.getName();
        }

        public String fullName() {
            return suiteName() + "." + testName();
        }
    }
}
