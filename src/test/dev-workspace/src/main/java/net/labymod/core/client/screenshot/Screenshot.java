package net.labymod.core.client.screenshot;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.SimpleTexture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.scaler.ImageScaler;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.core.client.screenshot.meta.ScreenshotMetaProvider;
import net.labymod.core.labynet.insight.controller.InsightUploader;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/Screenshot.class */
public class Screenshot {
    private static final GameImageProvider IMAGE_PROVIDER = Laby.references().gameImageProvider();
    private static final Logging LOGGER = Logging.create((Class<?>) Screenshot.class);
    private final Path path;
    private ScreenshotMeta meta;
    private ResourceLocation resourceLocation;
    private boolean loaded = false;
    private Icon icon;
    private ContextMenu contextMenu;

    public Screenshot(Path path) {
        this.path = path;
    }

    public Screenshot(Path path, ScreenshotMeta meta) {
        this.path = path;
        this.meta = meta;
    }

    public void initialize(ScreenshotMetaProvider metaProvider) {
        try {
            this.meta = metaProvider.provide(this.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.contextMenu = new ContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.open", new Component[0])).clickHandler(entry -> {
            openInSystem();
            return true;
        }).build());
        if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
            this.contextMenu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.copy", new Component[0])).clickHandler(entry2 -> {
                copyToClipboard();
                return true;
            }).build());
        }
        this.contextMenu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.screenshotBrowser.context.upload", new Component[0])).clickHandler(entry3 -> {
            upload().thenAccept(url -> {
                if (url == null) {
                    return;
                }
                OperatingSystem.getPlatform().openUrl(url);
                Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
            });
            return true;
        }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).clickHandler(entry4 -> {
            PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.delete.warning", new Component[0]).arguments(Component.text(getFileName()))).confirmCallback(() -> {
                if (delete()) {
                    ScreenshotBrowser browser = LabyMod.references().screenshotBrowser();
                    browser.removeScreenshot(this);
                }
            }).build().displayInOverlay();
            return true;
        }).build());
    }

    public void load(QualityType quality) throws IOException {
        load(quality, null);
    }

    public void load(QualityType quality, final Runnable callback) throws IOException {
        ensureFreeSpace();
        InputStream inputStream = newInputStream();
        try {
            GameImage image = IMAGE_PROVIDER.getImage(inputStream);
            float aspectRatio = image.getWidth() / image.getHeight();
            boolean isRaw = quality == QualityType.RAW;
            GameImage scaledImage = image;
            if (!isRaw) {
                try {
                    scaledImage = image.scale(ImageScaler.BILINEAR, quality.getWidth(), quality.getHeight(aspectRatio));
                    image.close();
                } catch (Exception e) {
                    LOGGER.info("Could not scale down " + this.path.getFileName().toString() + " (from " + image.getWidth() + "x" + image.getHeight() + " to " + quality.getWidth() + "x" + quality.getHeight(aspectRatio) + ")", new Object[0]);
                    e.printStackTrace();
                }
            }
            ResourceLocation previousResource = this.resourceLocation;
            LabyAPI api = Laby.labyAPI();
            String path = String.format(Locale.ROOT, "screenshot/%s", getMeta().getIdentifier());
            this.resourceLocation = api.renderPipeline().resources().resourceLocationFactory().create(api.getNamespace(this), UUID.nameUUIDFromBytes(path.getBytes()).toString());
            this.icon = Icon.texture(this.resourceLocation);
            this.icon.resolution(scaledImage.getWidth(), scaledImage.getHeight());
            if (previousResource != null && this.loaded) {
                Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(previousResource);
            }
            Resources resources = api.renderPipeline().resources();
            final SimpleTexture texture = SimpleTexture.simple(this.resourceLocation, scaledImage);
            resources.textureRepository().register(this.resourceLocation, texture, new TextureRepository.TextureRegistrationCallback(this) { // from class: net.labymod.core.client.screenshot.Screenshot.1
                final /* synthetic */ Screenshot this$0;

                {
                    this.this$0 = this;
                }

                @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
                public void onBeforeTextureRegistration() {
                    texture.upload();
                }

                @Override // net.labymod.api.client.resources.texture.TextureRepository.TextureRegistrationCallback
                public void onAfterTextureRegistration() {
                    this.this$0.loaded = true;
                    if (callback != null) {
                        callback.run();
                    }
                }
            });
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private void ensureFreeSpace() {
        try {
            Runtime runtime = Runtime.getRuntime();
            long requiredSpace = Files.size(this.path);
            for (long freeMemory = runtime.freeMemory(); freeMemory < requiredSpace; freeMemory = runtime.freeMemory()) {
                System.gc();
                Thread.sleep(500L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unload() {
        if (this.resourceLocation == null) {
            return;
        }
        Laby.labyAPI().renderPipeline().resources().textureRepository().releaseTexture(this.resourceLocation);
        this.loaded = false;
    }

    public void updateQuality(QualityType quality) {
        try {
            load(quality);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openInSystem() {
        OperatingSystem.getPlatform().openFile(IOUtil.toFile(this.path));
    }

    public void copyToClipboard() {
        try {
            InputStream stream = newInputStream();
            try {
                GameImage image = IMAGE_PROVIDER.getImage(stream);
                Laby.labyAPI().operatingSystemAccessor().copyToClipboard(image);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (Throwable e) {
            e.printStackTrace();
            NotificationController notifications = LabyMod.references().notificationController();
            notifications.push(Notification.builder().title(Component.text(e.getClass().getSimpleName()).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
        }
    }

    public CompletableFuture<String> upload() {
        if (!getMeta().hasInsight()) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<String> future = new CompletableFuture<>();
        InsightUploader uploader = LabyMod.references().insightUploader();
        Path path = this.path;
        Objects.requireNonNull(future);
        uploader.uploadAsync(path, (v1) -> {
            r2.complete(v1);
        }, e -> {
            if (e == null) {
                future.complete(null);
                return;
            }
            e.printStackTrace();
            NotificationController notifications = LabyMod.references().notificationController();
            notifications.push(Notification.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.upload.error", new Component[0]).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
            future.completeExceptionally(e);
        });
        return future;
    }

    public boolean exists() {
        return IOUtil.exists(this.path);
    }

    public boolean delete() {
        try {
            Files.delete(this.path);
            LabyMod.references().screenshotBrowser().removeScreenshot(this);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            NotificationController notifications = LabyMod.references().notificationController();
            notifications.push(Notification.builder().title(Component.text(e.getClass().getSimpleName()).color(NamedTextColor.RED)).icon(Textures.SpriteCommon.PICTURE).text(Component.text(e.getMessage())).type(Notification.Type.SYSTEM).build());
            return false;
        }
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public Path getPath() {
        return this.path;
    }

    public ScreenshotMeta getMeta() {
        return this.meta;
    }

    public String getFileName() {
        return this.path.getFileName().toString();
    }

    public boolean isValid() {
        return this.path.getFileName().toString().endsWith(".png");
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public InputStream newInputStream() throws IOException {
        return IOUtil.newInputStream(this.path);
    }

    public GameImage asGameImage() throws IOException {
        return IMAGE_PROVIDER.getImage(newInputStream());
    }

    public Icon getIcon() {
        return this.icon;
    }

    public float getAspectRatio() {
        return 1.7777778f;
    }

    public ContextMenu getContextMenu() {
        return this.contextMenu;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Screenshot)) {
            return false;
        }
        Screenshot other = (Screenshot) obj;
        if (other.meta == null || this.meta == null) {
            return this.path.equals(other.path);
        }
        return this.meta.getIdentifier().equals(other.meta.getIdentifier());
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/Screenshot$QualityType.class */
    public enum QualityType {
        LOW(128),
        MEDIUM(512),
        HIGH(2048),
        RAW(-1);

        private final int width;

        QualityType(int width) {
            this.width = width;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight(float aspectRatio) {
            return (int) (this.width / aspectRatio);
        }
    }
}
