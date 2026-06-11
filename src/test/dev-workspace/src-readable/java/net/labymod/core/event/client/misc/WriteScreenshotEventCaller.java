package net.labymod.core.event.client.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/misc/WriteScreenshotEventCaller.class */
public class WriteScreenshotEventCaller {
    public static void call(byte[] imageData, File destination) throws IOException {
        WriteScreenshotEvent event = (WriteScreenshotEvent) Laby.fireEvent(new WriteScreenshotEvent(Phase.PRE, imageData, destination));
        if (!event.isCancelled()) {
            imageData = event.getImage();
            FileOutputStream outputStream = new FileOutputStream(event.getDestination());
            try {
                outputStream.write(imageData);
                outputStream.close();
            } catch (Throwable th) {
                try {
                    outputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        Laby.fireEvent(new WriteScreenshotEvent(Phase.POST, imageData, destination));
    }
}
