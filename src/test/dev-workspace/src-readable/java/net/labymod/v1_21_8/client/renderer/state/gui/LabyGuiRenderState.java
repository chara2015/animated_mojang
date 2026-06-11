package net.labymod.v1_21_8.client.renderer.state.gui;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/renderer/state/gui/LabyGuiRenderState.class */
public interface LabyGuiRenderState {
    void labyMod$flushPending();

    static void flushPending(Object obj) {
        if (obj instanceof LabyGuiRenderState) {
            LabyGuiRenderState state = (LabyGuiRenderState) obj;
            state.labyMod$flushPending();
        }
    }
}
