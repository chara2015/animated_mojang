package net.labymod.core.client;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.inject.Singleton;
import net.labymod.api.client.ClipboardController;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.Logging;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.system.MemoryUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/DefaultClipboardController.class */
@Singleton
@Implements(ClipboardController.class)
public class DefaultClipboardController implements ClipboardController {
    private static final Logging LOGGER = Logging.getLogger();
    private static final int BUFFER_SIZE = 8192;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(8192);

    @Override // net.labymod.api.client.ClipboardController
    public String getClipboardContent(long windowHandle) {
        return getClipboardContent(windowHandle, (error, description) -> {
            if (error == 65545) {
                return;
            }
            String readableDescription = MemoryUtil.memUTF8(description);
            LOGGER.error("{}: {}", Integer.valueOf(error), readableDescription);
        });
    }

    public String getClipboardContent(long windowHandle, GLFWErrorCallbackI errorCallback) {
        GLFWErrorCallback defaultCallback = GLFW.glfwSetErrorCallback(errorCallback);
        String clipboardContent = GLFW.glfwGetClipboardString(windowHandle);
        String clipboardContent2 = clipboardContent == null ? "" : clipboardContent;
        GLFWErrorCallback customErrorCallback = GLFW.glfwSetErrorCallback(defaultCallback);
        if (customErrorCallback != null) {
            customErrorCallback.free();
        }
        return clipboardContent2;
    }

    @Override // net.labymod.api.client.ClipboardController
    public void setClipboardContent(long windowHandle, String content) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        int contentCapacity = contentBytes.length + 1;
        if (contentCapacity < this.buffer.capacity()) {
            setClipboardContent(windowHandle, this.buffer, contentBytes);
        } else {
            setClipboardContent(windowHandle, contentBytes, contentCapacity);
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() throws Exception {
    }

    private void setClipboardContent(long windowHandle, byte[] data, int length) {
        ByteBuffer buffer = MemoryUtil.memAlloc(length);
        try {
            setClipboardContent(windowHandle, buffer, data);
            MemoryUtil.memFree(buffer);
        } catch (Throwable th) {
            MemoryUtil.memFree(buffer);
            throw th;
        }
    }

    private void setClipboardContent(long windowHandle, ByteBuffer clipboardBuffer, byte[] data) {
        clipboardBuffer.clear();
        clipboardBuffer.put(data);
        clipboardBuffer.put((byte) 0);
        clipboardBuffer.flip();
        GLFW.glfwSetClipboardString(windowHandle, clipboardBuffer);
    }
}
