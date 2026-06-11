package net.labymod.core.main.listener;

import net.labymod.api.Constants;
import net.labymod.api.LabyAPI;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/ScreenshotListener.class */
public class ScreenshotListener {
    private final LabyAPI labyAPI;

    public ScreenshotListener(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
    }

    @Subscribe(126)
    public void onCaptureScreenshot(CaptureScreenshotEvent event) {
        if (this.labyAPI.config().notifications().screenshotSound().get().booleanValue()) {
            this.labyAPI.minecraft().sounds().playSound(Constants.Resources.SOUND_SCREENSHOT_CAPTURE, 1.0f, 1.0f);
        }
    }
}
