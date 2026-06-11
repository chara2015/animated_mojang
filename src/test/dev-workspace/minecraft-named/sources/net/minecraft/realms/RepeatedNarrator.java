package net.minecraft.realms;

import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.GameNarrator;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/realms/RepeatedNarrator.class */
public class RepeatedNarrator {
    private final float permitsPerSecond;
    private final AtomicReference<Params> params = new AtomicReference<>();

    public RepeatedNarrator(Duration $$0) {
        this.permitsPerSecond = 1000.0f / $$0.toMillis();
    }

    public void narrate(GameNarrator $$0, Component $$1) {
        Params $$2 = this.params.updateAndGet($$12 -> {
            if ($$12 == null || !$$1.equals($$12.narration)) {
                return new Params($$1, RateLimiter.create(this.permitsPerSecond));
            }
            return $$12;
        });
        if ($$2.rateLimiter.tryAcquire(1)) {
            $$0.saySystemNow($$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/realms/RepeatedNarrator$Params.class */
    static class Params {
        final Component narration;
        final RateLimiter rateLimiter;

        Params(Component $$0, RateLimiter $$1) {
            this.narration = $$0;
            this.rateLimiter = $$1;
        }
    }
}
