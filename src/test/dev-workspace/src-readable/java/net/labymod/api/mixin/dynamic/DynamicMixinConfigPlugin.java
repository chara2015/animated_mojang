package net.labymod.api.mixin.dynamic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mixin/dynamic/DynamicMixinConfigPlugin.class */
public class DynamicMixinConfigPlugin {
    private static final String DYNAMIC_MIXIN_CONFIG_LOCATION = "META-INF/mixin/dynamic-mixin-config.txt";
    private static final String DYNAMIC_MIXIN_APPLIER_CONFIG_LOCATION = "META-INF/mixin/dynamic-mixin-applier-config.txt";
    private final Map<String, List<DynamicMixinApplier>> dynamicMixinAppliers = new HashMap();
    private final Map<String, List<String>> dynamicMixins = new HashMap();

    public void onLoad(ClassLoader classLoader, String mixinPackage, String runningVersion) {
        readResources(classLoader, DYNAMIC_MIXIN_CONFIG_LOCATION, runningVersion, namespace -> {
            return this.dynamicMixins.computeIfAbsent(namespace, s -> {
                return new ArrayList();
            });
        }, (list, name) -> {
            if (!list.contains(name)) {
                list.add(name);
            }
        });
        readResources(classLoader, DYNAMIC_MIXIN_APPLIER_CONFIG_LOCATION, runningVersion, namespace2 -> {
            return this.dynamicMixinAppliers.computeIfAbsent(namespace2, s -> {
                return new ArrayList();
            });
        }, (list2, name2) -> {
            if (name2 == null || Objects.equals(name2, "null")) {
                list2.add(AlwaysDynamicMixinApplier.INSTANCE);
                return;
            }
            try {
                Class<?> cls = classLoader.loadClass(name2);
                Object instance = cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                if (!(instance instanceof DynamicMixinApplier)) {
                    return;
                }
                list2.add((DynamicMixinApplier) instance);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private <T> void readResources(ClassLoader classLoader, String location, String runningVersion, Function<String, List<T>> function, BiConsumer<List<T>, String> consumer) {
        try {
            Enumeration<URL> resources = DynamicMixinUtil.getResources(classLoader, location);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (url != null) {
                    readResource(url, runningVersion, function, consumer);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean shouldApply(Function<String, Boolean> condition, String targetClassName, String mixinClassName) {
        for (Map.Entry<String, List<DynamicMixinApplier>> entry : this.dynamicMixinAppliers.entrySet()) {
            String key = entry.getKey();
            if (!condition.apply(key).booleanValue()) {
                List<String> dynamicMixins = this.dynamicMixins.get(key);
                for (String dynamicMixin : dynamicMixins) {
                    if (Objects.equals(dynamicMixin, mixinClassName)) {
                        return false;
                    }
                }
            } else {
                for (DynamicMixinApplier dynamicMixinApplier : entry.getValue()) {
                    boolean apply = dynamicMixinApplier.apply(targetClassName, mixinClassName);
                    if (!apply) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private <T> void readResource(URL url, String runningVersion, Function<String, List<T>> function, BiConsumer<List<T>, String> consumer) throws IOException {
        InputStream inputStream = url.openStream();
        try {
            String content = toString(inputStream);
            String[] lines = content.split("\n");
            for (String str : lines) {
                String line = str.trim();
                if (!line.isEmpty()) {
                    String[] split = line.split(":");
                    if (split.length != 3) {
                        throw new IllegalStateException("Invalid format: \"" + line + "\" (key:value:version)");
                    }
                    String namespace = split[0];
                    List<T> list = function.apply(namespace);
                    String className = split[1];
                    String version = split[2];
                    if (Objects.equals(version, runningVersion)) {
                        consumer.accept(list, className);
                    }
                }
            }
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

    private String toString(InputStream inputStream) throws IOException {
        return new String(readBytes(inputStream), StandardCharsets.UTF_8);
    }

    private byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] data = new byte[512];
            while (true) {
                int read = inputStream.read(data, 0, data.length);
                if (read != -1) {
                    outputStream.write(data, 0, read);
                } else {
                    outputStream.flush();
                    byte[] byteArray = outputStream.toByteArray();
                    outputStream.close();
                    return byteArray;
                }
            }
        } catch (Throwable th) {
            try {
                outputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
