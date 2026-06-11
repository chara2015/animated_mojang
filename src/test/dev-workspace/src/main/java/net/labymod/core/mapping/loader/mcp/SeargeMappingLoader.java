package net.labymod.core.mapping.loader.mcp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.zip.ZipEntry;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.zip.Zips;
import net.minecraftforge.srgutils.IMappingBuilder;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/loader/mcp/SeargeMappingLoader.class */
public class SeargeMappingLoader implements MappingLoader {
    private final McpConfig mcpConfig;
    private final Path mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.SEARGE_MAPPINGS_PATH, MinecraftVersions.current().getRawVersion()), new String[0]);

    public SeargeMappingLoader(McpConfig mcpConfig) {
        this.mcpConfig = mcpConfig;
    }

    public McpConfig mcpConfig() {
        return this.mcpConfig;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getSourceNamespace() {
        return MappingNamespace.MINECRAFT_OBFUSCATED;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getTargetNamespace() {
        return MappingNamespace.SEARGE;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public MappingType type() {
        return MappingType.TSRG2;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public InputStream load() throws IOException {
        if (!Files.exists(this.mappingsPath, new LinkOption[0])) {
            Response<WebInputStream> configResponse = Request.ofInputStream().url(this.mcpConfig.getConfigUrl(), new Object[0]).executeSync();
            if (configResponse.hasException()) {
                throw configResponse.exception();
            }
            InputStream stream = configResponse.get();
            try {
                Zips.readStream(stream, (entry, bytes) -> {
                    String mappingsFileName = this.mcpConfig.isForge() ? "config/joined.tsrg" : "joined.srg";
                    if (!entry.getName().equals(mappingsFileName)) {
                        return false;
                    }
                    String sourceNamespace = getSourceNamespace();
                    String targetNamespace = getTargetNamespace();
                    InputStream mappingsStream = new ByteArrayInputStream(bytes);
                    try {
                        IMappingBuilder mappingBuilder = IMappingBuilder.create(new String[]{sourceNamespace, targetNamespace});
                        IMappingFile mappings = IMappingFile.load(mappingsStream);
                        Zips.read(PlatformEnvironment.getObfuscatedJarPath(), (jarEntry, jarEntryData) -> {
                            loadFieldDescriptor(mappingBuilder, mappings, jarEntry, jarEntryData);
                            return false;
                        });
                        IMappingFile finalMappings = mappingBuilder.build().getMap(sourceNamespace, targetNamespace);
                        finalMappings.write(this.mappingsPath, IMappingFile.Format.TSRG2, false);
                        mappingsStream.close();
                        return true;
                    } catch (Throwable t$) {
                        try {
                            mappingsStream.close();
                        } catch (Throwable x2) {
                            t$.addSuppressed(x2);
                        }
                        throw t$;
                    }
                });
                if (stream != null) {
                    stream.close();
                }
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
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }

    private void loadFieldDescriptor(IMappingBuilder mappingBuilder, IMappingFile file, ZipEntry entry, byte[] data) {
        if (!entry.getName().endsWith(".class")) {
            return;
        }
        ClassReader reader = new ClassReader(data);
        ClassNode node = new ClassNode();
        reader.accept(node, 0);
        IMappingFile.IClass classMapping = file.getClass(node.name);
        if (classMapping == null) {
            return;
        }
        IMappingBuilder.IClass newClass = mappingBuilder.addClass(new String[]{classMapping.getOriginal(), classMapping.getMapped()});
        for (IMappingFile.IMethod method : classMapping.getMethods()) {
            newClass.method(method.getDescriptor(), new String[]{method.getOriginal(), method.getMapped()});
        }
        for (FieldNode field : node.fields) {
            IMappingFile.IField fieldMapping = classMapping.getField(field.name);
            if (fieldMapping != null) {
                newClass.field(new String[]{fieldMapping.getOriginal(), fieldMapping.getMapped()}).descriptor(field.desc);
            }
        }
    }
}
