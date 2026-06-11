package net.labymod.core.util.classpath;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/classpath/ClasspathUtil.class */
public final class ClasspathUtil {
    public static List<String> rootResources(String addonId, Class<?> callerCls) throws IOException {
        List<String> names = new ArrayList();
        String jarPath = getJarPath(callerCls);
        if (jarPath.endsWith(".jar")) {
            names = (List) getJarEntryNames(jarPath, "").stream().filter(name -> {
                return !name.contains("/");
            }).collect(Collectors.toList());
        } else {
            InputStream inputStream = getResourceAsInputStream(addonId, "", false);
            try {
                BufferedReader resourceReader = new BufferedReader(new InputStreamReader(inputStream));
                while (true) {
                    try {
                        String resource = resourceReader.readLine();
                        if (resource == null) {
                            break;
                        }
                        if (resource.contains(".")) {
                            names.add(resource);
                        }
                    } finally {
                    }
                }
                resourceReader.close();
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
        return names;
    }

    public static InputStream getResourceAsInputStream(String addonId, String path) throws IOException {
        return getResourceAsInputStream(addonId, path, true);
    }

    public static InputStream getResourceAsInputStream(String addonId, String path, boolean shouldScanAddons) throws IOException {
        URL resource = null;
        if (shouldScanAddons) {
            Optional<LoadedAddon> optionalAddon = LabyMod.getInstance().addonService().getOptionalAddon(addonId);
            if (optionalAddon.isPresent()) {
                LoadedAddon loadedAddon = optionalAddon.get();
                if (!(loadedAddon.getClassLoader() instanceof AddonClassLoader)) {
                    throw new IOException(String.format(Locale.ROOT, "The loaded addon %s has a different class loader %s", addonId, loadedAddon.getClassLoader().getClass().getName()));
                }
                resource = loadedAddon.getClassLoader().getResource(path);
            }
        }
        if (resource == null) {
            resource = ClasspathUtil.class.getClassLoader().getResource(path);
        }
        if (resource == null) {
            throw new FileNotFoundException("No resource was found at this location: (" + path + ")");
        }
        return resource.openStream();
    }

    @NotNull
    public static List<String> getJarEntryNames(String jarPath, String path) throws IOException {
        List<String> paths = new ArrayList<>();
        JarFile file = new JarFile(jarPath);
        try {
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (path == null || path.isEmpty()) {
                    paths.add(entry.getName());
                } else if (entry.getName().startsWith(path)) {
                    paths.add(entry.getName());
                }
            }
            file.close();
            return paths;
        } catch (Throwable th) {
            try {
                file.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static String getJarPath(Class<?> cls) {
        try {
            return cls.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
