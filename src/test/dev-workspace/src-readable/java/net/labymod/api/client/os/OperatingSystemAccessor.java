package net.labymod.api.client.os;

import java.io.InputStream;
import net.labymod.api.client.resources.texture.GameImage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/os/OperatingSystemAccessor.class */
public interface OperatingSystemAccessor {
    void copyToClipboard(GameImage gameImage);

    GameImage getImageInClipboard();

    void setWindowIcon(long j, InputStream inputStream);
}
