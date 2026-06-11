package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.util.CommonLinks;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/Eula.class */
public class Eula {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Path file;
    private final boolean agreed;

    public Eula(Path $$0) {
        this.file = $$0;
        this.agreed = SharedConstants.IS_RUNNING_IN_IDE || readFile();
    }

    private boolean readFile() {
        try {
            InputStream $$0 = Files.newInputStream(this.file, new OpenOption[0]);
            try {
                Properties $$1 = new Properties();
                $$1.load($$0);
                boolean z = Boolean.parseBoolean($$1.getProperty("eula", SnbtOperations.BUILTIN_FALSE));
                if ($$0 != null) {
                    $$0.close();
                }
                return z;
            } finally {
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to load {}", this.file);
            saveDefaults();
            return false;
        }
    }

    public boolean hasAgreedToEULA() {
        return this.agreed;
    }

    private void saveDefaults() {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            return;
        }
        try {
            OutputStream $$0 = Files.newOutputStream(this.file, new OpenOption[0]);
            try {
                Properties $$1 = new Properties();
                $$1.setProperty("eula", SnbtOperations.BUILTIN_FALSE);
                $$1.store($$0, "By changing the setting below to TRUE you are indicating your agreement to our EULA (" + String.valueOf(CommonLinks.EULA) + ").");
                if ($$0 != null) {
                    $$0.close();
                }
            } finally {
            }
        } catch (Exception $$2) {
            LOGGER.warn("Failed to save {}", this.file, $$2);
        }
    }
}
