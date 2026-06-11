package net.labymod.api.test;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/TestResult.class */
public final class TestResult {
    private final String suiteName;
    private final String testName;
    private final String description;
    private final TestPhase phase;
    private final Status status;
    private final long durationMillis;
    private final Throwable failure;
    private final String failureMessage;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/TestResult$Status.class */
    public enum Status {
        PASSED,
        FAILED,
        SKIPPED,
        TIMEOUT
    }

    private TestResult(String suiteName, String testName, String description, TestPhase phase, Status status, long durationMillis, Throwable failure, String failureMessage) {
        this.suiteName = suiteName;
        this.testName = testName;
        this.description = description;
        this.phase = phase;
        this.status = status;
        this.durationMillis = durationMillis;
        this.failure = failure;
        this.failureMessage = failureMessage;
    }

    public static TestResult success(String suiteName, String testName, String description, TestPhase phase, long durationMillis) {
        return new TestResult(suiteName, testName, description, phase, Status.PASSED, durationMillis, null, null);
    }

    public static TestResult failure(String suiteName, String testName, String description, TestPhase phase, long durationMillis, Throwable failure) {
        String message = failure.getMessage();
        if (message == null) {
            message = failure.getClass().getName();
        }
        return new TestResult(suiteName, testName, description, phase, Status.FAILED, durationMillis, failure, message);
    }

    public static TestResult skipped(String suiteName, String testName, String description, TestPhase phase, String reason) {
        return new TestResult(suiteName, testName, description, phase, Status.SKIPPED, 0L, null, reason);
    }

    public static TestResult timeout(String suiteName, String testName, String description, TestPhase phase, long timeoutMillis) {
        return new TestResult(suiteName, testName, description, phase, Status.TIMEOUT, timeoutMillis, null, "Test exceeded timeout of " + timeoutMillis + "ms");
    }

    public String suiteName() {
        return this.suiteName;
    }

    public String testName() {
        return this.testName;
    }

    public String description() {
        return this.description;
    }

    public TestPhase phase() {
        return this.phase;
    }

    public Status status() {
        return this.status;
    }

    public long durationMillis() {
        return this.durationMillis;
    }

    @Nullable
    public Throwable failure() {
        return this.failure;
    }

    @Nullable
    public String failureMessage() {
        return this.failureMessage;
    }

    public boolean isPassed() {
        return this.status == Status.PASSED;
    }

    public boolean isFailed() {
        return this.status == Status.FAILED || this.status == Status.TIMEOUT;
    }

    public String fullTestName() {
        return this.suiteName + "." + this.testName;
    }
}
