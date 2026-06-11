package net.labymod.core.loader.vanilla.launchwrapper.transformer.accesswidener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import net.labymod.accesswidener.AccessWidener;
import net.labymod.accesswidener.AccessWidenerBridge;
import net.labymod.accesswidener.AccessWidenerReader;
import net.labymod.accesswidener.bytecode.BytecodeProvider;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformClassloader;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.core.loader.ReflectLabyModLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerController.class */
public class AccessWidenerController {
    private static final String NAMED_NAMESPACE = "named";
    private static final String VANILLA_NAMESPACE = "vanilla";
    private static final String DEFAULT_ACCESS_WIDENER_TEMPLATE = "%s-%s.accesswidener";
    private static final String ACCESS_WIDENER_DIRECTORY_TEMPLATE = "META-INF/%s/accesswidener/%s/";
    private static final String ACCESS_WIDENER_INDEX_TEMPLATE = "META-INF/%s/accesswidener/%s/access_widener_index.json";
    private static final boolean UNOBFUSCATED = MinecraftVersions.V26_1_snapshot_1.orNewer();
    private static final Gson GSON = new GsonBuilder().create();
    private static final Function<AccessWidenerInfo, String> DEFAULT_ACCESS_WIDENER_FORMATTER = context -> {
        return String.format(Locale.ROOT, DEFAULT_ACCESS_WIDENER_TEMPLATE, context.namespace, context.version);
    };
    private static final Function<AccessWidenerInfo, String> ACCESS_WIDENER_DIRECTORY_FORMATTER = context -> {
        return String.format(Locale.ROOT, ACCESS_WIDENER_DIRECTORY_TEMPLATE, context.namespace, context.version);
    };
    private static final Function<AccessWidenerInfo, String> ACCESS_WIDENER_INDEX_FORMATTER = context -> {
        return String.format(Locale.ROOT, ACCESS_WIDENER_INDEX_TEMPLATE, context.namespace, context.version);
    };

    public void processAccessWidener(ClassLoader classLoader, String namespace, String version) {
        configureBytecodeProvider();
        AccessWidenerInfo context = new AccessWidenerInfo(classLoader, namespace, version);
        String accessWidenerNamespace = getAccessWidenerNamespace();
        LazyAccessWidener widener = new LazyAccessWidener(accessWidenerNamespace);
        readDefaultAccessWidener(widener, context);
        readAccessWidenerIndex(widener, context);
        registerAccessWidenerTransformer(widener, accessWidenerNamespace);
    }

    private void registerAccessWidenerTransformer(LazyAccessWidener widener, String namespace) {
        AccessWidener accessWidener = widener.finalizeAccessWidener();
        if (accessWidener == null) {
            return;
        }
        PlatformEnvironment.getPlatformClassloader().registerTransformer(PlatformClassloader.TransformerPhase.NORMAL, new AccessWidenerTransformer(namespace, accessWidener));
    }

    private String getAccessWidenerNamespace() {
        if (UNOBFUSCATED || ReflectLabyModLoader.invokeIsLabyModDevEnvironment()) {
            return "named";
        }
        return "vanilla";
    }

    private void configureBytecodeProvider() {
        AccessWidenerBridge bridge = AccessWidenerBridge.getInstance();
        BytecodeProvider provider = bridge.getBytecodeProvider();
        if (provider != null) {
            return;
        }
        bridge.setBytecodeProvider(new ASMBytecodeProvider());
    }

    private void readDefaultAccessWidener(LazyAccessWidener lazyAccessWidener, AccessWidenerInfo context) {
        String path = DEFAULT_ACCESS_WIDENER_FORMATTER.apply(context);
        readAccessWidener(lazyAccessWidener, context, path);
    }

    private void readAccessWidenerIndex(LazyAccessWidener lazyWidener, AccessWidenerInfo context) {
        String path = ACCESS_WIDENER_INDEX_FORMATTER.apply(context);
        URL resource = context.getResource(path);
        if (resource == null) {
            return;
        }
        try {
            InputStream stream = resource.openStream();
            try {
                Reader reader = new InputStreamReader(stream);
                try {
                    AccessWidenerIndex index = (AccessWidenerIndex) GSON.fromJson(reader, AccessWidenerIndex.class);
                    readAccessWidenerIndex(lazyWidener, index, context);
                    reader.close();
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Throwable th) {
                    try {
                        reader.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } finally {
            }
        } catch (IOException e) {
        }
    }

    private void readAccessWidenerIndex(LazyAccessWidener lazyWidener, AccessWidenerIndex index, AccessWidenerInfo context) {
        String directoryPath = ACCESS_WIDENER_DIRECTORY_FORMATTER.apply(context);
        for (String file : index.files) {
            if (file != null && !file.isEmpty()) {
                readAccessWidener(lazyWidener, context, directoryPath + file);
            }
        }
    }

    private void readAccessWidener(LazyAccessWidener lazyWidener, InputStream stream) {
        AccessWidener accessWidener = lazyWidener.getAccessWidener();
        AccessWidenerReader reader = new AccessWidenerReader(accessWidener);
        reader.read(stream, accessWidener.getNamespace());
    }

    private void readAccessWidener(LazyAccessWidener lazyAccessWidener, AccessWidenerInfo context, String path) {
        URL resource = context.getResource(path);
        if (resource == null) {
            return;
        }
        try {
            InputStream stream = resource.openStream();
            try {
                readAccessWidener(lazyAccessWidener, stream);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException e) {
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerController$LazyAccessWidener.class */
    static class LazyAccessWidener {
        private final String namespace;
        private AccessWidener accessWidener;
        private boolean finalized;

        LazyAccessWidener(String namespace) {
            this.namespace = namespace;
        }

        public AccessWidener getAccessWidener() {
            if (!this.finalized && this.accessWidener == null) {
                AccessWidener aw = new AccessWidener();
                aw.setNamespace(this.namespace);
                this.accessWidener = aw;
            }
            return this.accessWidener;
        }

        public AccessWidener finalizeAccessWidener() {
            this.finalized = true;
            return this.accessWidener;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerController$AccessWidenerInfo.class */
    static class AccessWidenerInfo {
        private final ClassLoader loader;
        private final String namespace;
        private final String version;

        public AccessWidenerInfo(ClassLoader loader, String namespace, String version) {
            this.loader = loader;
            this.namespace = namespace;
            this.version = version;
        }

        public URL getResource(String path) {
            return this.loader.getResource(path);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/vanilla/launchwrapper/transformer/accesswidener/AccessWidenerController$AccessWidenerIndex.class */
    static class AccessWidenerIndex {
        int version;
        List<String> files = new ArrayList();

        AccessWidenerIndex() {
        }
    }
}
