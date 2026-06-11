package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.input;

import java.nio.ByteBuffer;
import net.labymod.api.util.Disposable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/backend/lwjgl3/input/KeyboardInput.class */
public interface KeyboardInput extends Disposable {
    void create();

    void poll(ByteBuffer byteBuffer);

    void read(ByteBuffer byteBuffer);
}
