package net.labymod.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.classloader.ClassLoaderUtil;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.models.version.Version;
import net.labymod.api.service.model.ServiceModel;
import net.labymod.api.util.function.ThrowableConsumer;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.reflection.Reflection;
import net.labymod.api.util.version.serial.VersionDeserializer;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/CustomServiceLoader.class */
public class CustomServiceLoader<T> implements Iterable<T> {
    private static final String CUSTOM_SERVICES_DIRECTORY = "META-INF/custom-services/";
    private static final String SERVICES_DIRECTORY = "META-INF/services/";
    private final List<T> serviceClasses = new ArrayList();
    private final Class<T> serviceClass;
    private final String serviceClassName;
    private final ClassLoader loader;
    private final ServiceType serviceType;
    private final ConstructorCreator<T> constructorCreator;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/CustomServiceLoader$ConstructorCreator.class */
    @FunctionalInterface
    public interface ConstructorCreator<T> {
        T createInstance(String str, ClassLoader classLoader) throws Throwable;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/CustomServiceLoader$ServiceType.class */
    public enum ServiceType {
        ADVANCED { // from class: net.labymod.api.service.CustomServiceLoader.ServiceType.1
            private final Gson gson = new GsonBuilder().create();

            @Override // net.labymod.api.service.CustomServiceLoader.ServiceType
            <T> void load(CustomServiceLoader<T> serviceLoader) throws Throwable {
                String file = serviceLoader.getServiceFileName(CustomServiceLoader.CUSTOM_SERVICES_DIRECTORY) + ".json";
                serviceLoader.iterateResources(file, resource -> {
                    InputStream stream = resource.openStream();
                    try {
                        InputStreamReader reader = new InputStreamReader(stream);
                        try {
                            ServiceModel[] models = (ServiceModel[]) this.gson.fromJson(reader, ServiceModel[].class);
                            for (ServiceModel model : models) {
                                if (ASMHelper.canLoadClass((int) model.getClassVersion())) {
                                    Version version = model.getVersion();
                                    if (version != null) {
                                        Version runningVersion = VersionDeserializer.from(MinecraftVersions.current().getRawVersion());
                                        if (runningVersion.isCompatible(version)) {
                                            serviceLoader.registerClass(model.getServiceClass());
                                        }
                                    }
                                }
                            }
                            reader.close();
                            if (stream != null) {
                                stream.close();
                            }
                        } finally {
                        }
                    } catch (Throwable t$) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable x2) {
                                t$.addSuppressed(x2);
                            }
                        }
                        throw t$;
                    }
                });
            }
        },
        CROSS_VERSION { // from class: net.labymod.api.service.CustomServiceLoader.ServiceType.2
            @Override // net.labymod.api.service.CustomServiceLoader.ServiceType
            <T> void load(CustomServiceLoader<T> serviceLoader) throws Throwable {
                String serviceFile = serviceLoader.getServiceFileName(CustomServiceLoader.SERVICES_DIRECTORY);
                serviceLoader.iterateResources(serviceFile, resource -> {
                    InputStream stream = resource.openStream();
                    try {
                        String serviceContent = IOUtil.toString(stream);
                        String[] split = serviceContent.split("\n");
                        for (String s : split) {
                            String[] serviceSplit = s.split(";", 2);
                            try {
                                double version = Double.parseDouble(serviceSplit[1]);
                                if (ASMHelper.canLoadClass((int) version)) {
                                    String className = serviceSplit[0];
                                    serviceLoader.registerClass(className.trim());
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid service format " + s);
                            }
                        }
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (Throwable t$) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable x2) {
                                t$.addSuppressed(x2);
                            }
                        }
                        throw t$;
                    }
                });
            }
        },
        NORMAL { // from class: net.labymod.api.service.CustomServiceLoader.ServiceType.3
            @Override // net.labymod.api.service.CustomServiceLoader.ServiceType
            <T> void load(CustomServiceLoader<T> serviceLoader) throws Throwable {
                String serviceFile = serviceLoader.getServiceFileName(CustomServiceLoader.SERVICES_DIRECTORY);
                serviceLoader.iterateResources(serviceFile, resource -> {
                    InputStream stream = resource.openStream();
                    try {
                        String content = IOUtil.toString(stream);
                        String[] lines = content.split(System.lineSeparator());
                        for (String line : lines) {
                            serviceLoader.registerClass(line.trim());
                        }
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (Throwable t$) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (Throwable x2) {
                                t$.addSuppressed(x2);
                            }
                        }
                        throw t$;
                    }
                });
            }
        };

        abstract <T> void load(CustomServiceLoader<T> customServiceLoader) throws IOException;
    }

    private CustomServiceLoader(@NotNull Class<T> serviceClass, @NotNull ClassLoader loader, @NotNull ServiceType serviceType, @NotNull ConstructorCreator<T> constructorCreator) {
        this.serviceType = serviceType;
        this.serviceClass = serviceClass;
        this.serviceClassName = serviceClass.getName();
        this.loader = loader;
        this.constructorCreator = constructorCreator;
        loadServices();
    }

    private void loadServices() {
        try {
            this.serviceType.load(this);
            if (this.serviceType == ServiceType.CROSS_VERSION) {
                ServiceType.ADVANCED.load(this);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @NotNull
    public static <T> CustomServiceLoader<T> load(@NotNull Class<T> serviceClass, ClassLoader classLoader, ServiceType serviceType) {
        return load(serviceClass, classLoader, serviceType, (className, loader) -> {
            Constructor<?> constructor = findEmptyConstructor(className, loader);
            if (constructor == null) {
                return null;
            }
            return constructor.newInstance(new Object[0]);
        });
    }

    @NotNull
    public static <T> CustomServiceLoader<T> load(@NotNull Class<T> serviceClass, ClassLoader loader, ServiceType serviceType, ConstructorCreator<T> constructorCreator) {
        return new CustomServiceLoader<>(serviceClass, loader, serviceType, constructorCreator);
    }

    public List<T> getServiceClasses() {
        return this.serviceClasses;
    }

    public String getServiceClassName() {
        return this.serviceClassName;
    }

    public Class<T> getServiceClass() {
        return this.serviceClass;
    }

    public ClassLoader getLoader() {
        return this.loader;
    }

    public ServiceType getServiceType() {
        return this.serviceType;
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator<T> iterator() {
        return this.serviceClasses.iterator();
    }

    void iterateResources(String serviceFile, ThrowableConsumer<URL, IOException> resourceConsumer) throws Throwable {
        Enumeration<URL> resources = PlatformEnvironment.getResources(this.loader, serviceFile);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            resourceConsumer.accept(resource);
        }
    }

    String getServiceFileName(String directoryPath) {
        return directoryPath + this.serviceClassName;
    }

    static Constructor<?> findEmptyConstructor(String className, ClassLoader loader) throws ClassNotFoundException {
        Class<?> cls = ClassLoaderUtil.forName(className, false, loader);
        return Reflection.searchEmptyConstructor(cls);
    }

    void registerClass(String className) {
        try {
            T value = this.constructorCreator.createInstance(className, this.loader);
            if (value != null) {
                this.serviceClasses.add(value);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
