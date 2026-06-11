package net.minecraft.data;

import com.google.common.hash.HashCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.minecraft.util.FileUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/data/CachedOutput.class */
public interface CachedOutput {
    public static final CachedOutput NO_CACHE = ($$0, $$1, $$2) -> {
        FileUtil.createDirectoriesSafe($$0.getParent());
        Files.write($$0, $$1, new OpenOption[0]);
    };

    void writeIfNeeded(Path path, byte[] bArr, HashCode hashCode) throws IOException;
}
