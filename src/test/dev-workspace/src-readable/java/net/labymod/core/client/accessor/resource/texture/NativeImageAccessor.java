package net.labymod.core.client.accessor.resource.texture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/accessor/resource/texture/NativeImageAccessor.class */
public interface NativeImageAccessor {
    void writeToStream(OutputStream outputStream) throws IOException;

    boolean isFreed();

    long getPointer();

    default byte[] toByteArray() throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                writeToStream(outputStream);
                byte[] byteArray = outputStream.toByteArray();
                outputStream.close();
                return byteArray;
            } finally {
            }
        } catch (IOException exception) {
            throw new IOException("Could not write image to byte array", exception);
        }
    }
}
