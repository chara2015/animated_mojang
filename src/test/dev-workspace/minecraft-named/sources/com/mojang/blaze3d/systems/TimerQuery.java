package com.mojang.blaze3d.systems;

import java.util.OptionalLong;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/TimerQuery.class */
public class TimerQuery {
    private CommandEncoder activeEncoder;
    private GpuQuery activeGpuQuery;

    public static TimerQuery getInstance() {
        return TimerQueryLazyLoader.INSTANCE;
    }

    public boolean isRecording() {
        return this.activeGpuQuery != null;
    }

    public void beginProfile() {
        RenderSystem.assertOnRenderThread();
        if (this.activeGpuQuery != null) {
            throw new IllegalStateException("Current profile not ended");
        }
        this.activeEncoder = RenderSystem.getDevice().createCommandEncoder();
        this.activeGpuQuery = this.activeEncoder.timerQueryBegin();
    }

    public FrameProfile endProfile() {
        RenderSystem.assertOnRenderThread();
        if (this.activeGpuQuery == null || this.activeEncoder == null) {
            throw new IllegalStateException("endProfile called before beginProfile");
        }
        this.activeEncoder.timerQueryEnd(this.activeGpuQuery);
        FrameProfile $$0 = new FrameProfile(this.activeGpuQuery);
        this.activeGpuQuery = null;
        this.activeEncoder = null;
        return $$0;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/TimerQuery$FrameProfile.class */
    public static class FrameProfile {
        private static final long NO_RESULT = 0;
        private static final long CANCELLED_RESULT = -1;
        private final GpuQuery gpuQuery;
        private long timerResult = 0;

        FrameProfile(GpuQuery $$0) {
            this.gpuQuery = $$0;
        }

        public void cancel() {
            RenderSystem.assertOnRenderThread();
            if (this.timerResult != 0) {
                return;
            }
            this.timerResult = -1L;
            this.gpuQuery.close();
        }

        public boolean isDone() {
            RenderSystem.assertOnRenderThread();
            if (this.timerResult != 0) {
                return true;
            }
            OptionalLong $$0 = this.gpuQuery.getValue();
            if ($$0.isPresent()) {
                this.timerResult = $$0.getAsLong();
                this.gpuQuery.close();
                return true;
            }
            return false;
        }

        public long get() {
            RenderSystem.assertOnRenderThread();
            if (this.timerResult == 0) {
                OptionalLong $$0 = this.gpuQuery.getValue();
                if ($$0.isPresent()) {
                    this.timerResult = $$0.getAsLong();
                    this.gpuQuery.close();
                }
            }
            return this.timerResult;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/systems/TimerQuery$TimerQueryLazyLoader.class */
    static class TimerQueryLazyLoader {
        static final TimerQuery INSTANCE = instantiate();

        private TimerQueryLazyLoader() {
        }

        private static TimerQuery instantiate() {
            return new TimerQuery();
        }
    }
}
