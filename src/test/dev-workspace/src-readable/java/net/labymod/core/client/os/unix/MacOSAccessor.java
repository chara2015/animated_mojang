package net.labymod.core.client.os.unix;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;
import com.sun.jna.Pointer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collection;
import javax.imageio.ImageIO;
import net.labymod.accountmanager.storage.credentials.CredentialsAccessor;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.os.AbstractOperatingSystemAccessor;
import net.labymod.core.client.os.unix.credentials.MacOSCredentialsAccessor;
import net.labymod.core.client.os.unix.ns.ClientUtil;
import net.labymod.core.client.os.unix.ns.NSPasteboard;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/unix/MacOSAccessor.class */
public class MacOSAccessor extends AbstractOperatingSystemAccessor {
    private static final Logging LOGGER = Logging.getLogger();

    @Override // net.labymod.api.client.os.OperatingSystemAccessor
    public void copyToClipboard(GameImage image) {
        Client client = Client.getInstance();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            image.write("png", outputStream);
            outputStream.flush();
            byte[] imageBuffer = outputStream.toByteArray();
            Proxy dataProxy = ClientUtil.newNSData(client, imageBuffer);
            Proxy imageProxy = ClientUtil.newNSImageInitWithData(client, dataProxy);
            NSPasteboard nsPasteboard = NSPasteboard.generalPasteboard(client);
            nsPasteboard.clearContents();
            nsPasteboard.writeObjects(imageProxy);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to copy image to Clipboard", exception);
        }
    }

    @Override // net.labymod.api.client.os.OperatingSystemAccessor
    public GameImage getImageInClipboard() throws Throwable {
        byte[] imageBuffer;
        Client client = Client.getInstance();
        NSPasteboard nsPasteboard = NSPasteboard.generalPasteboard(client);
        Proxy pasteboardItems = nsPasteboard.getPasteboardItems();
        Proxy nsPasteboardItem = ClientUtil.objectAtIndex(pasteboardItems, 0);
        Collection<String> entries = ClientUtil.forEach(nsPasteboardItem, "types", (v0) -> {
            return v0.toString();
        });
        Proxy imageData = ClientUtil.findFirst(entries, nsPasteboardItem, "dataForType:").orElseThrow(() -> {
            return new IllegalStateException("Failed to find image data");
        });
        Object imageBytes = imageData.send("bytes", new Object[0]);
        int length = imageData.sendInt("length", new Object[0]);
        if (imageBytes instanceof Pointer) {
            Pointer pointer = (Pointer) imageBytes;
            imageBuffer = pointer.getByteArray(0L, length);
        } else if (imageBytes instanceof byte[]) {
            byte[] buffer = (byte[]) imageBytes;
            imageBuffer = buffer;
        } else {
            throw new IllegalStateException("Unsupported image type: " + String.valueOf(imageBytes.getClass()));
        }
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(imageBuffer, 0, length);
            try {
                GameImage image = GameImage.IMAGE_PROVIDER.getImage(ImageIO.read(stream));
                stream.close();
                return image;
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read image from Clipboard", exception);
        }
    }

    @Override // net.labymod.core.client.os.AbstractOperatingSystemAccessor, net.labymod.api.client.os.OperatingSystemAccessor
    public void setWindowIcon(long handle, InputStream stream) {
        try {
            String encodedData = Base64.getEncoder().encodeToString(stream.readAllBytes());
            Client client = Client.getInstance();
            Object nsData = ClientUtil.allocNSData(client).send("initWithBase64Encoding:", new Object[]{encodedData});
            Object nsImage = ClientUtil.allocNSImage(client).send("initWithData:", new Object[]{nsData});
            client.sendProxy("NSApplication", "sharedApplication", new Object[0]).send("setApplicationIconImage:", new Object[]{nsImage});
        } catch (IOException exception) {
            LOGGER.error("Failed to set window icon", exception);
        }
    }

    @Override // net.labymod.core.client.os.AbstractOperatingSystemAccessor
    public CredentialsAccessor credentialsAccessor() throws UnsupportedOperationException {
        return new MacOSCredentialsAccessor();
    }
}
