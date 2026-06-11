package net.labymod.api.configuration.loader.impl;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.configuration.exception.ConfigurationLoadException;
import net.labymod.api.configuration.exception.ConfigurationSaveException;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.annotation.Exclude;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.property.ConfigPropertyTypeAdapter;
import net.labymod.api.event.labymod.config.ConfigurationLoadEvent;
import net.labymod.api.event.labymod.config.ConfigurationVersionUpdateEvent;
import net.labymod.api.event.labymod.config.JsonConfigLoaderInitializeEvent;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.gson.SettingDevelopmentExclusionStrategy;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.execption.InsufficientStorageSpace;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/loader/impl/JsonConfigLoader.class */
public class JsonConfigLoader extends AbstractConfigLoader {
    private static final Logging LOGGER = Logging.create((Class<?>) JsonConfigLoader.class);
    private static final String CONFIG_VERSION_KEY = "configVersion";
    private final Gson gson;

    public JsonConfigLoader(Path directory) {
        this(directory, new GsonBuilder());
    }

    public JsonConfigLoader(Path directory, GsonBuilder builder) {
        super(directory);
        Laby.fireEvent(new JsonConfigLoaderInitializeEvent(builder));
        builder.registerTypeAdapter(ConfigProperty.class, new ConfigPropertyTypeAdapter());
        builder.setExclusionStrategies(new ExclusionStrategy[]{new SettingDevelopmentExclusionStrategy(Laby.labyAPI())});
        this.gson = builder.create();
    }

    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public <T extends ConfigAccessor> T load(Class<T> cls) throws ConfigurationLoadException {
        JsonObject convertedConfigurationJson;
        Path path = getPath(cls);
        try {
            if (IOUtil.exists(path)) {
                BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(path);
                try {
                    JsonObject jsonObject = (JsonObject) this.gson.fromJson(bufferedReaderNewBufferedReader, JsonObject.class);
                    try {
                        convertedConfigurationJson = getConvertedConfigurationJson(cls, jsonObject);
                    } catch (Exception e) {
                        LOGGER.error("Failed to check version of of " + cls.getName(), e);
                        convertedConfigurationJson = jsonObject;
                    }
                    ConfigAccessor configAccessorLoadConfig = (ConfigAccessor) this.gson.fromJson(convertedConfigurationJson, cls);
                    if (configAccessorLoadConfig == null) {
                        configAccessorLoadConfig = loadConfig(cls);
                    }
                    T t = (T) configAccessorLoadConfig;
                    if (bufferedReaderNewBufferedReader != null) {
                        bufferedReaderNewBufferedReader.close();
                    }
                    return t;
                } finally {
                }
            }
            return (T) loadConfig(cls);
        } catch (Exception e2) {
            throw new ConfigurationLoadException(cls, e2);
        }
    }

