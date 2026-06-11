package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/FileSystemUtil.class */
public class FileSystemUtil {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Path safeGetPath(URI $$0) throws IOException {
        try {
            return Paths.get($$0);
        } catch (FileSystemNotFoundException e) {
            try {
                FileSystems.newFileSystem($$0, (Map<String, ?>) Collections.emptyMap());
            } catch (FileSystemAlreadyExistsException e2) {
            }
            return Paths.get($$0);
        } catch (Throwable $$1) {
            LOGGER.warn("Unable to get path for: {}", $$0, $$1);
            FileSystems.newFileSystem($$0, (Map<String, ?>) Collections.emptyMap());
            return Paths.get($$0);
        }
    }
}
