package net.labymod.core.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingService;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.api.mapping.loader.MappingReader;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.remap.JarRemapperBuilder;
import net.labymod.api.models.Implements;
import net.labymod.api.service.Service;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.loader.DefaultLabyModLoader;
import net.labymod.core.mapping.loader.FartMappingReader;
import net.labymod.core.mapping.loader.mcp.McpConfig;
import net.labymod.core.mapping.loader.mcp.McpMappingLoader;
import net.labymod.core.mapping.loader.mcp.SeargeMappingLoader;
import net.labymod.core.mapping.loader.mojang.MojangMappingLoader;
import net.labymod.core.mapping.provider.FartMappingProvider;
import net.labymod.core.mapping.remap.AsmRemapper;
import net.labymod.core.mapping.remap.FartJarRemapperBuilder;
import net.labymod.core.mapping.remap.MixinRemapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/FartMappingService.class */
@Singleton
@Implements(MappingService.class)
public class FartMappingService extends Service implements MappingService {
    private final Collection<MappingReader> mappingReaders = new HashSet();
    private final Map<String, MappingProvider> mappingProviders = new HashMap();
    private final Map<String, MappingProvider> cachedMappings = new HashMap();

    @Inject
    public FartMappingService() {
    }

    @Override // net.labymod.api.service.Service
    protected void prepare() {
        try {
            if (!IOUtil.exists(Constants.Files.MAPPINGS)) {
                IOUtil.createDirectories(Constants.Files.MAPPINGS);
            }
            registerReader(new FartMappingReader());
            if (PlatformEnvironment.isNoMojangMappings()) {
                McpConfig mcpConfig = null;
                McpConfig[] mcpConfigArrValues = McpConfig.values();
                int length = mcpConfigArrValues.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    McpConfig value = mcpConfigArrValues[i];
                    if (!value.getMinecraftVersion().equals(MinecraftVersions.current().getRawVersion())) {
                        i++;
                    } else {
                        mcpConfig = value;
                        break;
                    }
                }
                if (mcpConfig != null) {
                    try {
                        SeargeMappingLoader seargeMappingLoader = new SeargeMappingLoader(mcpConfig);
                        registerMappings(seargeMappingLoader);
                        MappingProvider mappings = registerMappings(new McpMappingLoader(seargeMappingLoader));
                        registerMappings(mappings.sourceMappings());
                        return;
                    } catch (Exception exception) {
                        throw new RuntimeException("Failed to register MCP mappings", exception);
                    }
                }
                return;
            }
            try {
                MappingProvider mappings2 = registerMappings(new MojangMappingLoader());
                registerMappings(mappings2.sourceMappings());
            } catch (Exception exception2) {
                throw new RuntimeException("Failed to register Mojang mappings", exception2);
            }
        } catch (IOException exception3) {
            throw new RuntimeException(exception3);
        }
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public Collection<String> getNamespaces() {
        return Collections.unmodifiableCollection(this.mappingProviders.keySet());
    }

    @Override // net.labymod.api.mapping.MappingService
    public void registerReader(@NotNull MappingReader reader) {
        this.mappingReaders.add(reader);
    }