    private <T extends ConfigAccessor> JsonObject getConvertedConfigurationJson(Class<T> cls, JsonObject jsonObject) {
        int usedConfigVersion;
        Laby.fireEvent(new ConfigurationLoadEvent(cls, jsonObject));
        Reflection.getFields(cls, false, member -> {
            JsonObject convertedConfigurationJson;
            JsonObject convertedConfigurationJson2;
            if (member.isAnnotationPresent(Exclude.class) || !jsonObject.has(member.getName())) {
                return;
            }
            JsonElement jsonElement = jsonObject.get(member.getName());
            if (!jsonElement.isJsonObject() || !ConfigAccessor.class.isAssignableFrom(member.getType())) {
                Class<?> type = member.getType();
                Type genericType = member.getGenericType();
                if (ConfigProperty.class.isAssignableFrom(type) && (genericType instanceof ParameterizedType)) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    int len$ = actualTypeArguments.length;
                    int i$ = 0;
                    while (true) {
                        if (i$ >= len$) {
                            break;
                        }
                        Type actualTypeArgument = actualTypeArguments[i$];
                        if (actualTypeArgument instanceof Class) {
                            type = (Class) actualTypeArgument;
                            genericType = type;
                            break;
                        } else if (!(actualTypeArgument instanceof ParameterizedType)) {
                            i$++;
                        } else {
                            ParameterizedType subType = (ParameterizedType) actualTypeArgument;
                            type = (Class) subType.getRawType();
                            genericType = subType;
                            break;
                        }
                    }
                }
                if (jsonElement.isJsonArray() && List.class.isAssignableFrom(type)) {
                    Class<? extends ConfigAccessor> accessorClass = null;
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType2 = (ParameterizedType) genericType;
                        Type[] actualTypeArguments2 = parameterizedType2.getActualTypeArguments();
                        if (actualTypeArguments2.length != 0) {
                            Type patt0$temp = actualTypeArguments2[0];
                            if (patt0$temp instanceof Class) {
                                Class<? extends ConfigAccessor> cls2 = (Class) patt0$temp;
                                if (ConfigAccessor.class.isAssignableFrom(cls2)) {
                                    accessorClass = cls2;
                                }
                            }
                        }
                    }
                    if (accessorClass != null) {
                        JsonArray newArray = new JsonArray();
                        for (JsonElement element : jsonElement.getAsJsonArray()) {
                            if (element.isJsonObject()) {
                                try {
                                    convertedConfigurationJson = getConvertedConfigurationJson(accessorClass, element.getAsJsonObject());
                                } catch (Exception e) {
                                    LOGGER.error("Failed to check version of sub configuration of " + member.getName() + " (" + cls.getName() + ")", e);
                                    convertedConfigurationJson = element.getAsJsonObject();
                                }
                                newArray.add(convertedConfigurationJson);
                            } else {
                                return;
                            }
                        }
                        jsonObject.add(member.getName(), newArray);
                        return;
                    }
                    return;
                }
                return;
            }
            try {
                convertedConfigurationJson2 = getConvertedConfigurationJson(member.getType(), jsonElement.getAsJsonObject());
            } catch (Exception e2) {
                LOGGER.error("Failed to check version of " + member.getName() + " (" + cls.getName() + ")", e2);
                convertedConfigurationJson2 = jsonElement.getAsJsonObject();
            }
            jsonObject.add(member.getName(), convertedConfigurationJson2);
        });
        if (!Config.class.isAssignableFrom(cls)) {
            return jsonObject;
        }
        try {
            Config config = (Config) createInstance(cls);
            int configVersion = config.getConfigVersion();
            if (jsonObject.has(CONFIG_VERSION_KEY) && jsonObject.get(CONFIG_VERSION_KEY).isJsonPrimitive() && jsonObject.get(CONFIG_VERSION_KEY).getAsJsonPrimitive().isNumber()) {
                usedConfigVersion = jsonObject.get(CONFIG_VERSION_KEY).getAsInt();
            } else {
                usedConfigVersion = -1;
            }
            if (configVersion > usedConfigVersion) {
                ConfigurationVersionUpdateEvent configurationVersionUpdateEvent = (ConfigurationVersionUpdateEvent) Laby.fireEvent(new ConfigurationVersionUpdateEvent(cls, jsonObject, usedConfigVersion, configVersion));
                JsonObject editedJsonObject = configurationVersionUpdateEvent.getEditedJsonObject();
                if (editedJsonObject != null) {
                    editedJsonObject.addProperty(CONFIG_VERSION_KEY, Integer.valueOf(configVersion));
                    return editedJsonObject;
                }
                if (usedConfigVersion == -1) {
                    jsonObject.addProperty(CONFIG_VERSION_KEY, Integer.valueOf(configVersion));
                }
            }
            return jsonObject;
        } catch (ReflectiveOperationException e) {
            return jsonObject;
        }
    }

    private <T extends ConfigAccessor> T loadConfig(Class<T> cls) throws ReflectiveOperationException {
        T t = (T) createInstance(cls);
        save(t);
        return t;
    }

    private <T extends ConfigAccessor> T createInstance(Class<T> cls) throws ReflectiveOperationException {
        Constructor<T> constructor = cls.getDeclaredConstructor(new Class[0]);
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[0]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public void save(ConfigAccessor config) throws ConfigurationSaveException {
        Path path = getPath(config.getClass());
        Path parent = path.getParent();
        try {
            if (!IOUtil.exists(parent)) {
                IOUtil.createDirectories(parent);
            }
            GsonUtil.writeJson(this.gson, path, config);
        } catch (Exception exception) {
            if (exception instanceof InsufficientStorageSpace) {
                Debounce.of("unable-to-save-configuration", 200L, createDebounceTask(path));
                return;
            }
            throw new ConfigurationSaveException(config, exception);
        }
    }

    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public Object serialize(ConfigAccessor config) throws Exception {
        return this.gson.toJsonTree(config);
    }

    @Override // net.labymod.api.configuration.loader.ConfigLoader
    public String getFileExtension() {
        return "json";
    }

    public Gson getGson() {
        return this.gson;
    }

    public static JsonConfigLoader createDefault() {
        return new JsonConfigLoader(defaultDirectory());
    }

    private Runnable createDebounceTask(Path file) {
        return () -> {
            NotificationController controller = Laby.references().notificationController();
            controller.push(Notification.builder().title(Component.translatable("labymod.notification.insufficientStorageSpace.configuration.title", new Component[0])).text(Component.translatable("labymod.notification.insufficientStorageSpace.configuration.description", Component.text(IOUtil.getFileStoreName(file), NamedTextColor.YELLOW, TextDecoration.BOLD))).type(Notification.Type.SYSTEM).duration(10000L).build());
        };
    }
}
