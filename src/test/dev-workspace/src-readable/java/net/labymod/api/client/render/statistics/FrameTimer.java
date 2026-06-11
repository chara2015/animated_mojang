package net.labymod.api.client.render.statistics;

import javax.inject.Singleton;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/statistics/FrameTimer.class */
@Singleton
@Referenceable
public class FrameTimer {
    private int frame = 0;
    private int pausedFrame = -1;
    private int targetFrame = -1;

    @Subscribe
    public void increment(GameRenderEvent event) {
        if (this.pausedFrame == -1 && event.phase() == Phase.POST) {
            if (this.frame == Integer.MAX_VALUE) {
                this.frame = 0;
            }
            this.frame++;
        }
    }

    public int getFrame() {
        return this.frame;
    }

    public int getPausedFrame() {
        return this.pausedFrame;
    }

    public boolean isPaused() {
        return this.pausedFrame != -1;
    }

    public void pause() {
        if (isPaused()) {
            return;
        }
        this.pausedFrame = this.frame;
        this.targetFrame = this.frame;
    }

    public void resume() {
        if (!isPaused()) {
            return;
        }
        this.pausedFrame = -1;
    }

    public void nextFrame() {
        nextFrame(1);
    }

    public void nextFrame(int delta) {
        if (!isPaused()) {
            throw new IllegalStateException("Rendering is not paused at the moment");
        }
        this.targetFrame += delta;
    }

    public void onPauseFrameRendered() {
        this.frame++;
    }

    public boolean isPauseInterrupted() {
        return this.pausedFrame != -1 && this.targetFrame > this.frame;
    }
}
