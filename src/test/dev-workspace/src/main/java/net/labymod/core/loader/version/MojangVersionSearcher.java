package net.labymod.core.loader.version;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.util.IsolatedClassLoader;
import net.labymod.api.util.function.ThrowableRunnable;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.version.parser.VersionManifestException;
import net.labymod.core.loader.version.parser.VersionParser;
import net.labymod.core.loader.version.reflect.VersionStringReflector;
import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/version/MojangVersionSearcher.class */
public class MojangVersionSearcher implements VersionSearcher {
    public static final Logging LOGGER = DefaultLoggingFactory.createLogger((Class<?>) MojangVersionSearcher.class);
    private static final VersionStringReflector V1_8_9 = new VersionStringReflector("net.minecraft.realms.RealmsSharedConstants", "VERSION_STRING");
    private static final VersionStringReflector V1_12_2 = new VersionStringReflector("net.minecraft.realms.RealmsSharedConstants", "VERSION_STRING");
    private static final VersionStringReflector[] REFLECTORS = {V1_8_9, V1_12_2};
    private final Collection<String> mainClasses = new HashSet();
    private final List<Throwable> throwables;
    private String version;
    private Path clientJarPath;

    public MojangVersionSearcher(String version) {
        this.mainClasses.add("net.minecraft.client.main.Main");
        this.throwables = new ArrayList();
        this.version = version;
    }

    @Override // net.labymod.core.loader.version.VersionSearcher
    public void searchClasspath(PlatformClassloader classLoader) {
        for (String mainClass : this.mainClasses) {
            URL classLocation = getClassLocation(classLoader, mainClass);
            if (classLocation != null) {
                safeExecute(() -> {
                    readClientJar(classLocation);
                });
            }
        }
        if (this.version != null) {
            this.throwables.clear();
            return;
        }
        StringBuilder errorBuilder = new StringBuilder();
        int errorIndex = 1;
        for (Throwable throwable : this.throwables) {
            errorBuilder.append("Error #").append(errorIndex);
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                errorBuilder.append("\t").append(stackTraceElement).append("\n");
            }
            errorIndex++;
        }
        throw new RuntimeException(errorBuilder.toString());
    }

    private void readClientJar(URL location) throws URISyntaxException, IOException {
        this.clientJarPath = Paths.get(location.toURI());
        if (Files.notExists(this.clientJarPath, new LinkOption[0])) {
            throw new IllegalStateException("Minecraft jar could not be found (" + String.valueOf(location) + ")");
        }
        IsolatedClassLoader loader = new IsolatedClassLoader();
        try {
            loader.addPath(this.clientJarPath);
            safeExecute(() -> {
                readVersionJson(loader);
            });
            for (VersionStringReflector reflector : REFLECTORS) {
                if (this.version != null) {
                    break;
                }
                safeExecute(() -> {
                    reflector.invoke(loader, this::setVersion);
                });
            }
            loader.close();
        } catch (Throwable th) {
            try {
                loader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void safeExecute(ThrowableRunnable<Throwable> runnable) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            this.throwables.add(throwable);
        }
    }

    private void readVersionJson(IsolatedClassLoader loader) {
        try {
            Enumeration<URL> resources = loader.getResources("version.json");
            while (resources.hasMoreElements() && this.version == null) {
                try {
                    VersionParser.parse(resources.nextElement(), this::setVersion);
                } catch (VersionManifestException exception) {
                    if (exception.getCause() == null) {
                        throw exception;
                    }
                    LOGGER.error("Parsing of the version manifest failed", exception);
                }
            }
        } catch (IOException exception2) {
            LOGGER.error("Version manifest could not be found", exception2);
        }
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override // net.labymod.core.loader.version.VersionSearcher
    public String getMinecraftVersion() {
        return this.version;
    }

    @Override // net.labymod.core.loader.version.VersionSearcher
    public Path getClientJarPath() {
        return this.clientJarPath;
    }

    @Override // net.labymod.core.loader.version.VersionSearcher
    public Collection<String> getMainClasses() {
        return this.mainClasses;
    }

    @Nullable
    private URL getClassLocation(@NotNull PlatformClassloader loader, @NotNull String className) {
        String suffix = className.replace('.', '/') + ".class";
        URL classResource = loader.getPlatformClassloader().getResource(suffix);
        if (classResource == null) {
            return null;
        }
        String url = classResource.toString();
        if (!url.endsWith(suffix)) {
            return null;
        }
        String path = url.substring(0, url.length() - suffix.length());
        if (path.startsWith("jar:")) {
            path = path.substring(4, path.length() - 2);
        }
        try {
            return new URL(path);
        } catch (Exception e) {
            return null;
        }
    }
}
