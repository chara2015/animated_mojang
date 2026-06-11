package net.labymod.api.event.client.misc;

import java.io.File;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/misc/CaptureScreenshotEvent.class */
public class CaptureScreenshotEvent implements Event {
    private final File destination;

    public CaptureScreenshotEvent(File destination) {
        this.destination = destination;
    }

    public File getDestination() {
        return this.destination;
    }
}