    @Override // net.labymod.api.mapping.MappingService
    @Nullable
    public MappingReader findReader(@NotNull MappingType type) {
        for (MappingReader mappingReader : this.mappingReaders) {
            for (MappingType mappingType : mappingReader.supportedTypes()) {
                if (mappingType == type) {
                    return mappingReader;
                }
            }
        }
        return null;
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public MappingProvider registerMappings(@NotNull InputStream stream, @NotNull String sourceNamespace, @NotNull String targetNamespace, @NotNull MappingType type) throws IOException {
        MappingReader reader = findReader(type);
        if (reader == null) {
            throw new IllegalStateException("No mapping reader registered for type " + String.valueOf(type));
        }
        MappingProvider provider = reader.read(stream, sourceNamespace, targetNamespace, type);
        return registerMappings(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public MappingProvider registerMappings(MappingLoader loader) throws IOException {
        InputStream stream = loader.load();
        try {
            MappingProvider mappingProviderRegisterMappings = registerMappings(stream, loader.getSourceNamespace(), loader.getTargetNamespace(), loader.type());
            if (stream != null) {
                stream.close();
            }
            return mappingProviderRegisterMappings;
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public MappingProvider registerMappings(@NotNull MappingProvider provider) {
        if (!provider.getSourceNamespace().equals(MappingNamespace.MINECRAFT_OBFUSCATED) && !provider.getTargetNamespace().equals(MappingNamespace.MINECRAFT_OBFUSCATED)) {
            throw new IllegalArgumentException("Provided mappings do not contain the obfuscated namespace");
        }
        if (provider.getTargetNamespace().equals(MappingNamespace.MINECRAFT_OBFUSCATED)) {
            provider = provider.reverse();
        }
        this.mappingProviders.put(provider.getTargetNamespace(), provider);
        return provider;
    }

    @Override // net.labymod.api.mapping.MappingService
    @Nullable
    public MappingProvider mappings(@NotNull String sourceNamespace, @NotNull String targetNamespace) {
        MappingProvider mappingProviderReverse;
        MappingProvider mappingProvider;
        String cacheKey = sourceNamespace + targetNamespace;
        if (this.cachedMappings.containsKey(cacheKey)) {
            return this.cachedMappings.get(cacheKey);
        }
        MappingProvider sourceProvider = this.mappingProviders.get(sourceNamespace);
        MappingProvider targetProvider = this.mappingProviders.get(targetNamespace);
        MappingProvider mappings = null;
        if (sourceProvider == null && targetProvider != null) {
            if (targetProvider.getSourceNamespace().equals(sourceNamespace)) {
                mappingProvider = targetProvider;
            } else {
                mappingProvider = null;
            }
            mappings = mappingProvider;
        } else if (targetProvider == null && sourceProvider != null) {
            if (sourceProvider.getSourceNamespace().equals(targetNamespace)) {
                mappingProviderReverse = sourceProvider.reverse();
            } else {
                mappingProviderReverse = null;
            }
            mappings = mappingProviderReverse;
        } else if (sourceProvider != null) {
            mappings = sourceProvider.reverse().chain(targetProvider);
        }
        this.cachedMappings.put(cacheKey, mappings);
        return mappings;
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public MappingProvider currentMappings() {
        String currentNamespace = DefaultLabyModLoader.getInstance().isLabyModDevelopmentEnvironment() ? MappingNamespace.NAMED : MappingNamespace.MINECRAFT_OBFUSCATED;
        MappingProvider provider = mappings(MappingNamespace.NAMED, currentNamespace);
        if (provider == null) {
            throw new IllegalStateException("Current mappings are not available");
        }
        return provider;
    }

    @Override // net.labymod.api.mapping.MappingService
    @Nullable
    public IRemapper mixinRemapper(@NotNull String sourceNamespace, @NotNull String targetNamespace) {
        MappingProvider provider = mappings(sourceNamespace, targetNamespace);
        if (provider == null) {
            return null;
        }
        return mixinRemapper(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public IRemapper mixinRemapper(@NotNull MappingProvider provider) {
        return new MixinRemapper(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    @Nullable
    public Remapper asmRemapper(@NotNull String sourceNamespace, @NotNull String targetNamespace) {
        MappingProvider provider = mappings(sourceNamespace, targetNamespace);
        if (provider == null) {
            return null;
        }
        return asmRemapper(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public Remapper asmRemapper(@NotNull MappingProvider provider) {
        return new AsmRemapper(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    public JarRemapperBuilder jarRemapper(@NotNull String sourceNamespace, @NotNull String targetNamespace) {
        MappingProvider provider = mappings(sourceNamespace, targetNamespace);
        if (provider == null) {
            return null;
        }
        return jarRemapper(provider);
    }

    @Override // net.labymod.api.mapping.MappingService
    @NotNull
    public JarRemapperBuilder jarRemapper(@NotNull MappingProvider provider) {
        if (!(provider instanceof FartMappingProvider)) {
            throw new IllegalStateException("Cannot remap with non-FartMappingProvider");
        }
        return new FartJarRemapperBuilder((FartMappingProvider) provider);
    }
}
