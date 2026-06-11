package net.labymod.api.configuration.converter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/converter/LegacyConverter.class */
public abstract class LegacyConverter<T> {
    private static final Logging LOGGER = Logging.create((Class<?>) LegacyConverter.class);
    public static final Path LEGACY_PATH = Paths.get(Constants.Branding.NAME, new String[0]);
    protected final Class<? extends T> type;
    protected Path path;
    private T value;
    private Exception exception;
    private boolean loaded;
    protected final Gson gson;

    protected abstract void convert(T t) throws Exception;

    public abstract boolean hasStuffToConvert() throws Exception;

    protected LegacyConverter(String fileName, Class<? extends T> type) {
        this(LEGACY_PATH.resolve(fileName), type);
    }

    protected LegacyConverter(Path path, Class<? extends T> type) {
        this.path = path;
        this.type = type;
        this.gson = createGson();
    }

    protected Gson createGson() {
        return new Gson();
    }

    public boolean isEmpty() {
        return this.value == null && this.exception == null;
    }

    @Nullable
    public T getValue() {
        return this.value;
    }

    @Nullable
    public Exception getException() {
        return this.exception;
    }

    @NotNull
    public final String getNamespace() {
        return Laby.labyAPI().getNamespace(this);
    }

    public boolean wasLoaded() {
        return this.loaded;
    }

    public final void load() {
        Objects.requireNonNull(this.path, "Path is null");
        this.loaded = true;
        if (!IOUtil.exists(this.path)) {
            this.value = null;
            this.exception = null;
            return;
        }
        try {
            T t = (T) fromJson(new String(Files.readAllBytes(this.path), StandardCharsets.UTF_8), this.type);
            load(t);
            this.value = t;
            this.exception = null;
        } catch (Exception e) {
            this.value = null;
            this.exception = e;
        }
    }

    public final void convert() throws Exception {
        if (this.exception != null) {
            throw this.exception;
        }
        if (this.value == null) {
            throw new IllegalStateException("Value is null");
        }
        convert(this.value);
    }

    protected void load(T value) throws Exception {
    }

    protected final <M> M fromJson(String str, Class<? extends M> cls) {
        return (M) this.gson.fromJson(str, cls);
    }

    protected final <M> M fromJson(JsonElement jsonElement, Class<? extends M> cls) {
        return (M) this.gson.fromJson(jsonElement, cls);
    }

    @Nullable
    protected final <M> M fromJson(Path path, Class<? extends M> cls) {
        if (Files.notExists(path, new LinkOption[0])) {
            return null;
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(path, new OpenOption[0]));
            try {
                M m = (M) this.gson.fromJson(inputStreamReader, cls);
                inputStreamReader.close();
                return m;
            } finally {
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read file {} for legacy converter {} in {}", path, getClass().getSimpleName(), getNamespace());
            return null;
        }
    }

    protected final Map<String, String> loadMinecraftSettings(Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<>();
        for (String line : Files.readAllLines(path)) {
            String[] split = line.split(":", 2);
            if (split.length == 2) {
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    protected Key getKey(int keyCode) {
        if (keyCode == -1) {
            return Key.NONE;
        }
        if (keyCode < 0) {
            return KeyMapper.getMouseButton(keyCode + 100, KeyMapper.KeyCodeType.LWJGL);
        }
        return KeyMapper.getKey(keyCode, KeyMapper.KeyCodeType.LWJGL);
    }
}
