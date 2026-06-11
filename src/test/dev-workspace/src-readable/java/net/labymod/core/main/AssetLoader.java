package net.labymod.core.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.updater.manifest.LabyModManifest;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/AssetLoader.class */
public class AssetLoader {
    private static final AssetLoader instance = new AssetLoader();
    private static final Path ASSET_DIRECTORY = Constants.Files.fromArguments("net.labymod.assets-dir", Constants.Files.LABYMOD_DIRECTORY.resolve("assets"));
    private static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) AssetLoader.class);
    private static final Map<String, LocalAsset> ASSETS = new HashMap();

    public static AssetLoader getInstance() {
        return instance;
    }

    public void loadAssets() {
        try {
            if (!Files.exists(ASSET_DIRECTORY, new LinkOption[0])) {
                Files.createDirectories(ASSET_DIRECTORY, new FileAttribute[0]);
            } else {
                Stream<Path> files = Files.list(ASSET_DIRECTORY);
                try {
                    files.forEach(path -> {
                        LocalAsset asset = LocalAsset.of(path);
                        ASSETS.put(asset.getName(), asset);
                        if (asset.getName().endsWith(".jar") && asset.isNotCorrupted()) {
                            PlatformEnvironment.getPlatformClassloader().addPath(path);
                        }
                    });
                    if (files != null) {
                        files.close();
                    }
                } finally {
                }
            }
        } catch (IOException e) {
            LOGGER.error("An exception occurred while loading the assets. Cause: {} ({})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public void downloadAssets(boolean async, LabyModManifest.Version version) {
        String commitReference = net.labymod.api.BuildData.commitReference();
        if (!version.getCommitReference().equals(commitReference)) {
            ASSETS.clear();
            return;
        }
        boolean downloaded = false;
        for (Map.Entry<String, String> entry : version.getAssets().entrySet()) {
            String assetName = entry.getKey();
            Path path = ASSET_DIRECTORY.resolve(assetName + ".jar");
            LocalAsset localAsset = ASSETS.get(path.getFileName().toString());
            if (localAsset == null || !IOUtil.exists(localAsset.getPath()) || !localAsset.isNotCorrupted()) {
                downloaded = true;
                downloadAsset(path, version.getCommitReference(), assetName, entry.getValue(), async);
            }
        }
        if (async && downloaded) {
            Laby.labyAPI().minecraft().executeNextTick(() -> {
                LabyMod.getInstance().minecraft().reloadResources();
            });
        }
        ASSETS.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void downloadAsset(Path path, String commitReference, String assetName, String assetHash, boolean async) {
        LOGGER.info("Downloading {}...", path);
        DefaultWebResolver.instance().resolveConnection(((FileRequest) ((FileRequest) Request.ofFile(path).url(Constants.Urls.ASSET_DOWNLOAD, net.labymod.api.BuildData.releaseType(), commitReference, assetName, assetHash)).addHeader("Release-Channel", (Object) net.labymod.api.BuildData.releaseType())).async(async), response -> {
            if (response.hasException()) {
                LOGGER.warn("Could not download asset {}. Cause: {}", assetName, response.exception().getMessage());
            } else {
                PlatformEnvironment.getPlatformClassloader().addPath(path);
            }
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/AssetLoader$LocalAsset.class */
    static class LocalAsset {
        private final String name;
        private final Path path;
        private final boolean corrupted;

        private LocalAsset(@NotNull Path path) {
            this.path = path;
            this.name = path.getFileName().toString();
            this.corrupted = IOUtil.isCorrupted(path);
            if (this.corrupted) {
                AssetLoader.LOGGER.warn("Corrupted file \"{}\" was found.", path);
            }
        }

        @Contract("_ -> new")
        @NotNull
        public static LocalAsset of(Path path) {
            return new LocalAsset(path);
        }

        public String getName() {
            return this.name;
        }

        public Path getPath() {
            return this.path;
        }

        public boolean isNotCorrupted() {
            return !this.corrupted;
        }
    }
}
