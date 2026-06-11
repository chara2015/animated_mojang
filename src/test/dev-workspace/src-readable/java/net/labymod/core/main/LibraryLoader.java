package net.labymod.core.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.addon.info.dependency.MavenDependency;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.util.io.web.connection.DefaultWebResolver;
import net.labymod.core.util.logging.DefaultLoggingFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/LibraryLoader.class */
public class LibraryLoader {
    private static final DefaultWebResolver WEB_RESOLVER = DefaultWebResolver.instance();
    private final Logging logger = DefaultLoggingFactory.createLogger((Class<?>) LibraryLoader.class);
    private final PlatformClassloader platformClassloader = PlatformEnvironment.getPlatformClassloader();

    public void loadLabyModLibrary() {
        try {
            JsonObject jsonObject = downloadManifest();
            JsonArray<JsonElement> libraries = jsonObject.get("libraries").getAsJsonArray();
            for (JsonElement library : libraries) {
                JsonObject object = library.getAsJsonObject();
                String name = object.get("name").getAsString();
                String[] names = name.split(":");
                String url = object.get("url").getAsString();
                String path = String.format(Locale.ROOT, "%s;%s;%s", names[0].replace(".", ";"), names[1], names[2]).replace(";", FileSystems.getDefault().getSeparator());
                String fileName = String.format(Locale.ROOT, "%s-%s.jar", names[1], names[2]);
                try {
                    downloadAndAddToClassPath(path, fileName, url);
                } catch (IOException e) {
                    this.logger.error("Could not download {} library {} from {}. Cause: {}", Constants.Branding.NAME, name, url, e.getMessage());
                    System.exit(1);
                    return;
                }
            }
        } catch (IOException e2) {
            this.logger.error("Could not download manifest", e2.getMessage());
            System.exit(1);
        }
    }

    private JsonObject downloadManifest() throws IOException {
        Response<JsonObject> response = WEB_RESOLVER.resolveConnection(Request.ofGson(JsonObject.class).url(Constants.Urls.LIBRARY_MANIFEST, net.labymod.api.BuildData.releaseType()));
        if (response.hasException()) {
            throw response.exception();
        }
        return response.get();
    }

    public Path loadMavenDependency(MavenDependency mavenDependency) throws IOException {
        return downloadAndAddToClassPath(mavenDependency.buildFileDictionary(), mavenDependency.buildFileName(), mavenDependency.buildURL());
    }

    private Path downloadAndAddToClassPath(String filePath, String fileName, String downloadUrl) throws IOException {
        Path path = Constants.Files.LIBRARIES.resolve(filePath).resolve(fileName);
        if (!IOUtil.exists(path)) {
            this.logger.info("Downloading library {}...", fileName.replace(".jar", ""));
            downloadLibrary(downloadUrl, path);
            this.platformClassloader.addPath(path);
        } else if (!isLibraryOnClasspath(path)) {
            this.platformClassloader.addPath(path);
        }
        return path;
    }

    private boolean isLibraryOnClasspath(Path path) {
        String allClassPaths = System.getProperty("java.class.path");
        String[] classPaths = allClassPaths.split(";");
        for (String classPath : classPaths) {
            if (classPath.equals(path.toAbsolutePath().toString())) {
                return true;
            }
        }
        return false;
    }

    private void downloadLibrary(String url, Path destination) throws IOException {
        IOUtil.createDirectories(destination.getParent());
        Response<Path> response = WEB_RESOLVER.resolveConnection(Request.ofFile(destination).url(url, new Object[0]));
        if (response.hasException()) {
            throw response.exception();
        }
    }
}
