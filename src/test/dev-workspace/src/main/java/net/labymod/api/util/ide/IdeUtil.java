package net.labymod.api.util.ide;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.function.ThrowableRunnable;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.util.logging.Logging;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ide/IdeUtil.class */
public final class IdeUtil {
    private static final int IDE_CRASH_EXIT_CODE = -69420;
    public static final boolean RUNNING_IN_IDE = Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment();
    private static final StackWalker WALKER = StackWalker.getInstance();
    public static final boolean DUMP_SPRITE_ICONS = SystemProperties.DUMP_SPRITES.get().booleanValue();
    public static final Logging LOGGER = Logging.create("IDE");
    private static final Map<ResourceLocation, GameImage> OLD_SPRITE_SHEETS = new HashMap();
    private static final boolean INCOMPATIBLE_RESOURCE_LOADING = MinecraftVersions.V1_16_5.isCurrent();
    private static boolean resourcesLoaded;
    private static boolean alreadyCrashed;

    private IdeUtil() {
    }

    public static void doPauseOrThrown(ThrowableSupplier<Throwable, Throwable> throwableSupplier) {
        _doPause(() -> {
            throw ((Throwable) throwableSupplier.get());
        });
    }

    public static void ensureResourcesLoaded(ResourceLocation location) {
        if (DUMP_SPRITE_ICONS || !RUNNING_IN_IDE || INCOMPATIBLE_RESOURCE_LOADING || resourcesLoaded) {
            return;
        }
        crashGame("\"" + String.valueOf(location) + "\" was loaded too early, please use \"" + ResourcesReloadWatcher.class.getName() + "\" to avoid issues in the launcher environment.", new IdeException());
    }

    public static void dumpSpriteIcons(String spriteLocation, Icon icon) {
        if (!DUMP_SPRITE_ICONS) {
            return;
        }
        String namespace = Laby.labyAPI().addonService().getClassPathAddon();
        if (namespace == null) {
            namespace = "labymod";
        }
        ResourceLocation location = icon.getResourceLocation();
        Objects.requireNonNull(location, "Icon resource location cannot be null");
        String locationNamespace = location.getNamespace();
        if (!locationNamespace.equals(namespace)) {
            return;
        }
        Sprite sprite = icon.sprite();
        GameImage image = OLD_SPRITE_SHEETS.computeIfAbsent(location, l -> {
            try {
                return GameImage.IMAGE_PROVIDER.getImage(l.openStream());
            } catch (IOException exception) {
                throw new UncheckedIOException(exception);
            }
        });
        GameImage spriteImage = GameImage.IMAGE_PROVIDER.createImage((int) sprite.getWidth(), (int) sprite.getHeight());
        spriteImage.drawImage(image, 0, 0, (int) sprite.getX(), (int) sprite.getY(), (int) sprite.getWidth(), (int) sprite.getHeight());
        Theme currentTheme = Laby.references().themeService().currentTheme();
        try {
            String spriteLocation2 = spriteLocation.toLowerCase(Locale.ROOT).replace(".", "/");
            if (!spriteLocation2.startsWith(namespace)) {
                spriteLocation2 = namespace + "/" + spriteLocation2;
            }
            Path destination = Constants.Files.LABYMOD_DEBUG_DIRECTORY.resolve("settings_icons").resolve(currentTheme.getId()).resolve(spriteLocation2 + ".png");
            Files.createDirectories(destination.getParent(), new FileAttribute[0]);
            spriteImage.write("png", destination);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    @ApiStatus.Internal
    public static void setResourcesLoaded(boolean resourcesLoaded2) {
        resourcesLoaded = resourcesLoaded2;
    }

    public static void doPause(ThrowableRunnable<Throwable> executor) {
        _doPause(executor);
    }

    public static void doPauseOrCrashGame(String message, Throwable throwable) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            doPauseOrThrown(() -> {
                return throwable;
            });
        } else {
            _doPause(() -> {
                minecraft.crashGame(message, throwable);
            });
        }
    }

    private static void _doPause(ThrowableRunnable<Throwable> crashExecutor) {
        if (alreadyCrashed) {
            return;
        }
        boolean shouldCrash = true;
        if (RUNNING_IN_IDE) {
            Instant start = Instant.now();
            printBreakpointMessage();
            shouldCrash = Duration.between(start, Instant.now()).toMillis() < 500;
        }
        if (!shouldCrash) {
            alreadyCrashed = false;
            return;
        }
        alreadyCrashed = true;
        try {
            crashExecutor.run();
        } catch (Throwable throwable) {
            crashGame(throwable.getMessage(), throwable);
        }
    }

    private static void printBreakpointMessage() {
        LOGGER.error("Did you remember to set a breakpoint here?", new Object[0]);
    }

    private static void crashGame(String message, Throwable throwable) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            throwable.printStackTrace();
            System.exit(IDE_CRASH_EXIT_CODE);
        } else {
            if (message == null) {
                message = "Unknown Message";
            }
            minecraft.crashGame(message, throwable);
        }
    }

    public static List<StackTraceElement> getStackTrace() {
        return (List) WALKER.walk(stackFrameStream -> {
            return stackFrameStream.skip(2L).map((v0) -> {
                return v0.toStackTraceElement();
            }).toList();
        });
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/ide/IdeUtil$IdeException.class */
    private static class IdeException extends RuntimeException {
        public IdeException() {
        }

        public IdeException(String message) {
            super(message);
        }

        public IdeException(String message, Throwable cause) {
            super(message, cause);
        }

        public IdeException(Throwable cause) {
            super(cause);
        }

        public IdeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
