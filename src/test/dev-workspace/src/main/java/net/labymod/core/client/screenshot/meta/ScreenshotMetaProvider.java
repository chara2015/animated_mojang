package net.labymod.core.client.screenshot.meta;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.labymod.api.Constants;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/meta/ScreenshotMetaProvider.class */
public class ScreenshotMetaProvider {
    private static final Logging LOGGER = Logging.getLogger();
    private static final int VERSION = 3;
    private final Map<String, ScreenshotMeta> cache = new ConcurrentHashMap();
    private volatile boolean dirty;

    public void load() {
        this.cache.clear();
        this.dirty = false;
        Path path = Constants.Files.SCREENSHOT_META_CACHE;
        if (!Files.exists(path, new LinkOption[0])) {
            return;
        }
        try {
            DataInputStream in = new DataInputStream(Files.newInputStream(path, new OpenOption[0]));
            try {
                int version = in.readInt();
                if (version == 3) {
                    int entryCount = in.readInt();
                    for (int i = 0; i < entryCount; i++) {
                        String identifier = in.readUTF();
                        long timestamp = in.readLong();
                        ScreenshotMeta meta = new ScreenshotMeta(identifier, timestamp);
                        int attributeCount = in.readInt();
                        for (int j = 0; j < attributeCount; j++) {
                            String key = in.readUTF();
                            String value = in.readUTF();
                            meta.set(key, value);
                        }
                        this.cache.put(identifier, meta);
                    }
                    in.close();
                    return;
                }
                in.close();
                return;
            } finally {
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load screenshot meta cache", e);
        }
        LOGGER.error("Failed to load screenshot meta cache", e);
    }

    public ScreenshotMeta provide(Path path) throws IOException {
        String identifier = ScreenshotMeta.identifierOfPath(path);
        ScreenshotMeta cached = this.cache.get(identifier);
        if (cached != null) {
            return cached;
        }
        ScreenshotMeta meta = new ScreenshotMeta(path);
        this.cache.put(identifier, meta);
        this.dirty = true;
        return meta;
    }

    public void flush() {
        if (!this.dirty) {
            return;
        }
        this.dirty = false;
        Path target = Constants.Files.SCREENSHOT_META_CACHE;
        Path tempFile = target.resolveSibling(String.valueOf(target.getFileName()) + ".tmp");
        try {
            Path parent = target.getParent();
            if (parent != null) {
                IOUtil.createDirectories(parent);
            }
            OutputStream fileOut = Files.newOutputStream(tempFile, new OpenOption[0]);
            try {
                DataOutputStream out = new DataOutputStream(fileOut);
                try {
                    out.writeInt(3);
                    out.writeInt(this.cache.size());
                    for (Map.Entry<String, ScreenshotMeta> entry : this.cache.entrySet()) {
                        ScreenshotMeta meta = entry.getValue();
                        out.writeUTF(entry.getKey());
                        out.writeLong(meta.getTimestamp());
                        Map<String, String> attributes = meta.getAttributes();
                        out.writeInt(attributes.size());
                        for (Map.Entry<String, String> attr : attributes.entrySet()) {
                            out.writeUTF(attr.getKey());
                            out.writeUTF(attr.getValue());
                        }
                    }
                    out.close();
                    if (fileOut != null) {
                        fileOut.close();
                    }
                    IOUtil.atomicMove(tempFile, target);
                } catch (Throwable th) {
                    try {
                        out.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException e) {
            LOGGER.error("Failed to flush screenshot meta cache", e);
        }
    }
}
