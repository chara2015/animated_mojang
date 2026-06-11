package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestSequence.class */
public class GameTestSequence {
    final GameTestInfo parent;
    private final List<GameTestEvent> events = Lists.newArrayList();
    private int lastTick;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/GameTestSequence$Condition.class */
    public class Condition {
        private static final int NOT_TRIGGERED = -1;
        private int triggerTime = -1;

        public Condition() {
        }

        void trigger(int $$0) {
            if (this.triggerTime != -1) {
                throw new IllegalStateException("Condition already triggered at " + this.triggerTime);
            }
            this.triggerTime = $$0;
        }

        public void assertTriggeredThisTick() {
            int $$0 = GameTestSequence.this.parent.getTick();
            if (this.triggerTime != $$0) {
                if (this.triggerTime == -1) {
                    throw new GameTestAssertException(Component.translatable("test.error.sequence.condition_not_triggered"), $$0);
                }
                throw new GameTestAssertException(Component.translatable("test.error.sequence.condition_already_triggered", Integer.valueOf(this.triggerTime)), $$0);
            }
        }
    }

    GameTestSequence(GameTestInfo $$0) {
        this.parent = $$0;
        this.lastTick = $$0.getTick();
    }

    public GameTestSequence thenWaitUntil(Runnable $$0) {
        this.events.add(GameTestEvent.create($$0));
        return this;
    }

    public GameTestSequence thenWaitUntil(long $$0, Runnable $$1) {
        this.events.add(GameTestEvent.create($$0, $$1));
        return this;
    }

    public GameTestSequence thenIdle(int $$0) {
        return thenExecuteAfter($$0, () -> {
        });
    }

    public GameTestSequence thenExecute(Runnable $$0) {
        this.events.add(GameTestEvent.create(() -> {
            executeWithoutFail($$0);
        }));
        return this;
    }

    public GameTestSequence thenExecuteAfter(int $$0, Runnable $$1) {
        this.events.add(GameTestEvent.create(() -> {
            if (this.parent.getTick() < this.lastTick + $$0) {
                throw new GameTestAssertException(Component.translatable("test.error.sequence.not_completed"), this.parent.getTick());
            }
            executeWithoutFail($$1);
        }));
        return this;
    }

    public GameTestSequence thenExecuteFor(int $$0, Runnable $$1) {
        this.events.add(GameTestEvent.create(() -> {
            if (this.parent.getTick() < this.lastTick + $$0) {
                executeWithoutFail($$1);
                throw new GameTestAssertException(Component.translatable("test.error.sequence.not_completed"), this.parent.getTick());
            }
        }));
        return this;
    }

    public void thenSucceed() {
        List<GameTestEvent> list = this.events;
        GameTestInfo gameTestInfo = this.parent;
        Objects.requireNonNull(gameTestInfo);
        list.add(GameTestEvent.create(gameTestInfo::succeed));
    }

    public void thenFail(Supplier<GameTestException> $$0) {
        this.events.add(GameTestEvent.create(() -> {
            this.parent.fail((GameTestException) $$0.get());
        }));
    }

    public Condition thenTrigger() {
        Condition $$0 = new Condition();
        this.events.add(GameTestEvent.create(() -> {
            $$0.trigger(this.parent.getTick());
        }));
        return $$0;
    }

    public void tickAndContinue(int $$0) {
        try {
            tick($$0);
        } catch (GameTestAssertException e) {
        }
    }

    public void tickAndFailIfNotComplete(int $$0) {
        try {
            tick($$0);
        } catch (GameTestAssertException $$1) {
            this.parent.fail($$1);
        }
    }

    private void executeWithoutFail(Runnable $$0) {
        try {
            $$0.run();
        } catch (GameTestAssertException $$1) {
            this.parent.fail($$1);
        }
    }

    private void tick(int $$0) {
        Iterator<GameTestEvent> $$1 = this.events.iterator();
        while ($$1.hasNext()) {
            GameTestEvent $$2 = $$1.next();
            $$2.assertion.run();
            $$1.remove();
            int $$3 = $$0 - this.lastTick;
            int $$4 = this.lastTick;
            this.lastTick = $$0;
            if ($$2.expectedDelay != null && $$2.expectedDelay.longValue() != $$3) {
                this.parent.fail(new GameTestAssertException(Component.translatable("test.error.sequence.invalid_tick", Long.valueOf(((long) $$4) + $$2.expectedDelay.longValue())), $$0));
                return;
            }
        }
    }
}
