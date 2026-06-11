package net.labymod.core.main.updater;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.BuildData;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.ShutdownEvent;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.version.Version;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Callback;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.core.main.AssetLoader;
import net.labymod.core.main.updater.manifest.LabyModManifest;
import net.labymod.core.main.updater.manifest.UpdaterManifest;
import net.labymod.core.main.util.JavaLauncher;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/Updater.class */
@Singleton
@Referenceable
public class Updater {
    private static final String RELEASE_CHANNEL_KEY = "Release-Channel";
    private final Request<LabyModManifest.Version> labyModManifestRequest;
    private final Request<UpdaterManifest> updaterManifestRequest;
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final Path updaterPath;
    private final boolean useUpdater;
    private boolean foundUpdate;
    private Task updaterTask;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v15, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request<net.labymod.core.main.updater.manifest.LabyModManifest$Version>] */
    /* JADX WARN: Type inference failed for: r1v22, types: [net.labymod.api.util.io.web.request.AbstractRequest, net.labymod.api.util.io.web.request.Request<net.labymod.core.main.updater.manifest.UpdaterManifest>] */
    @Inject
    public Updater(LabyAPI labyAPI, Logging.Factory loggingFactory) {
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(Updater.class);
        this.labyAPI.eventBus().registerListener(this);
        String releaseType = BuildData.releaseType();
        this.updaterPath = new File(String.format(Locale.ROOT, Constants.Files.UPDATER_PATH, releaseType)).toPath();
        this.useUpdater = !Laby.references().launcherService().isUsingLabyModLauncher();
        if (this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment()) {
            this.labyModManifestRequest = null;
            this.updaterManifestRequest = null;
        } else {
            this.labyModManifestRequest = ((GsonRequest) Request.ofGson(LabyModManifest.Version.class).url(Constants.Urls.LABYMOD_MANIFEST, releaseType)).addHeader(RELEASE_CHANNEL_KEY, (Object) releaseType);
            this.updaterManifestRequest = ((GsonRequest) ((GsonRequest) Request.ofGson(UpdaterManifest.class).url(Constants.Urls.UPDATER_MANIFEST, releaseType)).addHeader(RELEASE_CHANNEL_KEY, (Object) releaseType)).async();
        }
    }

    @Subscribe
    public void shutdown(ShutdownEvent event) {
        executeUpdater(event);
    }

    public void initialize() {
        if (this.labyAPI.labyModLoader().isLabyModDevelopmentEnvironment()) {
            return;
        }
        saveUpdaterInformation();
        this.updaterTask = Task.builder(() -> {
            ZipFile zipFile;
            Enumeration<? extends ZipEntry> entries;
            if (!IOUtil.exists(this.updaterPath) || !this.useUpdater) {
                resolveUpdaterManifest(this.labyAPI.labyModLoader().version(), true);
                return;
            }
            Version currentVersion = null;
            try {
                zipFile = new ZipFile(IOUtil.toFile(this.updaterPath));
                try {
                    entries = zipFile.entries();
                } finally {
                }
            } catch (IOException exception) {
                this.logging.error("An error occurred while reading the jar!", exception);
            }
            while (true) {
                if (!entries.hasMoreElements()) {
                    break;
                }
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.getName().equals("build-data.json")) {
                    break;
                }
                this.logging.error("An error occurred while reading the jar!", exception);
                if (currentVersion != null) {
                    try {
                        this.logging.info("No build information about the updater was found!", new Object[0]);
                        Files.delete(this.updaterPath);
                        resolveUpdaterManifest(this.labyAPI.labyModLoader().version(), true);
                        return;
                    } catch (IOException exception2) {
                        this.logging.error("An error occurred while deleting the jar!", exception2);
                        return;
                    }
                }
                resolveUpdaterManifest(currentVersion, false);
                return;
            }
            zipFile.close();
            if (currentVersion != null) {
            }
        }).build();
        this.updaterTask.execute();
        this.labyModManifestRequest.execute(new LabyModManifestResponse());
    }

    private void executeUpdater(ShutdownEvent event) {
        LabyModLoader loader = this.labyAPI.labyModLoader();
        if (loader.isLabyModDevelopmentEnvironment() || !this.useUpdater) {
            return;
        }
        boolean crash = event.isCrash();
        if (!crash && !this.foundUpdate) {
            this.logging.info("Not executing the updater as no update was found!", new Object[0]);
            return;
        }
        Logging logging = this.logging;
        Object[] objArr = new Object[1];
        objArr[0] = crash ? "crashreporter" : "updater";
        logging.info("Executing {}...", objArr);
        List<String> arguments = new ArrayList<>();
        arguments.add("-jar");
        arguments.add(this.updaterPath.toAbsolutePath().toString());
        arguments.add("--application-type");
        arguments.add(crash ? "crashreporter" : "updater");
        if (crash) {
            ShutdownEvent.CrashContext context = (ShutdownEvent.CrashContext) event.context();
            Path path = context.crashReport();
            if (path != null) {
                arguments.add("--crash-log");
                arguments.add(path.toAbsolutePath().toString());
            }
        }
        arguments.add("--launcher-type");
        arguments.add(Laby.references().launcherService().getLauncherOrDefault("microsoft"));
        arguments.add("--gd");
        arguments.add(loader.getAssetsDirectory().toAbsolutePath().getParent().toString());
        arguments.add("--wd");
        arguments.add(loader.getGameDirectory().toAbsolutePath().toString());
        JavaLauncher.launch(arguments);
    }

    public boolean hasFoundUpdate() {
        return this.foundUpdate;
    }

    private void resolveUpdaterManifest(Version currentVersion, boolean download) {
        if (!this.useUpdater) {
            return;
        }
        this.updaterManifestRequest.execute(new UpdaterManifestResponse(currentVersion, download));
    }

    private void downloadUpdater(String version) {
        ReadableByteChannel channel;
        if (!this.useUpdater) {
            return;
        }
        String releaseType = BuildData.releaseType();
        Response<WebInputStream> response = Request.ofInputStream().url(Constants.Urls.UPDATER_DOWNLOAD, releaseType, version).addHeader(RELEASE_CHANNEL_KEY, (Object) releaseType).executeSync();
        if (response.hasException()) {
            this.logging.error("An error occurred while downloading the updater!", response.exception());
            return;
        }
        WebInputStream inputStream = response.get();
        if (inputStream.getContentLength() == 0) {
            return;
        }
        try {
            channel = Channels.newChannel(inputStream.getInputStream());
        } catch (IOException e) {
            this.logging.error("An error occurred while downloading the updater jar!", e);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(IOUtil.toFile(this.updaterPath));
            try {
                FileChannel fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(channel, 0L, Long.MAX_VALUE);
                fileOutputStream.close();
                if (channel != null) {
                    channel.close();
                }
                if (this.updaterTask != null) {
                    this.updaterTask.cancel();
                    this.updaterTask = null;
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
        }
    }

    private void saveUpdaterInformation() {
        if (!this.useUpdater) {
            return;
        }
        LabyModLoader labyModLoader = this.labyAPI.labyModLoader();
        if (labyModLoader.getGameDirectory() == null || labyModLoader.getAssetsDirectory() == null) {
            return;
        }
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("gameDirectory", labyModLoader.getAssetsDirectory().getParent().toAbsolutePath().toString());
            byte[] newInformation = GsonUtil.DEFAULT_GSON.toJson(jsonObject).getBytes(StandardCharsets.UTF_8);
            Path path = Constants.Files.LABYMOD_DIRECTORY.resolve("updater-info.json");
            if (Files.notExists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                Files.createFile(path, new FileAttribute[0]);
            } else {
                try {
                    String currentInformation = new String(Files.readAllBytes(path));
                    if (currentInformation.equals(new String(newInformation))) {
                        return;
                    }
                } catch (Exception e) {
                }
            }
            Files.write(path, newInformation, new OpenOption[0]);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/Updater$UpdaterManifestResponse.class */
    final class UpdaterManifestResponse implements Callback<UpdaterManifest> {
        private final Version currentVersion;
        private final boolean download;

        public UpdaterManifestResponse(Version currentVersion, boolean download) {
            this.currentVersion = currentVersion;
            this.download = download;
        }

        @Override // java.util.function.Consumer
        public void accept(Response<UpdaterManifest> response) {
            if (response.hasException()) {
                Updater.this.logging.error("An error occurred when requesting the current update information", response.exception());
                return;
            }
            UpdaterManifest updaterManifest = response.get();
            Version latestVersion = VersionDeserializer.from(updaterManifest.getLatest());
            if (this.download) {
                Updater.this.downloadUpdater(updaterManifest.getLatest());
            } else if (this.currentVersion.isLowerThan(latestVersion)) {
                Updater.this.downloadUpdater(updaterManifest.getLatest());
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/updater/Updater$LabyModManifestResponse.class */
    final class LabyModManifestResponse implements Callback<LabyModManifest.Version> {
        LabyModManifestResponse() {
        }

        @Override // java.util.function.Consumer
        public void accept(Response<LabyModManifest.Version> response) {
            if (response.hasException()) {
                Updater.this.logging.error("An error occurred while requesting the manifest!", response.exception());
                return;
            }
            if (!response.isPresent()) {
                return;
            }
            LabyModManifest.Version latest = response.get();
            AssetLoader.getInstance().downloadAssets(Laby.labyAPI().isFullyInitialized(), latest);
            String labyModVersion = latest.getLabyModVersion();
            Version version = VersionDeserializer.from(labyModVersion);
            if (version.isGreaterThan(BuildData.version())) {
                Updater.this.foundUpdate = true;
            }
            if (!latest.getCommitReference().equals(BuildData.commitReference())) {
                Updater.this.foundUpdate = true;
            }
            if (Updater.this.foundUpdate) {
                Updater.this.logging.info("Found a new labymod update!", new Object[0]);
            }
        }
    }
}
