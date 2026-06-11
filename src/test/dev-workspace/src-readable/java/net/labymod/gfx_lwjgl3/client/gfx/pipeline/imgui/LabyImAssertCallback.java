package net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui;

import imgui.assertion.ImAssertCallback;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/client/gfx/pipeline/imgui/LabyImAssertCallback.class */
public class LabyImAssertCallback extends ImAssertCallback {
    private static final Logging LOGGER = Logging.getLogger();

    public void imAssert(String assertion, int line, String file) {
        if (IdeUtil.RUNNING_IN_IDE) {
            LOGGER.error("Dear ImGui Assertion Failed: " + assertion, new Object[0]);
            LOGGER.error("Assertion Located At: {}:{}", file, Integer.valueOf(line));
        }
    }

    public void imAssertCallback(String assertion, int line, String file) {
    }
}
