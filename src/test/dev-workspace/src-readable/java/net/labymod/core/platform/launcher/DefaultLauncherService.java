package net.labymod.core.platform.launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.models.Implements;
import net.labymod.api.models.version.Version;
import net.labymod.api.platform.launcher.LauncherService;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.platform.launcher.communication.LauncherCommunicationClient;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/launcher/DefaultLauncherService.class */
@Singleton
@Implements(LauncherService.class)
public class DefaultLauncherService implements LauncherService {
    private static final Logging LOGGER = Logging.create((Class<?>) LauncherService.class);
    private final LauncherCommunicationClient communicator;
    private final String launcher;
    private final String launcherVersion;
    private final String modPackId;
    private final String modPackName;
    private final Version modPackVersion;
    private final String modLoader;
    private final UUID gameSessionId;
    private final boolean usingLabyModLauncher;

    public DefaultLauncherService() {
        boolean devEnvironment = DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment();
        String launcher = System.getProperty("net.labymod.launcher");
        launcher = launcher == null ? System.getProperty("minecraft.launcher.brand") : launcher;
        this.launcher = launcher != null ? launcher.replace("\"", "") : launcher;
        this.launcherVersion = System.getProperty("minecraft.launcher.version");
        this.usingLabyModLauncher = devEnvironment || (this.launcher != null && this.launcher.equalsIgnoreCase("labymod-launcher"));
        this.gameSessionId = getGameSessionIdFromProperties(devEnvironment);
        this.modPackId = System.getProperty("net.labymod.mod-pack-id");
        this.modLoader = System.getProperty("net.labymod.mod-loader");
        String modPackName = System.getProperty("net.labymod.mod-pack-name");
        this.modPackName = modPackName == null ? this.modPackId : modPackName;
        String modPackVersion = System.getProperty("net.labymod.mod-pack-version");
        this.modPackVersion = (modPackVersion == null || !isUsingModPack()) ? null : VersionDeserializer.from(modPackVersion);
        LauncherCommunicationClient client = null;
        if (this.usingLabyModLauncher && this.gameSessionId != null) {
            String rawPort = System.getProperty("net.labymod.launcher.port");
            if (devEnvironment && rawPort == null) {
                rawPort = "1337";
            }
            if (rawPort == null) {
                LOGGER.warn("Using LabyMod Launcher but no communication port was specified.", new Object[0]);
            } else {
                Integer port = null;
                try {
                    port = Integer.valueOf(Integer.parseInt(rawPort));
                } catch (NumberFormatException e) {
                    LOGGER.error("Failed to parse launcher communication port from system properties", e);
                }
                if (port != null && port.intValue() > 0 && port.intValue() < 65536) {
                    client = new LauncherCommunicationClient(this.gameSessionId.toString(), port.intValue());
                    client.start();
                } else {
                    LOGGER.warn("Invalid launcher communication port specified: " + rawPort, new Object[0]);
                }
            }
        }
        this.communicator = client;
    }

    public static DefaultLauncherService getInstance() {
        return (DefaultLauncherService) Laby.references().launcherService();
    }

    @Nullable
    public LauncherCommunicationClient getCommunicator() {
        return this.communicator;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public String getLauncher() {
        return this.launcher;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public String getLauncherVersion() {
        return this.launcherVersion;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public UUID getGameSessionId() {
        return this.gameSessionId;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public String getModPackId() {
        return this.modPackId;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public String getModPackName() {
        return this.modPackName;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    @Nullable
    public Version getModPackVersion() {
        return this.modPackVersion;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    public boolean isUsingLabyModLauncher() {
        return this.usingLabyModLauncher;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    public boolean isConnectedToLauncher() {
        return this.communicator != null && this.communicator.isConnected();
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    public boolean isUsingModLoader() {
        return this.modLoader != null;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    public String getModLoader() {
        return this.modLoader;
    }

    @Override // net.labymod.api.platform.launcher.LauncherService
    public void restart() {
        if (this.gameSessionId == null) {
            throw new IllegalStateException("The game session id is null. Was LabyMod started using the LabyMod Launcher (v1.0.28 or newer)?");
        }
        List<String> uniqueIds = new ArrayList<>();
        uniqueIds.add(this.gameSessionId.toString());
        if (IOUtil.exists(Constants.Files.RESTART)) {
            try {
                uniqueIds.addAll(Files.readAllLines(Constants.Files.RESTART));
            } catch (IOException e) {
                LOGGER.warn("Failed to read restart file", e);
            }
        }
        try {
            Files.write(Constants.Files.RESTART, uniqueIds, new OpenOption[0]);
        } catch (IOException e2) {
            LOGGER.warn("Failed to write restart file", e2);
        }
        Laby.labyAPI().minecraft().shutdownGame();
    }

    private UUID getGameSessionIdFromProperties(boolean devEnvironment) {
        String gameSessionIdString = System.getProperty("net.labymod.game-session-id");
        UUID gameSessionId = null;
        if (gameSessionIdString != null) {
            try {
                gameSessionId = UUID.fromString(gameSessionIdString);
            } catch (Exception e) {
            }
        }
        if (devEnvironment) {
            gameSessionId = UUID.randomUUID();
        }
        if (this.usingLabyModLauncher) {
            return gameSessionId;
        }
        return null;
    }
}
