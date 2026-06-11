package net.labymod.core.main.quicklauncher.link;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import net.labymod.api.util.io.IOUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/quicklauncher/link/Link.class */
public interface Link {
    Path create(String str, Path path, String[] strArr, String str2, BufferedImage bufferedImage) throws IOException;

    default void writeToFile(Path path, String content) throws IOException {
        writeToFile(path, content.getBytes());
    }

    default void writeToFile(Path path, byte[] bytes) throws IOException {
        OutputStream output = IOUtil.newOutputStream(path);
        output.write(bytes);
        output.close();
    }

    default String buildCommand(String[] command, String wrapped, String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (String arg : command) {
            if (builder.length() > 0) {
                builder.append(delimiter);
            }
            builder.append(wrapped).append(arg).append(wrapped);
        }
        return builder.toString();
    }
}
