package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.labymod.api.util.Disposable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/input/MouseInput.class */
public interface MouseInput extends Disposable {
    void create();

    void poll(IntBuffer intBuffer, ByteBuffer byteBuffer);

    void read(ByteBuffer byteBuffer);

    void setCursorPosition(int i, int i2);

    void grab(boolean z);

    boolean isInsideWindow();

    int getButtonCount();

    void setRawMouseInput(boolean z);
}
