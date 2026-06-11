package net.labymod.core.client.os.linux;

import com.sun.jna.Pointer;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;
import net.labymod.core.client.os.linux.clipboard.GTKLib;
import net.labymod.core.client.os.linux.credentials.LinuxCredentialsAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/linux/LinuxAccessor.class */
public class LinuxAccessor extends AbstractOperatingSystemAccessor {
    @Override // net.labymod.api.client.os.OperatingSystemAccessor
    public void copyToClipboard(GameImage image) {
        GTKLib lib = GTKLib.INSTANCE;
        lib.gtk_init(0, null);
        Pointer display = lib.gdk_display_get_default();
        Pointer clipboard = lib.gtk_clipboard_get_default(display);
        Pointer pixelBuf = GTKLib.toPixelBuf(image);
        lib.gtk_clipboard_set_image(clipboard, pixelBuf);
        lib.gdk_pixbuf_unref(pixelBuf);
    }

    @Override // net.labymod.api.client.os.OperatingSystemAccessor
    public GameImage getImageInClipboard() {
        GTKLib lib = GTKLib.INSTANCE;
        lib.gtk_init(0, null);
        Pointer display = lib.gdk_display_get_default();
        Pointer clipboard = lib.gtk_clipboard_get_default(display);
        Pointer pixelBuf = lib.gtk_clipboard_wait_for_image(clipboard);
        if (pixelBuf == null) {
            return null;
        }
        return GTKLib.fromPixelBuf(pixelBuf);
    }

    @Override // net.labymod.core.client.os.AbstractOperatingSystemAccessor
    public CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException {
        return new LinuxCredentialsAccessor();
    }
}
