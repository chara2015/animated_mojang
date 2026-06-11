package net.minecraft.util.thread;

import java.lang.Runnable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/thread/ReentrantBlockableEventLoop.class */
public abstract class ReentrantBlockableEventLoop<R extends Runnable> extends BlockableEventLoop<R> {
    private int reentrantCount;

    public ReentrantBlockableEventLoop(String $$0) {
        super($$0);
    }

    @Override // net.minecraft.util.thread.BlockableEventLoop
    public boolean scheduleExecutables() {
        return runningTask() || super.scheduleExecutables();
    }

    protected boolean runningTask() {
        return this.reentrantCount != 0;
    }

    @Override // net.minecraft.util.thread.BlockableEventLoop
    public void doRunTask(R $$0) {
        this.reentrantCount++;
        try {
            super.doRunTask($$0);
        } finally {
            this.reentrantCount--;
        }
    }
}
