package net.minecraft.server;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/TickTask.class */
public class TickTask implements Runnable {
    private final int tick;
    private final Runnable runnable;

    public TickTask(int $$0, Runnable $$1) {
        this.tick = $$0;
        this.runnable = $$1;
    }

    public int getTick() {
        return this.tick;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.runnable.run();
    }
}
