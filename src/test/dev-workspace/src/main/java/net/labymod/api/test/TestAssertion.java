package net.labymod.api.test;

import java.util.Collection;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/test/TestAssertion.class */
public final class TestAssertion {
    private TestAssertion() {
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            fail(message);
        }
    }

    public static void assertNotNull(@Nullable Object object, String message) {
        if (object == null) {
            fail(message);
        }
    }

    public static void assertNull(@Nullable Object object, String message) {
        if (object != null) {
            fail(message + " (was: " + String.valueOf(object) + ")");
        }
    }

    public static void assertEquals(@Nullable Object expected, @Nullable Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            fail(message + " (expected: " + String.valueOf(expected) + ", actual: " + String.valueOf(actual) + ")");
        }
    }

    public static void assertNotEquals(@Nullable Object unexpected, @Nullable Object actual, String message) {
        if (Objects.equals(unexpected, actual)) {
            fail(message + " (both were: " + String.valueOf(actual) + ")");
        }
    }

    public static void assertInstanceOf(Class<?> expectedType, @Nullable Object object, String message) {
        if (object == null) {
            fail(message + " (object was null)");
        } else if (!expectedType.isInstance(object)) {
            fail(message + " (expected: " + expectedType.getName() + ", actual: " + object.getClass().getName() + ")");
        }
    }

    public static void assertNotEmpty(@Nullable Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            fail(message);
        }
    }

    public static void assertNotBlank(@Nullable String string, String message) {
        if (string == null || string.isBlank()) {
            fail(message);
        }
    }

    public static void assertGreaterThan(long minimum, long actual, String message) {
        if (actual <= minimum) {
            fail(message + " (expected > " + minimum + ", actual: " + message + ")");
        }
    }

    public static void assertLessThan(long maximum, long actual, String message) {
        if (actual >= maximum) {
            fail(message + " (expected < " + maximum + ", actual: " + message + ")");
        }
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void fail(String message, Throwable cause) {
        throw new AssertionError(message, cause);
    }
}
