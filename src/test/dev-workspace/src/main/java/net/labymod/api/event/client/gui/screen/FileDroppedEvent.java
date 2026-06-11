package net.labymod.api.event.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/FileDroppedEvent.class */
public class FileDroppedEvent extends DefaultCancellable implements Event {
    private final MutableMouse mouse;
    private final List<Path> paths;

    public FileDroppedEvent(MutableMouse mouse, List<Path> paths) {
        this.mouse = mouse;
        this.paths = paths;
    }

    public MutableMouse mouse() {
        return this.mouse;
    }

    public List<Path> paths() {
        return this.paths;
    }
}
