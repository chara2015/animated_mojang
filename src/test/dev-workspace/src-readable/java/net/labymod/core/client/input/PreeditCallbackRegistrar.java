package net.labymod.core.client.input;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWPreeditCallback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/input/PreeditCallbackRegistrar.class */
public final class PreeditCallbackRegistrar {
    private static boolean registered;
    private static GLFWPreeditCallback previousCallback;

    public static void register(long windowHandle) {
        if (registered) {
            return;
        }
        registered = true;
        previousCallback = GLFW.glfwSetPreeditCallback(windowHandle, (window, preeditCount, preeditString, blockCount, blockSizes, focusedBlock, caret) -> {
            if (previousCallback != null) {
                previousCallback.invoke(window, preeditCount, preeditString, blockCount, blockSizes, focusedBlock, caret);
                return;
            }
            PreeditText text = PreeditText.fromCallback(preeditCount, preeditString, blockCount, blockSizes, focusedBlock, caret);
            ScreenWrapper screen = Laby.labyAPI().minecraft().minecraftWindow().currentScreen();
            if (screen != null) {
                screen.handlePreeditText(text);
            }
        });
    }
}
