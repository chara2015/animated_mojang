package net.labymod.api.configuration.loader.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/impl/AbstractConfigLoader.class */
public abstract class AbstractConfigLoader implements ConfigLoader {
    protected final Path directory;
    protected final Map<String, String> variables = new HashMap();

    public AbstractConfigLoader(Path directory) {
        this.directory = directory;
    }

    public Path getPath(Class<? extends ConfigAccessor> clazz) {
        String namespace = Laby.labyAPI().getNamespace((Class<?>) clazz);
        return this.directory.resolve(namespace).resolve(String.format(Locale.ROOT, "%s.%s", replaceVariables(getRelativePath(clazz)), getFileExtension()));
    }

    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public void setVariable(String key, Object value) {
        this.variables.put(key, value.toString());
    }

    protected String replaceVariables(String configPath) {
        for (Map.Entry<String, String> entry : this.variables.entrySet()) {
            configPath = configPath.replace(entry.getKey(), entry.getValue());
        }
        return configPath;
    }

    public static Path defaultDirectory() {
        return Constants.Files.CONFIGS;
    }

    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public void invalidate(Class<? extends ConfigAccessor> type) throws IOException {
        Path path = getPath(type);
        Path invalidPath = path.getParent().resolve(path.getFileName().toString() + ".invalid");
        Files.copy(path, invalidPath, StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(path);
    }
}
