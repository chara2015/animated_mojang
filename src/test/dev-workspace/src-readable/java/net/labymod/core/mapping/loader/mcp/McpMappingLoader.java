package net.labymod.core.mapping.loader.mcp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.api.Constants;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.mapping.MappingNamespace;
import net.labymod.api.mapping.MappingType;
import net.labymod.api.mapping.loader.MappingLoader;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.zip.Zips;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/loader/mcp/McpMappingLoader.class */
public class McpMappingLoader implements MappingLoader {
    private final SeargeMappingLoader seargeMappingLoader;
    private final Path mappingsPath = Paths.get(String.format(Locale.ROOT, Constants.Files.MCP_MAPPINGS_PATH, MinecraftVersions.current().getRawVersion()), new String[0]);

    public McpMappingLoader(SeargeMappingLoader seargeMappingLoader) {
        this.seargeMappingLoader = seargeMappingLoader;
    }

    private static void readCsv(byte[] csvData, Map<String, String> target) {
        String csv = new String(csvData, StandardCharsets.UTF_8);
        String[] lines = csv.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                target.put(parts[0], parts[1]);
            }
        }
    }

    private static String replacement(String content, Map<String, String> replacements) {
        StringBuilder builder = new StringBuilder(content);
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            int iIndexOf = builder.indexOf(key, 0);
            while (true) {
                int start = iIndexOf;
                if (start > -1) {
                    int end = start + key.length();
                    int nextSearchStart = start + value.length();
                    builder.replace(start, end, value);
                    iIndexOf = builder.indexOf(key, nextSearchStart);
                }
            }
        }
        return builder.toString();
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getSourceNamespace() {
        return MappingNamespace.MINECRAFT_OBFUSCATED;
    }

    @Override // net.labymod.api.mapping.loader.MappingLoader
    @NotNull
    public String getTargetNamespace() {
        return MappingNamespace.NAMED;
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
            Response<WebInputStream> mappingsResponse = Request.ofInputStream().url(this.seargeMappingLoader.mcpConfig().getMappingUrl(), new Object[0]).executeSync();
            if (mappingsResponse.hasException()) {
                throw mappingsResponse.exception();
            }
            Map<String, String> methodMappings = new HashMap<>();
            Map<String, String> fieldMappings = new HashMap<>();
            InputStream stream = mappingsResponse.get();
            try {
                Zips.readStream(stream, (entry, bytes) -> {
                    if (entry.getName().equals("methods.csv")) {
                        readCsv(bytes, methodMappings);
                    }
                    if (entry.getName().equals("fields.csv")) {
                        readCsv(bytes, fieldMappings);
                    }
                    return Boolean.valueOf((methodMappings.isEmpty() || fieldMappings.isEmpty()) ? false : true);
                });
                if (stream != null) {
                    stream.close();
                }
                InputStream seargeMappingsStream = this.seargeMappingLoader.load();
                try {
                    String seargeMappings = new String(seargeMappingsStream.readAllBytes(), StandardCharsets.UTF_8);
                    Files.writeString(this.mappingsPath, replacement(replacement(seargeMappings, methodMappings), fieldMappings), new OpenOption[0]);
                    if (seargeMappingsStream != null) {
                        seargeMappingsStream.close();
                    }
                } catch (Throwable th) {
                    if (seargeMappingsStream != null) {
                        try {
                            seargeMappingsStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                }
                throw th3;
            }
        }
        return Files.newInputStream(this.mappingsPath, new OpenOption[0]);
    }
}
