package net.labymod.core.main.user.shop.cosmetic.loader;

import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.CosmeticLoadingState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/loader/CosmeticRetryPolicy.class */
public class CosmeticRetryPolicy {
    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final long BASE_DELAY_MS = 2000;
    private static final long MAX_DELAY_MS = 30000;
    private final int maxRetries;

    public CosmeticRetryPolicy() {
        this(3);
    }

    public CosmeticRetryPolicy(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public boolean shouldRetry(CosmeticDefinition definition) {
        if (definition.loadingState() != CosmeticLoadingState.FAILED || definition.failureCount() >= this.maxRetries) {
            return false;
        }
        long elapsed = System.currentTimeMillis() - definition.lastFailureTimestamp();
        return elapsed >= getBackoffDelay(definition.failureCount());
    }

    private long getBackoffDelay(int failureCount) {
        return Math.min(2000 * (1 << (failureCount - 1)), 30000L);
    }
}
