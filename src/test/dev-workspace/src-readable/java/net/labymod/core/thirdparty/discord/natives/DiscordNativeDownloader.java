package net.labymod.core.thirdparty.discord.natives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.util.io.zip.ZipException;
import net.labymod.api.util.io.zip.Zips;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/natives/DiscordNativeDownloader.class */
@Singleton
@Referenceable
public final class DiscordNativeDownloader {
    private static final Logging LOGGER = Logging.create("Native Controller");
    private static final int CONNECT_TIMEOUT = 5000;
    private static final String NAME = "discord_game_sdk";
    private static final String PATH = "lib/%s/%s%s";
    private final String zipPath;
    private final Path tempDiscordNatives;
    private final Path nativePath;
    private final Request<Path> nativeRequest;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v16, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request<java.nio.file.Path>] */
    @Inject
    public DiscordNativeDownloader() {
        OperatingSystem platform = OperatingSystem.getPlatform();
        String suffix = "." + platform.getLibraryExtensionName();
        String arch = platform.getArch();
        this.zipPath = String.format(Locale.ROOT, PATH, arch.equals("amd64") ? "x86_64" : arch, NAME, suffix);
        this.nativePath = Constants.Files.NATIVES.resolve("discord").resolve("discord_game_sdk" + suffix);
        this.tempDiscordNatives = Paths.get("./tmp_discord_natives.zip", new String[0]);
        try {
            IOUtil.deleteIfExits(this.tempDiscordNatives);
        } catch (IOException exception) {
            LOGGER.error("Temporary discord-natives file could not be deleted", exception);
        }
        this.nativeRequest = ((FileRequest) ((FileRequest) Request.ofFile(this.tempDiscordNatives).url(Constants.Urls.DISCORD_NATIVES, new Object[0])).connectTimeout(CONNECT_TIMEOUT)).async();
    }

    public void download(Consumer<Path> consumer) {
        if (consumer == null) {
            return;
        }
        if (IOUtil.exists(this.nativePath)) {
            consumer.accept(this.nativePath);
        } else {
            this.nativeRequest.execute(pathResponse -> {
                accept(pathResponse, consumer);
            });
        }
    }

    private void accept(Response<Path> pathResponse, Consumer<Path> consumer) {
        if (!pathResponse.isPresent()) {
            if (pathResponse.hasException()) {
                LOGGER.error("Something went wrong when downloading the natives ({})", pathResponse.exception());
                return;
            } else {
                LOGGER.error("Something went wrong when downloading the natives (Response is empty)", new Object[0]);
                return;
            }
        }
        int responseCode = pathResponse.getStatusCode() / 100;
        if (responseCode != 2) {
            return;
        }
        Path path = pathResponse.get();
        try {
            Zips.read(path, (entry, data) -> {
                String name = entry.getName();
                if (!name.equals(this.zipPath)) {
                    return false;
                }
                consumer.accept(createNativeDirectory(data));
                return true;
            });
            Files.deleteIfExists(this.tempDiscordNatives);
        } catch (IOException exception) {
            if (exception instanceof ZipException) {
                LOGGER.error("Something went wrong when reading the temporary file ({})", exception.getMessage());
            } else {
                LOGGER.error("Temporary file for natives could not be deleted ({})", exception.getMessage());
            }
        }
    }

    @NotNull
    private Path createNativeDirectory(byte[] data) throws IOException {
        if (IOUtil.exists(this.nativePath)) {
            return this.nativePath;
        }
        IOUtil.createDirectories(this.nativePath.getParent());
        Files.write(this.nativePath, data, new OpenOption[0]);
        return this.nativePath;
    }
}
