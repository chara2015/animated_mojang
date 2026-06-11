package net.labymod.core.client.resources.transform;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.CustomHitColorShaderListener;
import net.labymod.util.property.SystemProperties;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/transform/DefaultResourceTransformerRegistry.class */
@Singleton
@Implements(ResourceTransformerRegistry.class)
public class DefaultResourceTransformerRegistry implements ResourceTransformerRegistry {
    private final Logging logging;
    private final Map<ResourceLocation, List<ResourceTransformer>> transformers;

    @Inject
    public DefaultResourceTransformerRegistry(@NotNull Logging.Factory factory, @NotNull EventBus eventBus) {
        this.logging = factory.create("Resource Transformer");
        eventBus.registerListener(new CustomHitColorShaderListener());
        this.transformers = new ConcurrentHashMap();
    }

    @Override // net.labymod.api.client.resources.transform.ResourceTransformerRegistry
    public void register(ResourceLocation location, ResourceTransformer transformer) {
        List<ResourceTransformer> transformers = this.transformers.get(location);
        if (transformers == null) {
            transformers = new ArrayList();
        }
        transformers.add(transformer);
        transformers.sort(Comparator.comparingInt((v0) -> {
            return v0.priority();
        }));
        this.transformers.put(location, transformers);
    }

    public InputStream applyTransformers(ResourceLocation location, InputStream inputStream) {
        if (Laby.references().resourcePackRepository().hasServerPackSelected()) {
            return inputStream;
        }
        ResourceTransformer currentTransformer = null;
        for (Map.Entry<ResourceLocation, List<ResourceTransformer>> entry : this.transformers.entrySet()) {
            if (entry.getKey().equals(location)) {
                this.logging.info("Transforming {}", location);
                try {
                    byte[] resourceData = IOUtil.readBytes(inputStream);
                    for (ResourceTransformer resourceTransformer : entry.getValue()) {
                        currentTransformer = resourceTransformer;
                        resourceData = resourceTransformer.transform(resourceData);
                    }
                    if (SystemProperties.RESOURCE_TRANSFORM.get().booleanValue()) {
                        try {
                            Path modifiedClassDataPath = Paths.get(String.format(Locale.ROOT, "labymod-neo/debug/resources/%s/%s", location.getNamespace(), location.getPath()), new String[0]);
                            IOUtil.createDirectories(modifiedClassDataPath.getParent());
                            Files.write(modifiedClassDataPath, resourceData, new OpenOption[0]);
                        } catch (IOException e) {
                        }
                    }
                    inputStream = IOUtil.writeBytes(resourceData);
                } catch (IOException e2) {
                    Logging logging = this.logging;
                    Object[] objArr = new Object[2];
                    objArr[0] = currentTransformer == null ? "unknown" : currentTransformer.getClass().getName();
                    objArr[1] = location;
                    logging.error("The transformer \"{}\" has caused an error while transforming the resource \"{}\".", objArr);
                }
            }
        }
        return inputStream;
    }

    public void clear() {
        this.transformers.clear();
    }
}
