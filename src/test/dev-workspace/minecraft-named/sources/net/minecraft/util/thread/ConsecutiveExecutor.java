package net.minecraft.util.thread;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import net.minecraft.util.thread.StrictQueue;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ConsecutiveExecutor.class */
public class ConsecutiveExecutor extends AbstractConsecutiveExecutor<Runnable> {
    public ConsecutiveExecutor(Executor $$0, String $$1) {
        super(new StrictQueue.QueueStrictQueue(new ConcurrentLinkedQueue()), $$0, $$1);
    }

    @Override // net.minecraft.util.thread.TaskScheduler
    public Runnable wrapRunnable(Runnable $$0) {
        return $$0;
    }
}
