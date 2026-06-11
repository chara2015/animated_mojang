package net.labymod.api.client.gui.screen.state.recorder;

import net.labymod.api.client.gui.screen.state.recorder.RecorderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/recorder/RecordedState.class */
public interface RecordedState<T extends RecorderState> {
    void submit(RecordedStateContext recordedStateContext);

    T state();
}
