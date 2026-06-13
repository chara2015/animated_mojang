package net.labymod.api.laby3d.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/FboWriteTracker.class */
public final class FboWriteTracker {
    public static final FboWriteTracker INSTANCE = new FboWriteTracker();
    private final Int2ObjectMap<FboWriteState> states = new Int2ObjectOpenHashMap();
    private int drawFbo = 0;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/FboWriteTracker$FboWriteState.class */
    public static class FboWriteState {
        public boolean written;
    }

    private FboWriteTracker() {
    }

    public void onBindFramebuffer(int target, int id) {
        if (target == 36160 || target == 36009) {
            this.drawFbo = id;
        }
    }

    public void onDrawCall() {
        if (this.drawFbo == 0) {
            return;
        }
        FboWriteState state = getOrCreateState(this.drawFbo);
        state.written = true;
    }

    public void onClear(int mask) {
        if (this.drawFbo != 0 && (mask & 16384) != 0) {
            FboWriteState state = getOrCreateState(this.drawFbo);
            state.written = false;
        }
    }

    public void onDeleteFramebuffer(int fbo) {
        this.states.remove(fbo);
    }

    public boolean wasWritten(int fbo) {
        FboWriteState state = (FboWriteState) this.states.get(fbo);
        return state != null && state.written;
    }

    public void nextFrame() {
        ObjectIterator it = this.states.values().iterator();
        while (it.hasNext()) {
            FboWriteState state = (FboWriteState) it.next();
            state.written = false;
        }
    }

    public FboWriteState getOrCreateState(int fbo) {
        return (FboWriteState) this.states.computeIfAbsent(fbo, k -> {
            return new FboWriteState();
        });
    }
}
