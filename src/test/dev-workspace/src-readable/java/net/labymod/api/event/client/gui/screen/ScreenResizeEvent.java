package net.labymod.api.event.client.gui.screen;

import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/ScreenResizeEvent.class */
public class ScreenResizeEvent implements Event {
    private final int width;
    private final int height;
    private final int rawWidth;
    private final int rawHeight;

    public ScreenResizeEvent(int width, int height, int rawWidth, int rawHeight) {
        this.width = width;
        this.height = height;
        this.rawWidth = rawWidth;
        this.rawHeight = rawHeight;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getRawWidth() {
        return this.rawWidth;
    }

    public int getRawHeight() {
        return this.rawHeight;
    }
}
