package net.labymod.core.main.quicklauncher;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.accountmanager.storage.account.Account;
import net.labymod.api.BuildData;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.notification.Notification;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.SafeImageIO;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.quicklauncher.link.Link;
import net.labymod.core.main.quicklauncher.link.LinkFactory;
import net.labymod.core.main.updater.Updater;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/quicklauncher/QuickLauncher.class */
@Singleton
@Referenceable
public class QuickLauncher {
    private final LabyAPI labyAPI;
    private final Logging logging;
    private final Path quickLauncherPath = Paths.get(Constants.Files.QUICK_LAUNCHER_JAR_PATH, new String[0]);

    @Inject
    public QuickLauncher(LabyAPI labyAPI, Logging.Factory loggingFactory) {
        this.labyAPI = labyAPI;
        this.logging = loggingFactory.create(Updater.class);
    }

    public boolean iSupported() {
        return getProfile() != null && LinkFactory.isSupported(OperatingSystem.getPlatform());
    }

    public void createAsync(Account account) {
        Task.builder(() -> {
            try {
                create(account);
            } catch (Throwable e) {
                e.printStackTrace();
                Notification notification = Notification.builder().title(Component.translatable("labymod.activity.accountManager.title", new Component[0])).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build();
                Laby.references().notificationController().push(notification);
            }
        }).build().execute();
    }

    public void create(Account account) throws IOException {
        BufferedImage icon;
        UUID uuid = account.getUUID();
        Path javaPath = getCurrentJavaPath();
        String profile = getProfile();
        Path directory = Paths.get(Constants.Files.QUICK_LAUNCHER_DIRECTORY, new String[0]);
        if (!IOUtil.exists(directory)) {
            IOUtil.createDirectories(directory);
        }
        Link link = LinkFactory.create(OperatingSystem.getPlatform());
        if (link == null) {
            return;
        }
        try {
            String url = String.format(Locale.ROOT, Constants.Urls.LABYNET_HEAD, account.getUUID().toString());
            InputStream stream = new URL(url).openStream();
            try {
                icon = SafeImageIO.read(stream, 65536);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            String base64 = account.getAvatarImage();
            if (base64 == null) {
                icon = new BufferedImage(16, 16, 2);
            } else {
                icon = IOUtil.base64ToBufferedImage(base64);
            }
        }
        String[] command = {javaPath.toString(), "-jar", this.quickLauncherPath.toAbsolutePath().toString(), profile, uuid.toString(), Constants.Files.ACCOUNTS.toAbsolutePath().toString(), javaPath.toString()};
        Path linkPath = link.create(String.format(Locale.ROOT, "%s-%s", uuid, profile), directory, command, "LabyMod - " + account.getUsername(), icon);
        OperatingSystem.getPlatform().openFile(IOUtil.toFile(linkPath.getParent()));
        download();
        Notification notification = Notification.builder().title(Component.translatable("labymod.activity.accountManager.title", new Component[0])).text(Component.translatable("labymod.activity.accountManager.export.success", new Component[0])).type(Notification.Type.SYSTEM).build();
        Laby.references().notificationController().push(notification);
    }

    private String getProfile() {
        LabyModLoader loader = this.labyAPI.labyModLoader();
        if (loader.isLabyModDevelopmentEnvironment()) {
            return this.labyAPI.minecraft().getVersion();
        }
        return loader.getProfile().replace("-" + BuildData.commitReference(), "");
    }

    private Path getCurrentJavaPath() {
        String javaBin;
        String jvmLibraryPath = System.getProperty("sun.boot.library.path");
        if (jvmLibraryPath.contains(File.pathSeparator)) {
            javaBin = jvmLibraryPath.split(File.pathSeparator)[0];
        } else {
            String suffix = OperatingSystem.getPlatform() == OperatingSystem.WINDOWS ? ".exe" : "";
            javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java" + suffix;
        }
        return Paths.get(javaBin, new String[0]);
    }

    private void download() {
        Response<WebInputStream> response = Request.ofInputStream().url(Constants.LegacyUrls.QUICK_LAUNCHER, new Object[0]).executeSync();
        if (response.hasException()) {
            this.logging.error("An error occurred while downloading the quick launcher!", response.exception());
            return;
        }
        WebInputStream inputStream = response.get();
        if (inputStream.getContentLength() == 0) {
            return;
        }
        try {
            ReadableByteChannel channel = Channels.newChannel(inputStream.getInputStream());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(IOUtil.toFile(this.quickLauncherPath));
                try {
                    FileChannel fileChannel = fileOutputStream.getChannel();
                    fileChannel.transferFrom(channel, 0L, Long.MAX_VALUE);
                    fileOutputStream.close();
                    if (channel != null) {
                        channel.close();
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
        } catch (IOException e) {
            this.logging.error("An error occurred while downloading the updater jar!", e);
        }
    }
}
